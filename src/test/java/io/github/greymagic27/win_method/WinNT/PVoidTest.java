package io.github.greymagic27.win_method.WinNT;

import io.github.greymagic27.win_method.Pointer;
import java.lang.foreign.MemorySegment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PVoidTest {
    @Test
    void testConstructorStoresValue() {
        Pointer p = new Pointer(MemorySegment.ofAddress(42));
        assertEquals(42, new PVOID(p).segment.address());
        Pointer zero = new Pointer(MemorySegment.ofAddress(0));
        assertEquals(0, new PVOID(zero).segment.address());
        Pointer negative = new Pointer(MemorySegment.ofAddress(-123));
        assertEquals(-123, new PVOID(negative).segment.address());
    }

    @Test
    void testPVOIDMemorySegmentConstructor() {
        MemorySegment segment = MemorySegment.ofAddress(0x1234);
        PVOID pvoid = new PVOID(segment);
        assertEquals(segment, pvoid.segment);
    }

    @Test
    void testPointerValue() {
        Pointer p = new Pointer(MemorySegment.ofAddress(7));
        assertEquals(p, new PVOID(p));
    }

    @Test
    void testToStringContainsValue() {
        assertTrue(new PVOID(MemorySegment.ofAddress(0x777)).toString().contains("777"));
    }
}