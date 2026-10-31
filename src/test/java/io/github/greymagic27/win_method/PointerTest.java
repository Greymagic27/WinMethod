package io.github.greymagic27.win_method;

import io.github.greymagic27.win_method.types.WinUser;
import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PointerTest {

    private Arena arena;

    @SuppressWarnings("MustBeClosedChecker")
    @BeforeEach
    void setUp() {
        arena = Arena.ofConfined();
    }

    @AfterEach
    void tearDown() {
        arena.close();
    }

    @Test
    void testGetWideString() {
        String expected = "expected string";
        byte[] bytes = (expected + "\0").getBytes(StandardCharsets.UTF_16LE);
        MemorySegment segment = arena.allocate(bytes.length);
        for (int i = 0; i < bytes.length; i++) segment.set(ValueLayout.JAVA_BYTE, i, bytes[i]);
        Pointer ptr = new Pointer(segment);
        assertEquals(expected, ptr.getWideString(0));
    }

    @Test
    void testGetWideStringOutOfBounds() {
        MemorySegment segment = arena.allocate(2);
        segment.set(ValueLayout.JAVA_SHORT, 0, (short) 'A');
        Pointer p = new Pointer(segment);
        assertThrows(IndexOutOfBoundsException.class, () -> p.getWideString(0));
    }

    @Test
    void testToString() {
        MemorySegment segment = arena.allocate(1);
        Pointer ptr = new Pointer(segment);
        String toString = ptr.toString();
        assertTrue(toString.startsWith("Pointer@0x"));
        assertNotEquals("Pointer@0x0", toString);

    }

    @Test
    void testSegmentAccess() {
        MemorySegment segment = arena.allocate(4);
        Pointer ptr = new Pointer(segment);
        assertEquals(segment, ptr.segment);
    }

    @Test
    void testMakeIntResource() {
        int id = 99999;
        Pointer ptr = WinUser.MAKEINTRESOURCEW(id);
        assertEquals(id, ptr.segment.address());
        assertFalse(ptr.isNull());
        Pointer nullPtr = WinUser.MAKEINTRESOURCEW(0);
        assertTrue(nullPtr.isNull());
    }

    @Test
    void testGetByteArray() {
        MemorySegment segment = arena.allocate(4);
        segment.set(ValueLayout.JAVA_BYTE, 0, (byte) 1);
        segment.set(ValueLayout.JAVA_BYTE, 1, (byte) 2);
        segment.set(ValueLayout.JAVA_BYTE, 2, (byte) 3);
        segment.set(ValueLayout.JAVA_BYTE, 3, (byte) 4);
        Pointer pointer = new Pointer(segment);
        assertArrayEquals(new byte[]{1, 2, 3, 4}, pointer.getByteArray(0, 4));

    }

    @Test
    void testGetByteArrayWithOffset() {
        MemorySegment segment = arena.allocate(4);
        segment.set(ValueLayout.JAVA_INT_UNALIGNED, 0, 0x04030201);
        Pointer pointer = new Pointer(segment);
        assertArrayEquals(new byte[]{2, 3}, pointer.getByteArray(1, 2));

    }

    @Test
    void testGetByteArrayWithZeroLength() {
        Pointer pointer = new Pointer(arena.allocate(4));
        assertArrayEquals(new byte[0], pointer.getByteArray(0, 0));

    }

    @Test
    void testGetByteArrayRejectsOutOfBoundsAccess() {
        Pointer pointer = new Pointer(arena.allocate(4));
        assertThrows(IndexOutOfBoundsException.class, () -> pointer.getByteArray(5, 0));
        assertThrows(IndexOutOfBoundsException.class, () -> pointer.getByteArray(3, 2));

    }

    @Test
    void testGetLong() {
        MemorySegment segment = arena.allocate(8);
        segment.set(ValueLayout.JAVA_LONG_UNALIGNED, 0, 123456789L);
        assertEquals(123456789L, new Pointer(segment).getLong(0));

    }

    @Test
    void testGetInt() {
        MemorySegment segment = arena.allocate(4);
        segment.set(ValueLayout.JAVA_INT_UNALIGNED, 0, 12345);
        assertEquals(12345, new Pointer(segment).getInt(0));

    }

    @Test
    void testGetShort() {
        MemorySegment segment = arena.allocate(2);
        segment.set(ValueLayout.JAVA_SHORT_UNALIGNED, 0, (short) 1234);
        assertEquals((short) 1234, new Pointer(segment).getShort(0));

    }

    @Test
    void testSetLong() {
        MemorySegment segment = arena.allocate(8);
        Pointer pointer = new Pointer(segment);
        pointer.setLong(0, 123456789L);
        assertEquals(123456789L, segment.get(ValueLayout.JAVA_LONG_UNALIGNED, 0));

    }

    @Test
    void testSetInt() {
        MemorySegment segment = arena.allocate(4);
        Pointer pointer = new Pointer(segment);
        pointer.setInt(0, 12345);
        assertEquals(12345, segment.get(ValueLayout.JAVA_INT_UNALIGNED, 0));

    }

    @Test
    void testSetShort() {
        MemorySegment segment = arena.allocate(2);
        Pointer pointer = new Pointer(segment);
        pointer.setShort(0, (short) 1234);
        assertEquals((short) 1234, segment.get(ValueLayout.JAVA_SHORT_UNALIGNED, 0));

    }

    @Test
    void testGetPointer() {
        MemorySegment target = arena.allocate(8);
        MemorySegment source = arena.allocate(ValueLayout.ADDRESS);
        source.set(ValueLayout.ADDRESS, 0, target);
        Pointer pointer = new Pointer(source).getPointer(0, 8);
        assertEquals(target.address(), pointer.segment.address());
        assertEquals(8, pointer.segment.byteSize());
    }

    @Test
    void testGetPointerWithoutSize() {
        MemorySegment target = arena.allocate(8);
        MemorySegment source = arena.allocate(ValueLayout.ADDRESS);
        source.set(ValueLayout.ADDRESS, 0, target);
        Pointer pointer = new Pointer(source).getPointer(0);
        assertEquals(target.address(), pointer.segment.address());
        assertEquals(0, pointer.segment.byteSize());
    }

    @Test
    void testSetPointer() {
        MemorySegment target = arena.allocate(8);
        MemorySegment source = arena.allocate(ValueLayout.ADDRESS);
        Pointer targetPointer = new Pointer(target);
        Pointer sourcePointer = new Pointer(source);
        sourcePointer.setPointer(0, targetPointer);
        assertEquals(target.address(), source.get(ValueLayout.ADDRESS, 0).address());
        targetPointer.setPointer(0, sourcePointer);
        assertEquals(source.address(), target.get(ValueLayout.ADDRESS, 0).address());

    }

    @Test
    void testGetPointerRejectsOutOfBoundsAccess() {
        Pointer pointer = new Pointer(arena.allocate(4));
        assertThrows(IndexOutOfBoundsException.class, () -> pointer.getPointer(0));
    }


    @Test
    void testNullPointer() {
        Pointer pointer = new Pointer(MemorySegment.NULL);
        assertTrue(pointer.isNull());
        assertEquals("Pointer@0x0", pointer.toString());
    }

    @Test
    void testEqualsWithSameAddress() {
        MemorySegment segment = arena.allocate(8);
        Pointer first = new Pointer(segment);
        Pointer second = new Pointer(segment);
        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());

    }

    @Test
    void testNotEqualsWithDifferentAddresses() {
        Pointer first = new Pointer(arena.allocate(8));
        Pointer second = new Pointer(arena.allocate(8));
        assertNotEquals(first, second);
    }
}