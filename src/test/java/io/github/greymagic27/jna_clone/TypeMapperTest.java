package io.github.greymagic27.jna_clone;

import io.github.greymagic27.jna_clone.WinDef.HDC;
import io.github.greymagic27.jna_clone.WinDef.HWND;
import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.util.List;
import org.junit.jupiter.api.Test;

import static java.lang.foreign.ValueLayout.JAVA_FLOAT;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
        for (Class<?> type : List.of(long.class, Long.class)) {
            assertEquals(ValueLayout.JAVA_LONG, TypeMapper.layoutMappings(type));
        }
        for (Class<?> type : List.of(short.class, Short.class)) {
            assertEquals(ValueLayout.JAVA_SHORT, TypeMapper.layoutMappings(type));
        }
        for (Class<?> type : List.of(byte.class, Byte.class)) {
            assertEquals(ValueLayout.JAVA_BYTE, TypeMapper.layoutMappings(type));
        }
        for (Class<?> type : List.of(boolean.class, Boolean.class)) {
            assertEquals(ValueLayout.JAVA_INT, TypeMapper.layoutMappings(type));
        }
        for (Class<?> type : List.of(double.class, Double.class)) {
            assertEquals(ValueLayout.JAVA_DOUBLE, TypeMapper.layoutMappings(type));
        }
        for (Class<?> type : List.of(float.class, Float.class)) {
            assertEquals(JAVA_FLOAT, TypeMapper.layoutMappings(type));
        }
        for (Class<?> type : List.of(void.class, Void.class)) {
            assertNull(TypeMapper.layoutMappings(type));
        }
        for (Class<?> type : List.of(String.class)) {
            assertEquals(ValueLayout.ADDRESS, TypeMapper.layoutMappings(type));
        }
        for (Class<?> type : List.of(Pointer.class, Structure.class, Handle.class)) {
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
        TestPoint point = new TestPoint();
        point.x = 10;
        point.y = 20;
        try (Arena arena = Arena.ofConfined()) {
            Object result = TypeMapper.toNative(point, Structure.class, arena);
            assertInstanceOf(MemorySegment.class, result);
            assertEquals(point.pointer().segment(), result);
        }
    }

    @Test
    void testToNative_Handle() {
        try (Arena arena = Arena.ofConfined()) {
            MemorySegment segment = arena.allocate(8);
            Handle handle = new Handle(segment);
            assertEquals(segment, TypeMapper.toNative(handle, Handle.class, arena));
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
    void testToNative_HandleNull() {
        try (Arena arena = Arena.ofConfined()) {
            assertEquals(MemorySegment.NULL, TypeMapper.toNative(null, Handle.class, arena));
            assertEquals(MemorySegment.NULL, TypeMapper.toNative(null, HWND.class, arena));
            assertEquals(MemorySegment.NULL, TypeMapper.toNative(null, HDC.class, arena));
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
        assertEquals(0x9999, ((HWND) result).segment().address());
    }

    @Test
    void testFromNative_HDC() {
        MemorySegment segment = MemorySegment.ofAddress(0x9999);
        Object result = TypeMapper.fromNative(segment, HDC.class);
        assertInstanceOf(HDC.class, result);
        assertEquals(0x9999L, ((HDC) result).segment().address());
    }

    @Test
    void testFromNative_HandleMissingConstructor() {
        class Bad extends Handle {
            Bad() {
                super(MemorySegment.NULL);
            }
        }
        RuntimeException e = assertThrows(RuntimeException.class, () -> TypeMapper.fromNative(MemorySegment.NULL, Bad.class));
        assertTrue(e.getMessage().contains("MemorySegment"));
    }

    @Test
    void testFromNative_PointerMissingConstructor() {
        class Bad extends Handle {
            Bad() {
                super(MemorySegment.NULL);
            }
        }
        RuntimeException e = assertThrows(RuntimeException.class, () -> TypeMapper.fromNative(MemorySegment.NULL, Bad.class));
        assertTrue(e.getMessage().contains("MemorySegment"));
    }

    @SuppressWarnings("DataFlowIssue")
    @Test
    void testPrimitiveReturns() {
        assertEquals(5, TypeMapper.fromNative(5, short.class));
        assertEquals(3.14f, (Float) TypeMapper.fromNative(3.14f, float.class), 0.001f);
        assertEquals(true, TypeMapper.fromNative(1, boolean.class));
    }

    @SuppressWarnings("unused")
    @Structure.FieldOrder({"x", "y"})
    private static class TestPoint extends Structure {
        private int x;
        private int y;
    }
}