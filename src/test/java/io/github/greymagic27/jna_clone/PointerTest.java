package io.github.greymagic27.jna_clone;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

import static java.lang.foreign.ValueLayout.JAVA_BYTE;
import static java.lang.foreign.ValueLayout.JAVA_INT;
import static java.lang.foreign.ValueLayout.JAVA_LONG;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PointerTest {

    @Test
    void testNullPointer() {
        Pointer ptr = Pointer.NULL;
        assertTrue(ptr.isNull(), "Pointer.NULL should be null");
        assertEquals(0, ptr.segment().address());
        assertEquals("Pointer@0x0", ptr.toString());
    }

    @Test
    void testIntOperations() {
        try (Arena arena = Arena.ofConfined()) {
            MemorySegment segment = arena.allocate(JAVA_INT);
            Pointer ptr = new Pointer(segment);
            assertFalse(ptr.isNull());
            ptr.setInt(0, 42);
            assertEquals(42, ptr.getInt(0));
            MemorySegment multiSegment = arena.allocate(JAVA_INT, 2);
            Pointer ptr2 = new Pointer(multiSegment);
            ptr2.setInt(4, 99);
            assertEquals(99, ptr.getInt(4));
        }
    }

    @Test
    void testLongOperations() {
        try (Arena arena = Arena.ofConfined()) {
            MemorySegment segment = arena.allocate(JAVA_LONG);
            Pointer ptr = new Pointer(segment);
            long value = 0xFFFFFFF;
            ptr.setLong(0, value);
            assertEquals(value, ptr.getLong(0));
        }
    }

    @Test
    void testGetWideString() {
        try (Arena arena = Arena.ofConfined()) {
            String expected = "expected string";
            byte[] bytes = (expected + "\0").getBytes(StandardCharsets.UTF_16LE);
            MemorySegment segment = arena.allocate(bytes.length);
            for (int i = 0; i < bytes.length; i++) {
                segment.set(JAVA_BYTE, i, bytes[i]);
            }
            Pointer ptr = new Pointer(segment);
            assertEquals(expected, ptr.getWideString(0));
        }
    }

    @Test
    void testToString() {
        try (Arena arena = Arena.ofConfined()) {
            MemorySegment segment = arena.allocate(1);
            Pointer ptr = new Pointer(segment);
            String toString = ptr.toString();
            assertTrue(toString.startsWith("Pointer@0x"));
            assertNotEquals("Pointer@0x0", toString);
        }
    }

    @Test
    void testSegmentAccess() {
        try (Arena arena = Arena.ofConfined()) {
            MemorySegment segment = arena.allocate(4);
            Pointer ptr = new Pointer(segment);
            assertEquals(segment, ptr.segment());
        }
    }
}