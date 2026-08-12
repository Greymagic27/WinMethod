package io.github.greymagic27.win_method.WinDef;

import io.github.greymagic27.win_method.Pointer;
import java.lang.foreign.MemorySegment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HFontTest {

    @Test
    void testConstructorStoresValue() {
        Pointer p = new Pointer(MemorySegment.ofAddress(42));
        assertEquals(42, new HFONT(p).segment.address());
        Pointer zero = new Pointer(MemorySegment.ofAddress(0));
        assertEquals(0, new HFONT(zero).segment.address());
        Pointer negative = new Pointer(MemorySegment.ofAddress(-123));
        assertEquals(-123, new HFONT(negative).segment.address());
    }

    @Test
    void testHFONTMemorySegmentConstructor() {
        MemorySegment segment = MemorySegment.ofAddress(0x1234);
        HFONT hfont = new HFONT(segment);
        assertEquals(segment, hfont.segment);
    }

    @Test
    void testPointerValue() {
        Pointer p = new Pointer(MemorySegment.ofAddress(7));
        assertEquals(p, new HFONT(p));
    }

    @Test
    void testToStringContainsValue() {
        assertTrue(new HFONT(new Pointer(MemorySegment.ofAddress(0x777))).toString().contains("777"));
    }
}