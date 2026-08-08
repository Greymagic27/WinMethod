package io.github.greymagic27.win_method;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.foreign.Arena;
import java.lang.foreign.GroupLayout;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemoryLayout.PathElement;
import java.lang.foreign.MemorySegment;
import java.lang.invoke.VarHandle;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public abstract class Structure {

    private final Arena arena;
    private final Map<String, VarHandle> handles = new LinkedHashMap<>();
    protected final Map<String, Field> fields = new LinkedHashMap<>();
    private final MemoryLayout layout;
    private MemorySegment segment;

    protected Structure() {
        this(Arena.ofShared());
    }

    public Structure(@NonNull Arena arena) {
        this.arena = arena;
        initFields();
        GroupLayout layout = buildLayout();
        this.layout = layout;
        this.segment = arena.allocate(layout);
        read();
    }

    private void initFields() {
        for (Field field : getClass().getDeclaredFields()) {
            if (Modifier.isStatic(field.getModifiers())) continue;
            field.setAccessible(true);
            try {
                if (field.get(this) == null) {
                    Object value = createDefaultValue(field);
                    if (value != null) field.set(this, value);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private @Nullable Object createDefaultValue(@NonNull Field field) {
        Class<?> type = field.getType();
        if (type.isArray()) {
            ArrayLength arrayLength = field.getAnnotation(ArrayLength.class);
            if (arrayLength == null) throw new IllegalStateException("Array field " + field.getName() + " must have an @ArrayLength");
            Object array = Array.newInstance(type.getComponentType(), arrayLength.value());
            if (Structure.class.isAssignableFrom(type.getComponentType())) {
                for (int i = 0; i < arrayLength.value(); i++) {
                    Array.set(array, i, instantiateStructure(type.getComponentType()));
                }
            }
            return array;
        }
        try {
            if (type.isPrimitive()) return null;
            if (type == String.class) return null;
            return type.getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException e) {
            return null;
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Could not initialise structure field of type " + type, e);
        }
    }

    private @NonNull Structure instantiateStructure(@NonNull Class<?> structureType) {
        try {
            return (Structure) structureType.getDeclaredConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Could not initialise structure array element of type " + structureType, e);
        }
    }

    private GroupLayout buildLayout() {
        List<Field> orderedFields = resolveFieldOrder();
        validateFieldCount(orderedFields);
        List<MemoryLayout> members = new ArrayList<>();
        long currentOffset = 0;
        long maxAlign = 1;
        long maxSize = 0;
        for (Field f : orderedFields) {
            f.setAccessible(true);
            MemoryLayout ml;
            if (f.getType().isArray()) {
                Object array;
                try {
                    array = f.get(this);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
                if (array == null) throw new IllegalStateException("Array field " + f.getName() + " must be initialised");
                int length = Array.getLength(array);
                MemoryLayout elementLayout = getArrayElementLayout(f);
                ml = MemoryLayout.sequenceLayout(length, elementLayout);
            } else if (Structure.class.isAssignableFrom(f.getType())) {
                Object nested;
                try {
                    nested = f.get(this);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
                if (nested == null) throw new IllegalStateException("Nested structure field '" + f.getName() + "' must be initialised");
                ml = ((Structure) nested).layout;
            } else {
                ml = TypeMapper.layoutMappings(f.getType());
                if (ml == null) throw new IllegalStateException("Unsupported struct field type: " + f.getType() + " on " + f.getName());
            }
            if (isUnion()) {
                members.add(ml.withName(f.getName()));
                fields.put(f.getName(), f);
                maxAlign = Math.max(maxAlign, ml.byteAlignment());
                maxSize = Math.max(maxSize, ml.byteSize());
                continue;
            }
            long align = ml.byteAlignment();
            long pad = (align - (currentOffset % align)) % align;
            if (pad > 0) {
                members.add(MemoryLayout.paddingLayout(pad));
                currentOffset += pad;
            }
            ml = ml.withName(f.getName());
            members.add(ml);
            fields.put(f.getName(), f);
            currentOffset += ml.byteSize();
            maxAlign = Math.max(maxAlign, align);
        }
        if (members.isEmpty()) throw new IllegalStateException(getClass().getSimpleName() + " declares no usable fields");
        GroupLayout group;
        if (isUnion()) {
            long totalSize = ((maxSize + maxAlign - 1) / maxAlign) * maxAlign;
            if (totalSize > maxSize) members.add(MemoryLayout.paddingLayout(totalSize - maxSize));
            group = MemoryLayout.unionLayout(members.toArray(new MemoryLayout[0]));
        } else {
            long trailingPad = (maxAlign - (currentOffset % maxAlign)) % maxAlign;
            if (trailingPad > 0) members.add(MemoryLayout.paddingLayout(trailingPad));
            group = MemoryLayout.structLayout(members.toArray(new MemoryLayout[0]));
        }
        for (Field f : orderedFields) {
            if (f.getType().isArray()) continue;
            if (Structure.class.isAssignableFrom(f.getType())) continue;
            handles.put(f.getName(), group.varHandle(PathElement.groupElement(f.getName())));
        }
        return group;
    }

    private void validateFieldCount(List<Field> orderedFields) {
        List<String> declaredNames = new ArrayList<>();
        for (Field f : getClass().getDeclaredFields()) {
            if (!Modifier.isStatic(f.getModifiers())) {
                declaredNames.add(f.getName());
            }
        }
        List<String> orderedNames = new ArrayList<>();
        for (Field f : orderedFields) {
            orderedNames.add(f.getName());
        }
        if (orderedFields.size() != new HashSet<>(orderedFields).size()) {
            throw new IllegalStateException("@FieldOrder on " + getClass().getSimpleName() + " lists a duplicate field name: " + orderedNames);
        }
        List<String> missing = new ArrayList<>(declaredNames);
        missing.removeAll(orderedNames);
        List<String> unexpected = new ArrayList<>(orderedNames);
        unexpected.removeAll(declaredNames);
        if (!missing.isEmpty() || !unexpected.isEmpty()) {
            StringBuilder sb = new StringBuilder("@FieldOrder on ").append(getClass().getSimpleName()).append(" doesn't match its declared fields");
            if (!missing.isEmpty()) sb.append(System.lineSeparator()).append("Missing from @FieldOrder ").append(missing);
            if (!unexpected.isEmpty()) sb.append(System.lineSeparator()).append("Not a declared field: ").append(unexpected);
            throw new IllegalStateException(sb.toString());
        }
    }

    private @NonNull List<Field> resolveFieldOrder() {
        FieldOrder order = getClass().getAnnotation(FieldOrder.class);
        if (order != null) {
            List<Field> resolved = new ArrayList<>();
            List<String> missing = new ArrayList<>();
            for (String name : order.value()) {
                try {
                    resolved.add(getClass().getDeclaredField(name));
                } catch (NoSuchFieldException e) {
                    missing.add(name);
                }
            }
            if (!missing.isEmpty()) throw new IllegalStateException("@FieldOrder on " + getClass().getSimpleName() + " names " + missing + ", but no such field(s) exist");
            return resolved;
        }
        if (getClass().isAnnotationPresent(AutoFieldOrder.class)) {
            List<Field> fields = new ArrayList<>();
            for (Field field : getClass().getDeclaredFields()) {
                if (!Modifier.isStatic(field.getModifiers())) fields.add(field);
            }
            if (fields.isEmpty()) throw new IllegalStateException(getClass().getSimpleName() + " declares no fields");
            return fields;
        }
        throw new IllegalStateException(getClass().getSimpleName() + " must be annotated with @FieldOrder or @AutoFieldOrder");
    }

    private void writeArray(@NonNull Field field, Object value) {
        Class<?> componentType = field.getType().getComponentType();
        ArrayLength arrayLength = field.getAnnotation(ArrayLength.class);
        if (arrayLength != null && Array.getLength(value) != arrayLength.value()) throw new IllegalStateException("Array field " + field.getName() + " must have length " + arrayLength.value());
        if (Structure.class.isAssignableFrom(componentType)) {
            writeStructureArray(field, value);
            return;
        }
        long offset = layout.byteOffset(PathElement.groupElement(field.getName()));
        MemoryLayout elementLayout = getArrayElementLayout(field);
        VarHandle handle = elementLayout.varHandle();
        for (int i = 0; i < Array.getLength(value); i++) {
            Object element = Array.get(value, i);
            Object nativeValue = TypeMapper.toNative(element, componentType, arena);
            handle.set(segment, offset + (i * elementLayout.byteSize()), nativeValue);
        }
    }

    private void writeStructureArray(@NonNull Field field, Object value) {
        long baseOffset = layout.byteOffset(PathElement.groupElement(field.getName()));
        int length = Array.getLength(value);
        for (int i = 0; i < length; i++) {
            Structure element = (Structure) Array.get(value, i);
            if (element == null) throw new IllegalStateException("Element " + i + " of structure array field " + field.getName() + " cannot be null");
            element.write();
            long elementSize = element.layout.byteSize();
            MemorySegment.copy(element.segment, 0, segment, baseOffset + (i * elementSize), elementSize);
        }
    }

    private void readArray(@NonNull Field field) {
        Object value;
        try {
            value = field.get(this);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        if (value == null) throw new IllegalStateException("Array field " + field.getName() + " cannot be null");
        Class<?> componentType = field.getType().getComponentType();
        if (Structure.class.isAssignableFrom(componentType)) {
            readStructureArray(field, value);
            return;
        }
        long offset = layout.byteOffset(PathElement.groupElement(field.getName()));
        MemoryLayout elementLayout = getArrayElementLayout(field);
        VarHandle handle = elementLayout.varHandle();
        for (int i = 0; i < Array.getLength(value); i++) {
            Object nativeValue = handle.get(segment, offset + (i * elementLayout.byteSize()));
            Object javaValue = TypeMapper.fromNative(nativeValue, componentType);
            Array.set(value, i, javaValue);
        }
    }

    private void readStructureArray(@NonNull Field field, Object value) {
        long baseOffset = layout.byteOffset(PathElement.groupElement(field.getName()));
        int length = Array.getLength(value);
        for (int i = 0; i < length; i++) {
            Structure element = (Structure) Array.get(value, i);
            if (element == null) throw new IllegalStateException("Element " + i + " of structure array field " + field.getName() + " cannot be null");
            long elementSize = element.layout.byteSize();
            MemorySegment.copy(segment, baseOffset + (i * elementSize), element.segment, 0, elementSize);
            element.read();
        }
    }

    private @NonNull MemoryLayout getArrayElementLayout(@NonNull Field field) {
        Class<?> componentType = field.getType().getComponentType();
        if (Structure.class.isAssignableFrom(componentType)) {
            Object array;
            try {
                array = field.get(this);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            if (array == null || Array.getLength(array) == 0) throw new IllegalStateException("Structure array field " + field.getName() + " must be initialised with at least one element");
            Structure element = (Structure) Array.get(array, 0);
            if (element == null) throw new IllegalStateException("Structure array field " + field.getName() + " has an uninitialised element at index 0");
            return element.layout;
        }
        MemoryLayout elementLayout = TypeMapper.layoutMappings(componentType);
        if (elementLayout == null) throw new IllegalStateException("Unsupported array component type: " + componentType + " in field " + field.getName());
        return elementLayout;
    }

    private void writeNestedStructure(@NonNull Field field, Object nestedValue) {
        Structure nested = (Structure) nestedValue;
        if (!(nested instanceof Union union) || union.hasActiveField()) nested.write();
        long offset = layout.byteOffset(PathElement.groupElement(field.getName()));
        long size = nested.layout.byteSize();
        MemorySegment.copy(nested.segment, 0, segment, offset, size);
    }

    private void readNestedStructure(@NonNull Field field, Object nestedValue) {
        Structure nested = (Structure) nestedValue;
        long offset = layout.byteOffset(PathElement.groupElement(field.getName()));
        long size = nested.layout.byteSize();
        MemorySegment.copy(segment, offset, nested.segment, 0, size);
        nested.read();
    }

    public Pointer pointer() {
        write();
        return new Pointer(segment);
    }

    public int size() {
        return Math.toIntExact(layout.byteSize());
    }

    public void write() {
        for (Field f : fields.values()) writeField(f);
    }

    protected void writeField(@NonNull Field f) {
        Object javaValue;
        try {
            javaValue = f.get(this);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        if (f.getType().isArray()) {
            if (javaValue == null) throw new IllegalStateException("Array field " + f.getName() + " cannot be null");
            writeArray(f, javaValue);
            return;
        }
        if (Structure.class.isAssignableFrom(f.getType())) {
            if (javaValue == null) throw new IllegalStateException("Nested structure field " + f.getName() + " cannot be null");
            writeNestedStructure(f, javaValue);
            return;
        }
        Object nativeValue = TypeMapper.toNative(javaValue, f.getType(), arena);
        handles.get(f.getName()).set(segment, 0, nativeValue);
    }

    public void read() {
        for (Field f : fields.values()) readField(f);
    }

    protected void readField(@NonNull Field f) {
        if (f.getType().isArray()) {
            readArray(f);
            return;
        }
        if (Structure.class.isAssignableFrom(f.getType())) {
            Object nestedValue;
            try {
                nestedValue = f.get(this);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            if (nestedValue == null) throw new IllegalStateException("Nested structure field : " + f.getName() + " cannot be null");
            readNestedStructure(f, nestedValue);
            return;
        }
        if (!TypeMapper.isReadable(f.getType())) return;
        Object raw = handles.get(f.getName()).get(segment, 0);
        try {
            f.set(this, TypeMapper.fromNative(raw, f.getType()));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        write();
        StringBuilder sb = new StringBuilder(getClass().getSimpleName()).append("{");
        boolean first = true;
        for (Map.Entry<String, Field> name : fields.entrySet()) {
            if (!first) sb.append(", ");
            try {
                sb.append(name.getKey()).append("=").append(name.getValue().get(this));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            first = false;
        }
        return sb.append("}").toString();
    }

    public void useMemory(MemorySegment segment) {
        this.segment = segment;
    }

    protected boolean isUnion() {
        return false;
    }

    /// Structure field order is manually set using field names
    ///
    /// @deprecated Use {@link AutoFieldOrder} instead
    @SuppressWarnings("DeprecatedIsStillUsed")
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @Deprecated
    public @interface FieldOrder {
        String[] value();
    }

    /// Structure field order is automatically set using the declared field order
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface AutoFieldOrder {
    }

    /// Specifies the length of an array
    /// (i.e: @ArrayLength(8)
    /// private byte[] data;)
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface ArrayLength {
        int value();
    }
}