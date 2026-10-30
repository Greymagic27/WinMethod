package io.github.greymagic27.win_method.WinNT;

import io.github.greymagic27.win_method.Pointer;
import java.lang.foreign.MemorySegment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PCStrTest {

    @Test
    void testConstructorStoresValue() {
        MemorySegment segment = MemorySegment.ofAddress(42);
        assertEquals(segment, new PCSTR(segment).segment);
    }

    @Test
    void testNullSegment() {
        PCSTR pcstr = new PCSTR(MemorySegment.NULL);
        assertEquals(MemorySegment.NULL, pcstr.segment);
    }

    @Test
    void testPointerValue() {
        Pointer pointer = new Pointer(MemorySegment.ofAddress(0x1234));
        PCSTR pcstr = new PCSTR(pointer.segment);

        assertEquals(pointer.segment, pcstr.segment);
    }

    @Test
    void testToStringContainsValue() {
        assertTrue(new PCSTR(MemorySegment.ofAddress(0x777)).toString().contains("777"));
    }

    @Test
    void testInheritance() {
        assertInstanceOf(Pointer.class, new PCSTR(MemorySegment.NULL));
    }
}