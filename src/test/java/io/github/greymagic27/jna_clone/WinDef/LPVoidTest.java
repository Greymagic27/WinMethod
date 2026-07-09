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
        assertEquals(p, new LPVOID(p).pointerValue());
        Pointer zero = new Pointer(MemorySegment.ofAddress(0));
        assertEquals(new Pointer(MemorySegment.ofAddress(0)), new LPVOID(zero).pointerValue());
        Pointer negative = new Pointer(MemorySegment.ofAddress(-123));
        assertEquals(negative, new LPVOID(negative).pointerValue());
    }

    @Test
    void testPointerValue() {
        Pointer p = new Pointer(MemorySegment.ofAddress(7));
        assertEquals(p, new LPVOID(p).pointerValue());
    }

    @Test
    void testToStringContainsValue() {
        assertTrue(new LPVOID(new Pointer(MemorySegment.ofAddress(0x777))).toString().contains("777"));
    }
}
