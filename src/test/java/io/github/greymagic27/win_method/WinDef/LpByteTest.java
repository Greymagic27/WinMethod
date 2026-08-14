package io.github.greymagic27.win_method.WinDef;

import io.github.greymagic27.win_method.Pointer;
import java.lang.foreign.MemorySegment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LpByteTest {

    @Test
    void testConstructorStoresValue() {
        MemorySegment segment = MemorySegment.ofAddress(42);
        assertEquals(42, new LPBYTE(segment).segment.address());
        MemorySegment zero = MemorySegment.ofAddress(0);
        assertEquals(0, new LPBYTE(zero).segment.address());
        MemorySegment negative = MemorySegment.ofAddress(-123);
        assertEquals(-123, new LPBYTE(negative).segment.address());
    }

    @Test
    void testLPBYTEMemorySegmentConstructor() {
        MemorySegment segment = MemorySegment.ofAddress(0x1234);
        LPBYTE lpbyte = new LPBYTE(segment);
        assertEquals(segment, lpbyte.segment);
    }

    @Test
    void testPointerValue() {
        Pointer p = new Pointer(MemorySegment.ofAddress(7));
        LPBYTE lpbyte = new LPBYTE(p.segment);
        assertEquals(p, new Pointer(lpbyte.segment));
    }

    @Test
    void testToStringContainsValue() {
        assertTrue(new LPBYTE(MemorySegment.ofAddress(0x777)).toString().contains("777"));
    }
}