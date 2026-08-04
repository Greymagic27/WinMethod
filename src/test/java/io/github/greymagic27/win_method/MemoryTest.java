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
    void testCloseIsIdempotent() {
        memory.close();
        assertDoesNotThrow(memory::close);
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
}