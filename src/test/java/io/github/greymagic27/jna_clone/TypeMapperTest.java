package io.github.greymagic27.jna_clone;

import io.github.greymagic27.jna_clone.CallbackTest.IntCallback;
import io.github.greymagic27.jna_clone.WinDef.BOOL;
import io.github.greymagic27.jna_clone.WinDef.BYTE;
import io.github.greymagic27.jna_clone.WinDef.HDC;
import io.github.greymagic27.jna_clone.WinDef.HINSTANCE;
import io.github.greymagic27.jna_clone.WinDef.HMENU;
import io.github.greymagic27.jna_clone.WinDef.HMODULE;
import io.github.greymagic27.jna_clone.WinDef.HWND;
import io.github.greymagic27.jna_clone.WinDef.LONG;
import io.github.greymagic27.jna_clone.WinDef.LPARAM;
import io.github.greymagic27.jna_clone.WinDef.LRESULT;
import io.github.greymagic27.jna_clone.WinDef.WPARAM;
import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TypeMapperTest {

    @Test
    void testLayoutMappings() {
        for (Class<?> type : List.of(int.class, Integer.class)) {
            assertEquals(ValueLayout.JAVA_INT, TypeMapper.layoutMappings(type));
        }
        for (Class<?> type : List.of(long.class, Long.class, LRESULT.class)) {
            assertEquals(ValueLayout.JAVA_LONG, TypeMapper.layoutMappings(type));
        }
        for (Class<?> type : List.of(short.class, Short.class)) {
            assertEquals(ValueLayout.JAVA_SHORT, TypeMapper.layoutMappings(type));
        }
        for (Class<?> type : List.of(byte.class, Byte.class)) {
            assertEquals(ValueLayout.JAVA_BYTE, TypeMapper.layoutMappings(type));
        }
        for (Class<?> type : List.of(boolean.class, Boolean.class, BOOL.class)) {
            assertEquals(ValueLayout.JAVA_INT, TypeMapper.layoutMappings(type));
        }
        for (Class<?> type : List.of(double.class, Double.class)) {
            assertEquals(ValueLayout.JAVA_DOUBLE, TypeMapper.layoutMappings(type));
        }
        for (Class<?> type : List.of(float.class, Float.class)) {
            assertEquals(ValueLayout.JAVA_FLOAT, TypeMapper.layoutMappings(type));
        }
        for (Class<?> type : List.of(void.class, Void.class)) {
            assertNull(TypeMapper.layoutMappings(type));
        }
        for (Class<?> type : List.of(String.class)) {
            assertEquals(ValueLayout.ADDRESS, TypeMapper.layoutMappings(type));
        }
        for (Class<?> type : List.of(Pointer.class, Structure.class, HANDLE.class)) {
            assertEquals(ValueLayout.ADDRESS, TypeMapper.layoutMappings(type));
        }
        for (Class<?> type : List.of(HWND.class, HDC.class)) {
            assertEquals(ValueLayout.ADDRESS, TypeMapper.layoutMappings(type));
        }
    }

    @Test
    void testLayoutMappingsInvalid() {
        assertThrows(IllegalArgumentException.class, () -> TypeMapper.layoutMappings(Object.class));
    }

    @Test
    void testToNative_String() {
        try (Arena arena = Arena.ofConfined()) {
            Object result = TypeMapper.toNative("test string", String.class, arena);
            assertInstanceOf(MemorySegment.class, result);
            assertNotEquals(MemorySegment.NULL, result);
        }
    }

    @Test
    void testToNative_Pointer() {
        try (Arena arena = Arena.ofConfined()) {
            MemorySegment segment = arena.allocate(4);
            Pointer ptr = new Pointer(segment);
            Object result = TypeMapper.toNative(ptr, Pointer.class, arena);
            assertEquals(segment, result);
        }
    }

    @Test
    void testToNative_Boolean() {
        try (Arena arena = Arena.ofConfined()) {
            assertEquals(1, TypeMapper.toNative(true, boolean.class, arena));
            assertEquals(0, TypeMapper.toNative(false, Boolean.class, arena));
        }
    }

    @Test
    void testToNative_Null() {
        try (Arena arena = Arena.ofConfined()) {
            assertEquals(MemorySegment.NULL, TypeMapper.toNative(null, String.class, arena));
            assertEquals(0, TypeMapper.toNative(null, Integer.class, arena));
        }
    }

    @Test
    void testToNative_Structure() {
        @SuppressWarnings("unused")
        @Structure.FieldOrder("x")
        class TestPoint extends Structure {
            private int x;
        }
        TestPoint point = new TestPoint();
        try (Arena arena = Arena.ofConfined()) {
            Object result = TypeMapper.toNative(point, Structure.class, arena);
            assertInstanceOf(MemorySegment.class, result);
            assertEquals(point.pointer().segment, result);
        }
    }

    @Test
    void testToNative_Handle() {
        try (Arena arena = Arena.ofConfined()) {
            MemorySegment segment = arena.allocate(8);
            HANDLE handle = new HANDLE(segment);
            assertEquals(segment, TypeMapper.toNative(handle, HANDLE.class, arena));
        }
    }

    @Test
    void testToNative_HWND() {
        try (Arena arena = Arena.ofConfined()) {
            MemorySegment segment = arena.allocate(8);
            HWND hwnd = new HWND(segment);
            assertEquals(segment, TypeMapper.toNative(hwnd, HWND.class, arena));
        }
    }

    @Test
    void testToNative_HDC() {
        try (Arena arena = Arena.ofConfined()) {
            MemorySegment segment = arena.allocate(8);
            HDC hdc = new HDC(segment);
            assertEquals(segment, TypeMapper.toNative(hdc, HDC.class, arena));
        }
    }

    @Test
    void testToNative_HMENU() {
        try (Arena arena = Arena.ofConfined()) {
            MemorySegment segment = arena.allocate(8);
            HMENU hmenu = new HMENU(segment);
            assertEquals(segment, TypeMapper.toNative(hmenu, HMENU.class, arena));
        }
    }

    @Test
    void testToNative_HandleNull() {
        try (Arena arena = Arena.ofConfined()) {
            assertEquals(MemorySegment.NULL, TypeMapper.toNative(null, HANDLE.class, arena));
            assertEquals(MemorySegment.NULL, TypeMapper.toNative(null, HWND.class, arena));
            assertEquals(MemorySegment.NULL, TypeMapper.toNative(null, HDC.class, arena));
            assertEquals(MemorySegment.NULL, TypeMapper.toNative(null, HMENU.class, arena));
        }
    }

    @Test
    void testToNative_PointerNull() {
        try (Arena arena = Arena.ofConfined()) {
            assertEquals(MemorySegment.NULL, TypeMapper.toNative(null, Pointer.class, arena));
        }
    }

    @Test
    void testToNative_StructureNull() {
        try (Arena arena = Arena.ofConfined()) {
            assertEquals(MemorySegment.NULL, TypeMapper.toNative(null, Structure.class, arena));
        }
    }

    @Test
    void testToNative_BOOL() {
        try (Arena arena = Arena.ofConfined()) {
            assertEquals(1, TypeMapper.toNative(new BOOL(1), BOOL.class, arena));
            assertEquals(0, TypeMapper.toNative(new BOOL(0), BOOL.class, arena));
        }
    }

    @Test
    void testToNative_BOOLAnyNonZeroIsTrue() {
        try (Arena arena = Arena.ofConfined()) {
            assertEquals(99, TypeMapper.toNative(new BOOL(99), BOOL.class, arena));
        }
    }

    @Test
    void testToNativeBOOLNull() {
        try (Arena arena = Arena.ofConfined()) {
            assertEquals(0, TypeMapper.toNative(null, BOOL.class, arena));
        }
    }

    @Test
    void testToNative_LRESULT() {
        try (Arena arena = Arena.ofConfined()) {
            LRESULT value = new LRESULT(9999L);
            Object result = TypeMapper.toNative(value, LRESULT.class, arena);
            assertEquals(9999L, result);
        }
    }

    @Test
    void testToNative_LRESULTNull() {
        try (Arena arena = Arena.ofConfined()) {
            assertEquals(0, TypeMapper.toNative(null, LRESULT.class, arena));
        }
    }

    @Test
    void testToNative_LPARAM() {
        try (Arena arena = Arena.ofConfined()) {
            LPARAM value = new LPARAM(9999L);
            Object result = TypeMapper.toNative(value, LPARAM.class, arena);
            assertEquals(9999L, result);
        }
    }

    @Test
    void testToNative_LPARAMNull() {
        try (Arena arena = Arena.ofConfined()) {
            assertEquals(0, TypeMapper.toNative(null, LPARAM.class, arena));
        }
    }

    @Test
    void testToNative_LONG() {
        try (Arena arena = Arena.ofConfined()) {
            LONG value = new LONG(9999L);
            Object result = TypeMapper.toNative(value, LONG.class, arena);
            assertEquals(9999L, result);
        }
    }

    @Test
    void testToNative_LONGNull() {
        try (Arena arena = Arena.ofConfined()) {
            assertEquals(0, TypeMapper.toNative(null, LONG.class, arena));
        }
    }

    @Test
    void testToNative_WPARAM() {
        try (Arena arena = Arena.ofConfined()) {
            WPARAM value = new WPARAM(9999);
            Object result = TypeMapper.toNative(value, WPARAM.class, arena);
            assertEquals(9999, result);
        }
    }

    @Test
    void testToNative_WPARAMNull() {
        try (Arena arena = Arena.ofConfined()) {
            assertEquals(0, TypeMapper.toNative(null, WPARAM.class, arena));
        }
    }

    @Test
    void testToNative_HINSTANCE() {
        try (Arena arena = Arena.ofConfined()) {
            MemorySegment segment = arena.allocate(8);
            HINSTANCE hinstance = new HINSTANCE(segment);
            assertEquals(segment, TypeMapper.toNative(hinstance, HINSTANCE.class, arena));
        }
    }

    @Test
    void testToNative_HINSTANCENull() {
        try (Arena arena = Arena.ofConfined()) {
            assertEquals(MemorySegment.NULL, TypeMapper.toNative(null, HINSTANCE.class, arena));
        }
    }

    @Test
    void testToNative_HMODULE() {
        try (Arena arena = Arena.ofConfined()) {
            MemorySegment segment = arena.allocate(8);
            HMODULE hModule = new HMODULE(segment);
            assertEquals(segment, TypeMapper.toNative(hModule, HMODULE.class, arena));
        }
    }

    @Test
    void testToNative_HMODULENull() {
        try (Arena arena = Arena.ofConfined()) {
            assertEquals(MemorySegment.NULL, TypeMapper.toNative(null, HMODULE.class, arena));
        }
    }

    @Test
    void testToNative_BYTE() {
        try (Arena arena = Arena.ofConfined()) {
            BYTE value = new BYTE((byte) 15);
            Object result = TypeMapper.toNative(value, BYTE.class, arena);
            assertEquals((byte) 15, result);
        }
    }

    @Test
    void testToNative_BYTENull() {
        try (Arena arena = Arena.ofConfined()) {
            assertEquals(0, TypeMapper.toNative(null, BYTE.class, arena));
        }
    }

    @Test
    void testToNative_Callback() {
        IntCallback cb = (v) -> v;
        assertEquals(ValueLayout.ADDRESS, TypeMapper.layoutMappings(IntCallback.class));
        try (Arena arena = Arena.ofConfined()) {
            Object nativeValue = TypeMapper.toNative(cb, IntCallback.class, arena);
            assertInstanceOf(MemorySegment.class, nativeValue);
            MemorySegment segment = (MemorySegment) nativeValue;
            assertNotEquals(MemorySegment.NULL, segment, "Stub segment should not be NULL");
            MemorySegment expected = CallbackReference.getStub(cb, CallbackReference.descriptorFor(IntCallback.class));
            assertEquals(expected.address(), segment.address());
        }
    }

    @Test
    void testFromNative_Pointer() {
        MemorySegment segment = MemorySegment.NULL;
        Object result = TypeMapper.fromNative(segment, Pointer.class);
        assertInstanceOf(Pointer.class, result);
        assertTrue(((Pointer) result).isNull());
    }

    @Test
    void testFromNative_Boolean() {
        assertEquals(true, TypeMapper.fromNative(1, boolean.class));
        assertEquals(false, TypeMapper.fromNative(0, Boolean.class));
    }

    @Test
    void testFromNative_Void() {
        assertNull(TypeMapper.fromNative(null, void.class));
    }

    @Test
    void testFromNative_Identity() {
        String test = "test";
        assertEquals(test, TypeMapper.fromNative(test, String.class));
    }

    @Test
    void testFromNative_HWND() {
        MemorySegment segment = MemorySegment.ofAddress(0x9999);
        Object result = TypeMapper.fromNative(segment, HWND.class);
        assertInstanceOf(HWND.class, result);
        assertEquals(0x9999, ((HWND) result).segment.address());
    }

    @Test
    void testFromNative_HDC() {
        MemorySegment segment = MemorySegment.ofAddress(0x9999);
        Object result = TypeMapper.fromNative(segment, HDC.class);
        assertInstanceOf(HDC.class, result);
        assertEquals(0x9999L, ((HDC) result).segment.address());
    }

    @Test
    void testFromNative_HMENU() {
        MemorySegment segment = MemorySegment.ofAddress(0x9999);
        Object result = TypeMapper.fromNative(segment, HMENU.class);
        assertInstanceOf(HMENU.class, result);
        assertEquals(0x9999L, ((HMENU) result).segment.address());
    }

    @Test
    void testFromNative_HandleMissingConstructor() {
        class Bad extends HANDLE {
            Bad() {
                super(MemorySegment.NULL);
            }
        }
        RuntimeException e = assertThrows(RuntimeException.class, () -> TypeMapper.fromNative(MemorySegment.NULL, Bad.class));
        assertTrue(e.getMessage().contains("MemorySegment"));
    }

    @Test
    void testFromNative_BOOL() {
        Object result = TypeMapper.fromNative(1, BOOL.class);
        Object result2 = TypeMapper.fromNative(0, BOOL.class);
        assertInstanceOf(BOOL.class, result);
        assertInstanceOf(BOOL.class, result2);
        assertTrue(((BOOL) result).booleanValue());
        assertFalse(((BOOL) result2).booleanValue());
    }

    @Test
    void testFromNative_BOOLAnyNonZeroIsTrue() {
        Object result = TypeMapper.fromNative(-1, BOOL.class);
        assertInstanceOf(BOOL.class, result);
        assertTrue(((BOOL) result).booleanValue());
    }

    @Test
    void testFromNative_LRESULT() {
        Object result = TypeMapper.fromNative(9999L, LRESULT.class);
        assertInstanceOf(LRESULT.class, result);
        assertEquals(9999L, ((LRESULT) result).longValue());
    }

    @Test
    void testFromNative_LPARAM() {
        Object result = TypeMapper.fromNative(9999L, LPARAM.class);
        assertInstanceOf(LPARAM.class, result);
        assertEquals(9999L, ((LPARAM) result).longValue());
    }

    @Test
    void testFromNative_LONG() {
        Object result = TypeMapper.fromNative(9999L, LONG.class);
        assertInstanceOf(LONG.class, result);
        assertEquals(9999L, ((LONG) result).longValue());
    }

    @Test
    void testFromNative_WPARAM() {
        Object result = TypeMapper.fromNative(9999, WPARAM.class);
        assertInstanceOf(WPARAM.class, result);
        assertEquals(9999, ((WPARAM) result).intValue());
    }

    @Test
    void testFromNative_HINSTANCE() {
        MemorySegment segment = MemorySegment.ofAddress(0x1234);
        Object result = TypeMapper.fromNative(segment, HINSTANCE.class);
        assertInstanceOf(HINSTANCE.class, result);
        assertEquals(0x1234L, ((HINSTANCE) result).segment.address());
    }

    @Test
    void testFromNative_HMODULE() {
        MemorySegment segment = MemorySegment.ofAddress(0x5678);
        Object result = TypeMapper.fromNative(segment, HMODULE.class);
        assertInstanceOf(HMODULE.class, result);
        assertEquals(0x5678L, ((HMODULE) result).segment.address());
    }

    @Test
    void testFromNative_BYTE() {
        Object result = TypeMapper.fromNative((byte) 15, BYTE.class);
        assertInstanceOf(BYTE.class, result);
        assertEquals(15, ((BYTE) result).byteValue());
    }

    @Test
    void testPrimitiveReturns() {
        Object floatResult = TypeMapper.fromNative(9999f,  float.class);
        assertInstanceOf(Float.class, floatResult);
        assertEquals(9999f, (Float) floatResult, 0.001f);
        assertEquals((short) 5, TypeMapper.fromNative((short) 5, short.class));
        assertEquals(true, TypeMapper.fromNative(1, boolean.class));
    }
}