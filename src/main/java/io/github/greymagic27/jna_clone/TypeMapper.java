package io.github.greymagic27.jna_clone;

import java.lang.foreign.Arena;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.nio.charset.StandardCharsets;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

public final class TypeMapper {

    static @Nullable MemoryLayout layoutMappings(Class<?> javaType) {
        if (javaType == int.class || javaType == Integer.class) return ValueLayout.JAVA_INT;
        if (javaType == long.class || javaType == Long.class) return ValueLayout.JAVA_LONG;
        if (javaType == short.class || javaType == Short.class) return ValueLayout.JAVA_SHORT;
        if (javaType == byte.class || javaType == Byte.class) return ValueLayout.JAVA_BYTE;
        if (javaType == boolean.class || javaType == Boolean.class) return ValueLayout.JAVA_INT;
        if (javaType == double.class || javaType == Double.class) return ValueLayout.JAVA_DOUBLE;
        if (javaType == float.class || javaType == Float.class) return ValueLayout.JAVA_FLOAT;
        if (javaType == void.class || javaType == Void.class) return null;
        if (javaType == String.class) return ValueLayout.ADDRESS;
        if (javaType == Pointer.class) return ValueLayout.ADDRESS;
        if (javaType == Structure.class) return ValueLayout.ADDRESS;
        throw new IllegalArgumentException("No native layout mapping for: " + javaType);
    }

    static Object toNative(Object value, Class<?> javaType, Arena callArena) {
        if (value == null) {
            return javaType == String.class || javaType == Pointer.class ? MemorySegment.NULL : 0;
        }
        if (javaType == String.class) {
            return callArena.allocateFrom((String) value, StandardCharsets.UTF_16LE);
        }
        if (javaType == Pointer.class) {
            return ((Pointer) value).segment();
        }
        if (javaType == Structure.class) {
            return ((Structure) value).pointer().segment();
        }
        if (javaType == Boolean.class || javaType == boolean.class) {
            return ((Boolean) value) ? 1 : 0;
        }
        return value;
    }

    @Contract(pure = true)
    static @Nullable Object fromNative(Object raw, Class<?> returnType) {
        if (returnType == void.class || returnType == Void.class) {
            return null;
        }
        if (returnType == Pointer.class) {
            return new Pointer((MemorySegment) raw);
        }
        if (returnType == Boolean.class || returnType == boolean.class) {
            return ((Integer) raw) != 0;
        }
        return raw;
    }
}
