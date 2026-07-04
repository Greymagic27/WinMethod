package io.github.greymagic27.jna_clone;

import io.github.greymagic27.jna_clone.WinDef.BOOL;
import io.github.greymagic27.jna_clone.WinDef.LONG;
import io.github.greymagic27.jna_clone.WinDef.LPARAM;
import io.github.greymagic27.jna_clone.WinDef.LRESULT;
import io.github.greymagic27.jna_clone.WinDef.WPARAM;
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
        if (javaType == boolean.class || javaType == Boolean.class || javaType == BOOL.class || javaType == WPARAM.class) return ValueLayout.JAVA_INT;
        if (javaType == long.class || javaType == Long.class || javaType == LRESULT.class || javaType == LPARAM.class || javaType == LONG.class) return ValueLayout.JAVA_LONG;
        if (javaType == short.class || javaType == Short.class) return ValueLayout.JAVA_SHORT;
        if (javaType == byte.class || javaType == Byte.class) return ValueLayout.JAVA_BYTE;
        if (javaType == double.class || javaType == Double.class) return ValueLayout.JAVA_DOUBLE;
        if (javaType == float.class || javaType == Float.class) return ValueLayout.JAVA_FLOAT;
        if (javaType == String.class || javaType == Pointer.class) return ValueLayout.ADDRESS;
        if (Structure.class.isAssignableFrom(javaType) || HANDLE.class.isAssignableFrom(javaType)) return ValueLayout.ADDRESS;
        if (javaType == void.class || javaType == Void.class) return null;
        throw new IllegalArgumentException("No native layout mapping for: " + javaType);
    }

    static Object toNative(Object value, Class<?> javaType, Arena callArena) {
        if (value == null) {
            boolean addressType = javaType == String.class || javaType == Pointer.class || Structure.class.isAssignableFrom(javaType) || HANDLE.class.isAssignableFrom(javaType);
            return addressType ? MemorySegment.NULL : 0;
        }
        if (javaType == String.class) {
            return callArena.allocateFrom((String) value, StandardCharsets.UTF_16LE);
        }
        if (javaType == Pointer.class) {
            return ((Pointer) value).segment();
        }
        if (Structure.class.isAssignableFrom(javaType)) {
            return ((Structure) value).pointer().segment();
        }
        if (HANDLE.class.isAssignableFrom(javaType)) {
            return ((HANDLE) value).segment();
        }
        if (javaType == Boolean.class || javaType == boolean.class) {
            return ((Boolean) value) ? 1 : 0;
        }
        if (javaType == BOOL.class) {
            return ((BOOL) value).intValue();
        }
        if (javaType == LRESULT.class) {
            return ((LRESULT) value).longValue();
        }
        if (javaType == LPARAM.class) {
            return ((LPARAM) value).longValue();
        }
        if (javaType == LONG.class) {
            return ((LONG) value).longValue();
        }
        if (javaType == WPARAM.class) {
            return ((WPARAM) value).intValue();
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
        if (HANDLE.class.isAssignableFrom(returnType)) {
            try {
                MemorySegment segment = (MemorySegment) raw;
                return returnType.getConstructor(MemorySegment.class).newInstance(segment);
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException("HANDLE subclass " + returnType + " needs a (MemorySegment) constructor", e);
            }
        }
        if (returnType == Boolean.class || returnType == boolean.class) {
            return ((Integer) raw) != 0;
        }
        if (returnType == BOOL.class) {
            return new BOOL((Integer) raw);
        }
        if (returnType == LRESULT.class) {
            return new LRESULT((Long) raw);
        }
        if (returnType == LPARAM.class) {
            return new LPARAM((Long) raw);
        }
        if (returnType == LONG.class) {
            return new LONG((Long) raw);
        }
        if (returnType == WPARAM.class) {
            return new WPARAM((Integer) raw);
        }
        return raw;
    }
}
