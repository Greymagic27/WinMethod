package io.github.greymagic27.jna_clone.WinDef;

import io.github.greymagic27.jna_clone.Pointer;
import java.lang.foreign.MemorySegment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LPVoidTest {

    @Test
    void testConstructorStoresValue() {
        Pointer p = new Pointer(MemorySegment.ofAddress(42));
        assertEquals(42, new LPVOID(p).segment.address());
        Pointer zero = new Pointer(MemorySegment.ofAddress(0));
        assertEquals(0, new LPVOID(zero).segment.address());
        Pointer negative = new Pointer(MemorySegment.ofAddress(-123));
        assertEquals(-123, new LPVOID(negative).segment.address());
    }

    @Test
    void testLPVOIDMemorySegmentConstructor() {
        MemorySegment segment = MemorySegment.ofAddress(0x1234);
        LPVOID lpvoid = new LPVOID(segment);
        assertEquals(segment, lpvoid.segment);
    }

    @Test
    void testPointerValue() {
        Pointer p = new Pointer(MemorySegment.ofAddress(7));
        assertEquals(p, new LPVOID(p));
    }

    @Test
    void testToStringContainsValue() {
        assertTrue(new LPVOID(new Pointer(MemorySegment.ofAddress(0x777))).toString().contains("777"));
    }
}
