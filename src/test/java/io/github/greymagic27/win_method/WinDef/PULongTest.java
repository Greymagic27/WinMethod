package io.github.greymagic27.win_method.WinDef;

import io.github.greymagic27.win_method.Pointer;
import java.lang.foreign.MemorySegment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PULongTest {

    @Test
    void testConstructorStoresValue() {
        MemorySegment segment = MemorySegment.ofAddress(42);
        assertEquals(segment, new PULONG(segment).segment);
    }

    @Test
    void testNullSegment() {
        PULONG pulong = new PULONG(MemorySegment.NULL);
        assertEquals(MemorySegment.NULL, pulong.segment);
    }

    @Test
    void testPointerValue() {
        Pointer pointer = new Pointer(MemorySegment.ofAddress(0x1234));
        PULONG pulong = new PULONG(pointer.segment);
        assertEquals(pointer.segment, pulong.segment);
    }

    @Test
    void testToStringContainsValue() {
        assertTrue(new PULONG(MemorySegment.ofAddress(0x777)).toString().contains("777"));
    }

    @Test
    void testInheritance() {
        assertInstanceOf(Pointer.class, new PULONG(MemorySegment.NULL));
    }
}