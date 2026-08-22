package io.github.greymagic27.win_method;

import io.github.greymagic27.win_method.BaseTsd.LONG_PTR;
import io.github.greymagic27.win_method.BaseTsd.UINT_PTR;
import io.github.greymagic27.win_method.IntSafe.DWORD;
import io.github.greymagic27.win_method.WinDef.BOOL;
import io.github.greymagic27.win_method.WinDef.BYTE;
import io.github.greymagic27.win_method.WinDef.UINT;
import io.github.greymagic27.win_method.WinDef.WORD;
import io.github.greymagic27.win_method.WinNT.LONG;
import io.github.greymagic27.win_method.WinNT.LPCWSTR;
import io.github.greymagic27.win_method.WinNT.LPWSTR;
import io.github.greymagic27.win_method.WinNT.SHORT;
import io.github.greymagic27.win_method.WinNT.WCHAR;
import java.lang.foreign.Arena;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.nio.charset.StandardCharsets;
import org.jspecify.annotations.Nullable;

public final class TypeMapper {

    static @Nullable MemoryLayout layoutMappings(Class<?> javaType) {
        if (javaType == int.class || javaType == Integer.class || javaType == LONG.class) return ValueLayout.JAVA_INT;
        if (javaType == boolean.class || javaType == Boolean.class || javaType == BOOL.class || javaType == UINT.class) return ValueLayout.JAVA_INT;
        if (javaType == long.class || javaType == Long.class) return ValueLayout.JAVA_LONG;
        if (javaType == short.class || javaType == Short.class || javaType == SHORT.class) return ValueLayout.JAVA_SHORT;
        if (javaType == byte.class || javaType == Byte.class || javaType == BYTE.class) return ValueLayout.JAVA_BYTE;
        if (javaType == double.class || javaType == Double.class) return ValueLayout.JAVA_DOUBLE;
        if (javaType == float.class || javaType == Float.class) return ValueLayout.JAVA_FLOAT;
        if (javaType == char.class || javaType == Character.class || javaType == WCHAR.class) return ValueLayout.JAVA_CHAR;
        if (javaType == String.class) return ValueLayout.ADDRESS;
        if (Structure.class.isAssignableFrom(javaType) || Callback.class.isAssignableFrom(javaType) || Pointer.class.isAssignableFrom(javaType)) return ValueLayout.ADDRESS;
        if (WORD.class.isAssignableFrom(javaType)) return ValueLayout.JAVA_SHORT;
        if (DWORD.class.isAssignableFrom(javaType)) return ValueLayout.JAVA_INT;
        if (LONG_PTR.class.isAssignableFrom(javaType) || UINT_PTR.class.isAssignableFrom(javaType)) return ValueLayout.JAVA_LONG;
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
            return defaultNativeValue(javaType);
        }
        if ((javaType == LPWSTR.class || javaType == LPCWSTR.class) && value instanceof String s) {
            return callArena.allocateFrom(s, StandardCharsets.UTF_16LE);
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
        if (DWORD.class.isAssignableFrom(javaType)) {
            return ((DWORD) value).intValue();
        }
        if (LONG_PTR.class.isAssignableFrom(javaType)) {
            return ((LONG_PTR) value).longValue();
        }
        if (UINT_PTR.class.isAssignableFrom(javaType)) {
            return ((UINT_PTR) value).longValue();
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
        if (javaType == LONG.class) {
            return ((LONG) value).intValue();
        }
        if (javaType == BYTE.class) {
            return ((BYTE) value).byteValue();
        }
        if (javaType == SHORT.class) {
            return ((SHORT) value).shortValue();
        }
        if (javaType == UINT.class) {
            return ((UINT) value).intValue();
        }
        if (javaType == WCHAR.class) {
            return ((WCHAR) value).charValue();
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
        if (DWORD.class.isAssignableFrom(returnType)) {
            try {
                int value = (int)raw;
                return returnType.getConstructor(int.class).newInstance(value);
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException("DWORD subclass " + returnType + "needs an (int) constructor", e);
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
        if (LONG_PTR.class.isAssignableFrom(returnType) || UINT_PTR.class.isAssignableFrom(returnType)) {
            try {
                long value = (long) raw;
                return returnType.getConstructor(long.class).newInstance(value);
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException(returnType + " needs a (long) constructor", e);
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
        if (returnType == BYTE.class) {
            return new BYTE((Byte) raw);
        }
        if (returnType == LONG.class) {
            return new LONG((Integer) raw);
        }
        if (returnType == SHORT.class) {
            return new SHORT((Short) raw);
        }
        if (returnType == DWORD.class) {
            return new DWORD((Integer) raw);
        }
        if (returnType == UINT.class) {
            return new UINT((Integer) raw);
        }
        if (returnType == WCHAR.class) {
            return new WCHAR((Character) raw);
        }
        return raw;
    }

    static @Nullable Object defaultNativeValue(Class<?> javaType) {
        MemoryLayout layout = layoutMappings(javaType);
        if (layout == null) return null;
        if (layout.equals(ValueLayout.JAVA_LONG)) {
            return 0L;
        }
        if (layout.equals(ValueLayout.JAVA_INT)) {
            return 0;
        }
        if (layout.equals(ValueLayout.JAVA_SHORT)) {
            return (short) 0;
        }
        if (layout.equals(ValueLayout.JAVA_BYTE)) {
            return (byte) 0;
        }
        if (layout.equals(ValueLayout.JAVA_FLOAT)) {
            return 0.0f;
        }
        if (layout.equals(ValueLayout.JAVA_DOUBLE)) {
            return 0.0d;
        }
        if (layout.equals(ValueLayout.JAVA_CHAR)) {
            return '\0';
        }
        if (layout.equals(ValueLayout.ADDRESS)) {
            return MemorySegment.NULL;
        }
        throw new IllegalArgumentException("Unsupported default layout: " + layout);
    }
}