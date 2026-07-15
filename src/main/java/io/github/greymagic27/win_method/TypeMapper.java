package io.github.greymagic27.win_method;

import io.github.greymagic27.win_method.WinDef.BOOL;
import io.github.greymagic27.win_method.WinDef.BYTE;
import io.github.greymagic27.win_method.WinDef.LONG;
import io.github.greymagic27.win_method.WinDef.LPARAM;
import io.github.greymagic27.win_method.WinDef.LRESULT;
import io.github.greymagic27.win_method.WinDef.WORD;
import io.github.greymagic27.win_method.WinDef.WPARAM;
import java.lang.foreign.Arena;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.nio.charset.StandardCharsets;
import org.jspecify.annotations.Nullable;

public final class TypeMapper {

    static @Nullable MemoryLayout layoutMappings(Class<?> javaType) {
        if (javaType == int.class || javaType == Integer.class || javaType == LONG.class) return ValueLayout.JAVA_INT;
        if (javaType == boolean.class || javaType == Boolean.class || javaType == BOOL.class) return ValueLayout.JAVA_INT;
        if (javaType == long.class || javaType == Long.class || javaType == LRESULT.class || javaType == LPARAM.class || javaType == WPARAM.class) return ValueLayout.JAVA_LONG;
        if (javaType == short.class || javaType == Short.class) return ValueLayout.JAVA_SHORT;
        if (javaType == byte.class || javaType == Byte.class || javaType == BYTE.class) return ValueLayout.JAVA_BYTE;
        if (javaType == double.class || javaType == Double.class) return ValueLayout.JAVA_DOUBLE;
        if (javaType == float.class || javaType == Float.class) return ValueLayout.JAVA_FLOAT;
        if (javaType == String.class) return ValueLayout.ADDRESS;
        if (Structure.class.isAssignableFrom(javaType) || Callback.class.isAssignableFrom(javaType) || Pointer.class.isAssignableFrom(javaType)) return ValueLayout.ADDRESS;
        if (WORD.class.isAssignableFrom(javaType)) return ValueLayout.JAVA_SHORT;
        if (javaType == void.class || javaType == Void.class) return null;
        throw new IllegalArgumentException("No native layout mapping for: " + javaType);
    }

    static boolean isReadable(Class<?> type) {
        if (Callback.class.isAssignableFrom(type)) return false;
        if (type == String.class) return false;
        return !Structure.class.isAssignableFrom(type);
    }

    static Object toNative(Object value, Class<?> javaType, Arena callArena) {
        if (value == null) {
            boolean addressType = javaType == String.class || Pointer.class.isAssignableFrom(javaType) || Structure.class.isAssignableFrom(javaType) || Callback.class.isAssignableFrom(javaType);
            return addressType ? MemorySegment.NULL : 0;
        }
        if (Structure.class.isAssignableFrom(javaType)) {
            return ((Structure) value).pointer().segment;
        }
        if (Pointer.class.isAssignableFrom(javaType)) {
            return ((Pointer) value).segment;
        }
        if (Callback.class.isAssignableFrom(javaType)) {
            return CallbackReference.getStub((Callback) value, CallbackReference.descriptorFor(javaType));
        }
        if (WORD.class.isAssignableFrom(javaType)) {
            return ((WORD) value).shortValue();
        }
        if (javaType == Boolean.class || javaType == boolean.class) {
            return ((Boolean) value) ? 1 : 0;
        }
        if (javaType == String.class) {
            return callArena.allocateFrom((String) value, StandardCharsets.UTF_16LE);
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
            return ((LONG) value).intValue();
        }
        if (javaType == WPARAM.class) {
            return ((WPARAM) value).longValue();
        }
        if (javaType == BYTE.class) {
            return ((BYTE) value).byteValue();
        }
        return value;
    }

    static @Nullable Object fromNative(Object raw, Class<?> returnType) {
        if (returnType == void.class || returnType == Void.class) {
            return null;
        }
        if (Pointer.class.isAssignableFrom(returnType)) {
            try {
                MemorySegment segment = (MemorySegment) raw;
                return returnType.getConstructor(MemorySegment.class).newInstance(segment);
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException(returnType + " needs a (MemorySegment) constructor", e);
            }
        }
        if (WORD.class.isAssignableFrom(returnType)) {
            try {
                short value = (short) raw;
                return returnType.getConstructor(short.class).newInstance(value);
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException("WORD subclass " + returnType + " needs a (short) constructor", e);
            }
        }
        if (Structure.class.isAssignableFrom(returnType)) {
            try {
                Structure structure = (Structure) returnType.getConstructor().newInstance();
                structure.useMemory((MemorySegment) raw);
                return structure;
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException(returnType + "needs a (MemorySegment) constructor", e);
            }
        }
        if (returnType == String.class) {
            if (raw == null || raw.equals(MemorySegment.NULL)) return null;
            MemorySegment segment = (MemorySegment) raw;
            return segment.getString(0, StandardCharsets.UTF_16LE);
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
            return new LONG((Integer) raw);
        }
        if (returnType == WPARAM.class) {
            return new WPARAM((Long) raw);
        }
        if (returnType == BYTE.class) {
            return new BYTE((Byte) raw);
        }
        return raw;
    }
}