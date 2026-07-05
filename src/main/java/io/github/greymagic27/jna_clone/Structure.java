package io.github.greymagic27.jna_clone;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.foreign.Arena;
import java.lang.foreign.GroupLayout;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.invoke.VarHandle;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

public abstract class Structure {

    private final Arena arena;
    private final MemorySegment segment;
    private final MemoryLayout layout;
    private final Map<String, VarHandle> handles = new LinkedHashMap<>();
    private final Map<String, Field> fields = new LinkedHashMap<>();

    protected Structure() {
        this(Arena.ofAuto());
    }

    public Structure(@NotNull Arena arena) {
        this.arena = arena;
        GroupLayout layout = buildLayout();
        this.layout = layout;
        this.segment = arena.allocate(layout);
    }

    private GroupLayout buildLayout() {
        List<Field> orderedFields = resolveFieldOrder();
        validateFieldCount(orderedFields);
        List<MemoryLayout> members = new ArrayList<>();
        long currentOffset = 0;
        long maxAlign = 1;
        for (Field f : orderedFields) {
            f.setAccessible(true);
            MemoryLayout ml = TypeMapper.layoutMappings(f.getType());
            if (ml == null) throw new IllegalStateException("Unsupported struct field type: " + f.getType() + " on " + f.getName());
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
        for (MemoryLayout ml : members) {
            if (ml.name().isEmpty()) continue;
            String name = ml.name().orElseThrow();
            handles.put(name, group.varHandle(MemoryLayout.PathElement.groupElement(name)));
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
            if (!missing.isEmpty()) sb.append(System.lineSeparator()).append(System.lineSeparator()).append("Missing from @FieldOrder").append(missing);
            if (!unexpected.isEmpty()) sb.append(System.lineSeparator()).append("Not a declared field: ").append(unexpected);
            throw new IllegalStateException(sb.toString());
        }
    }

    private @NotNull List<Field> resolveFieldOrder() {
        FieldOrder order = getClass().getAnnotation(FieldOrder.class);
        if (order == null) {
            throw new IllegalStateException(getClass().getSimpleName() + " must be annotated with @FieldOrder");
        }
        List<Field> resolved = new ArrayList<>();
        List<String> missing = new ArrayList<>();
        for (String name : order.value()) {
            try {
                resolved.add(getClass().getDeclaredField(name));
            } catch (NoSuchFieldException e) {
                missing.add(name);
            }
        }
        if (!missing.isEmpty()) {
            throw new RuntimeException("@FieldOrder on " + getClass().getSimpleName() + " names " + missing + ", but no such field(s) exist");
        }
        return resolved;
    }

    public Pointer pointer() {
        write();
        return new Pointer(segment);
    }

    public long size() {
        return layout.byteSize();
    }

    public void write() {
        Arena callArena = arena != null ? arena : Arena.ofAuto();
        for (Map.Entry<String, Field> e : fields.entrySet()) {
            try {
                Field f = e.getValue();
                Object javaValue = f.get(this);
                Object nativeValue = TypeMapper.toNative(javaValue, f.getType(), callArena);
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

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface FieldOrder {
        String[] value();
    }
}