package io.github.greymagic27.win_method.WinNT;

import io.github.greymagic27.win_method.Pointer;
import java.lang.foreign.MemorySegment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PcwstrTest {

    @Test
    void testConstructorStoresValue() {
        MemorySegment segment = MemorySegment.ofAddress(42);
        assertEquals(segment, new PCWSTR(segment).segment);
    }

    @Test
    void testNullSegment() {
        PCWSTR pcwstr = new PCWSTR(MemorySegment.NULL);
        assertEquals(MemorySegment.NULL, pcwstr.segment);
    }

    @Test
    void testPointerValue() {
        Pointer pointer = new Pointer(MemorySegment.ofAddress(0x1234));
        PCWSTR pcwstr = new PCWSTR(pointer.segment);

        assertEquals(pointer.segment, pcwstr.segment);
    }

    @Test
    void testToStringContainsValue() {
        assertTrue(new PCWSTR(MemorySegment.ofAddress(0x777)).toString().contains("777"));
    }

    @Test
    void testInheritance() {
        assertInstanceOf(Pointer.class, new PCWSTR(MemorySegment.NULL));
    }
}