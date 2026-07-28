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
    private final Map<String, Field> fields = new LinkedHashMap<>();
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
            if (arrayLength == null) throw new IllegalStateException("Array field " + field.getName() + " must have @ArrayLength");
            return Array.newInstance(type.getComponentType(), arrayLength.value());
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

    private GroupLayout buildLayout() {
        List<Field> orderedFields = resolveFieldOrder();
        validateFieldCount(orderedFields);
        List<MemoryLayout> members = new ArrayList<>();
        long currentOffset = 0;
        long maxAlign = 1;
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
            } else {
                ml = TypeMapper.layoutMappings(f.getType());
                if (ml == null) throw new IllegalStateException("Unsupported struct field type: " + f.getType() + " on " + f.getName());
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
        long trailingPad = (maxAlign - (currentOffset % maxAlign)) % maxAlign;
        if (trailingPad > 0) members.add(MemoryLayout.paddingLayout(trailingPad));
        GroupLayout group = MemoryLayout.structLayout(members.toArray(new MemoryLayout[0]));
        for (Field f : orderedFields) {
            if (f.getType().isArray()) continue;
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
        long offset = layout.byteOffset(PathElement.groupElement(field.getName()));
        ArrayLength arrayLength = field.getAnnotation(ArrayLength.class);
        if (arrayLength != null && Array.getLength(value) != arrayLength.value()) throw new IllegalStateException("Array field " + field.getName() + " must have length " + arrayLength.value());
        MemoryLayout elementLayout = getArrayElementLayout(field);
        VarHandle handle = elementLayout.varHandle();
        Class<?> componentType = field.getType().getComponentType();
        for (int i = 0; i < Array.getLength(value); i++) {
            Object element = Array.get(value, i);
            Object nativeValue = TypeMapper.toNative(element, componentType, arena);
            handle.set(segment, offset + (i * elementLayout.byteSize()), nativeValue);
        }
    }

    private void readArray(@NonNull Field field) {
        long offset = layout.byteOffset(PathElement.groupElement(field.getName()));
        Object value;
        try {
            value = field.get(this);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        if (value == null) throw new IllegalStateException("Array field " + field.getName() + " cannot be null");
        MemoryLayout elementLayout = getArrayElementLayout(field);
        VarHandle handle = elementLayout.varHandle();
        Class<?> componentType = field.getType().getComponentType();
        for (int i = 0; i < Array.getLength(value); i++) {
            Object nativeValue = handle.get(segment, offset + (i * elementLayout.byteSize()));
            Object javaValue = TypeMapper.fromNative(nativeValue, componentType);
            Array.set(value, i, javaValue);
        }
    }

    private @NonNull MemoryLayout getArrayElementLayout(@NonNull Field field) {
        Class<?> componentType = field.getType().getComponentType();
        MemoryLayout elementLayout = TypeMapper.layoutMappings(componentType);
        if (elementLayout == null) throw new IllegalStateException("Unsupported array component type: " + componentType + " in field " + field.getName());
        return elementLayout;
    }

    public Pointer pointer() {
        write();
        return new Pointer(segment);
    }

    public int size() {
        return Math.toIntExact(layout.byteSize());
    }

    public void write() {
        for (Map.Entry<String, Field> e : fields.entrySet()) {
            try {
                Field f = e.getValue();
                Object javaValue = f.get(this);
                if (f.getType().isArray()) {
                    if (javaValue == null) throw new IllegalStateException("Array field " + f.getName() + " cannot be null");
                    writeArray(f, javaValue);
                    continue;
                }
                Object nativeValue = TypeMapper.toNative(javaValue, f.getType(), arena);
                handles.get(e.getKey()).set(segment, 0, nativeValue);
            } catch (IllegalAccessException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public void read() {
        for (Map.Entry<String, Field> e : fields.entrySet()) {
            try {
                Field f = e.getValue();
                if (f.getType().isArray()) {
                    readArray(f);
                    continue;
                }
                if (!TypeMapper.isReadable(f.getType())) continue;
                Object raw = handles.get(e.getKey()).get(segment, 0);
                f.set(this, TypeMapper.fromNative(raw, f.getType()));
            } catch (IllegalAccessException ex) {
                throw new RuntimeException(ex);
            }
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

    /**
     * Structure field order is manually set using field names
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface FieldOrder {
        String[] value();
    }

    /**
     * Structure field order is automatically set using the declared field order
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface AutoFieldOrder {
    }

    /// Specifies the length of an array
    /// (i.e: @ArrayLength(8)
    ///       private byte[] data;)
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface ArrayLength {
        int value();
    }
}