package io.github.greymagic27.win_method;

import java.lang.foreign.Arena;
import java.lang.foreign.ValueLayout;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MemoryTest {

    private Memory memory;

    @BeforeEach
    void setUp() {
        memory = new Memory(4);
    }

    @AfterEach
    void tearDown() {
        memory.close();
    }

    @Test
    void testAllocatesRequestedSize() {
        try (Memory memory = new Memory(16)) {
            assertEquals(16, memory.size());
        }
    }

    @Test
    void testIsZeroInitialised() {
        try (Memory memory = new Memory(8)) {
            for (long l = 0; l < 8; l++) assertEquals(0, memory.segment.get(ValueLayout.JAVA_BYTE, l));
        }
    }

    @Test
    void testClearResetsModifiedBytes() {
        memory.segment.set(ValueLayout.JAVA_INT, 0, 12345);
        assertNotEquals(0, memory.segment.get(ValueLayout.JAVA_INT, 0));
        memory.clear();
        assertEquals(0, memory.segment.get(ValueLayout.JAVA_INT, 0));
    }

    @Test
    void testIsPointer() {
        assertInstanceOf(Pointer.class, memory);
        assertFalse(memory.isNull());

    }

    @Test
    void testUsesProvidedArena() {
        Arena arena = Arena.ofConfined();
        try (Memory mem = new Memory(4, arena)) {
            mem.segment.set(ValueLayout.JAVA_INT, 0, 7);
            assertEquals(7, mem.segment.get(ValueLayout.JAVA_INT, 0));
        }
    }

    @Test
    void testClosesInvalidSegment() {
        memory.close();
        assertThrows(IllegalStateException.class, () -> memory.segment.get(ValueLayout.JAVA_INT, 0));
    }

    @Test
    void testCloseOnUnclosableArenaThrows() {
        Memory mem = new Memory(4, Arena.global());
        assertThrows(UnsupportedOperationException.class, mem::close);
    }

    @Test
    void testTryWithResourcesClosesArena() {
        Memory mem;
        try (Memory m = new Memory(4)) {
            mem = m;
            m.segment.get(ValueLayout.JAVA_INT, 0);
        }
        Memory finalMem = mem;
        assertThrows(IllegalStateException.class, () -> finalMem.segment.get(ValueLayout.JAVA_INT, 0));
    }

    @Test
    void testGetByteArray() {
        memory.segment.set(ValueLayout.JAVA_BYTE, 0, (byte) 1);
        memory.segment.set(ValueLayout.JAVA_BYTE, 1, (byte) 2);
        memory.segment.set(ValueLayout.JAVA_BYTE, 2, (byte) 3);
        memory.segment.set(ValueLayout.JAVA_BYTE, 3, (byte) 4);
        byte[] bytes = memory.getByteArray();
        assertEquals(4, bytes.length);
        assertEquals(1, bytes[0]);
        assertEquals(2, bytes[1]);
        assertEquals(3, bytes[2]);
        assertEquals(4, bytes[3]);
    }

    @Test
    void testGetByteArrayWithOffsetAndLength() {
        memory.segment.set(ValueLayout.JAVA_BYTE, 0, (byte) 1);
        memory.segment.set(ValueLayout.JAVA_BYTE, 1, (byte) 2);
        memory.segment.set(ValueLayout.JAVA_BYTE, 2, (byte) 3);
        memory.segment.set(ValueLayout.JAVA_BYTE, 3, (byte) 4);
        byte[] bytes = memory.getByteArray(1, 2);
        assertEquals(2, bytes.length);
        assertEquals(2, bytes[0]);
        assertEquals(3, bytes[1]);
    }

    @Test
    void testGetByteArrayWithZeroLength() {
        byte[] bytes = memory.getByteArray(0, 0);
        assertEquals(0, bytes.length);
    }

    @Test
    void testGetByteArrayAtEndOfMemory() {
        byte[] bytes = memory.getByteArray(4, 0);
        assertEquals(0, bytes.length);
    }

    @Test
    void testGetByteArrayRejectsNegativeOffset() {
        assertThrows(IndexOutOfBoundsException.class, () -> memory.getByteArray(-1, 1));
    }

    @Test
    void testGetByteArrayRejectsNegativeLength() {
        assertThrows(IndexOutOfBoundsException.class, () -> memory.getByteArray(0, -1));
    }

    @Test
    void testGetByteArrayRejectsOffsetBeyondMemory() {
        assertThrows(IndexOutOfBoundsException.class, () -> memory.getByteArray(5, 0));
    }

    @Test
    void testGetByteArrayRejectsLengthBeyondMemory() {
        assertThrows(IndexOutOfBoundsException.class, () -> memory.getByteArray(0, 5));
    }

    @Test
    void testGetByteArrayRejectsOffsetAndLengthBeyondMemory() {
        assertThrows(IndexOutOfBoundsException.class, () -> memory.getByteArray(3, 2));
    }

    @Test
    void testGetByteArrayDoesNotModifyMemory() {
        memory.segment.set(ValueLayout.JAVA_BYTE, 0, (byte) 42);
        byte[] bytes = memory.getByteArray();
        assertEquals(42, bytes[0]);
        assertEquals(42, memory.segment.get(ValueLayout.JAVA_BYTE, 0));
    }

    @Test
    void testWrite() {
        byte[] bytes = {1, 2, 3, 4};
        memory.write(0, bytes, 0, bytes.length);
        assertEquals(1, memory.segment.get(ValueLayout.JAVA_BYTE, 0));
        assertEquals(2, memory.segment.get(ValueLayout.JAVA_BYTE, 1));
        assertEquals(3, memory.segment.get(ValueLayout.JAVA_BYTE, 2));
        assertEquals(4, memory.segment.get(ValueLayout.JAVA_BYTE, 3));
    }

    @Test
    void testWriteWithOffsetAndIndex() {
        byte[] bytes = {1, 2, 3, 4};
        memory.write(1, bytes, 1, 2);
        assertEquals(0, memory.segment.get(ValueLayout.JAVA_BYTE, 0));
        assertEquals(2, memory.segment.get(ValueLayout.JAVA_BYTE, 1));
        assertEquals(3, memory.segment.get(ValueLayout.JAVA_BYTE, 2));
        assertEquals(0, memory.segment.get(ValueLayout.JAVA_BYTE, 3));
    }

    @Test
    void testWriteZeroLength() {
        assertDoesNotThrow(() -> memory.write(4, new byte[0], 0, 0));
    }

    @Test
    void testWriteDoesNotModifySourceArray() {
        byte[] bytes = {1, 2, 3, 4};
        memory.write(0, bytes, 0, bytes.length);
        bytes[0] = 99;
        assertEquals(1, memory.segment.get(ValueLayout.JAVA_BYTE, 0));
    }

    @Test
    void testWriteRejectsNegativeOffset() {
        assertThrows(IndexOutOfBoundsException.class, () -> memory.write(-1, new byte[1], 0, 1));
    }

    @Test
    void testWriteRejectsNegativeIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> memory.write(0, new byte[1], -1, 1));
    }

    @Test
    void testWriteRejectsNegativeLength() {
        assertThrows(IndexOutOfBoundsException.class, () -> memory.write(0, new byte[1], 0, -1));
    }

    @Test
    void testWriteRejectsIndexBeyondArray() {
        assertThrows(IndexOutOfBoundsException.class, () -> memory.write(0, new byte[4], 5, 0));
    }

    @Test
    void testWriteRejectsIndexAndLengthBeyondArray() {
        assertThrows(IndexOutOfBoundsException.class, () -> memory.write(0, new byte[4], 3, 2));
    }

    @Test
    void testWriteRejectsOffsetBeyondMemory() {
        assertThrows(IndexOutOfBoundsException.class, () -> memory.write(5, new byte[1], 0, 0));
    }

    @Test
    void testWriteRejectsOffsetAndLengthBeyondMemory() {
        assertThrows(IndexOutOfBoundsException.class, () -> memory.write(3, new byte[2], 0, 2));
    }
}
