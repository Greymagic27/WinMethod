package io.github.greymagic27.jna_clone;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.util.List;
import org.junit.jupiter.api.Test;

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
            assertEquals(ValueLayout.JAVA_FLOAT, TypeMapper.layoutMappings(type));
        }
        for (Class<?> type : List.of(void.class, Void.class)) {
            assertNull(TypeMapper.layoutMappings(type));
        }
        for (Class<?> type : List.of(String.class, Pointer.class, Structure.class)) {
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

    @SuppressWarnings("unused")
    @Structure.FieldOrder({"x", "y"})
    private static class TestPoint extends Structure {
        private int x;
        private int y;
    }
}