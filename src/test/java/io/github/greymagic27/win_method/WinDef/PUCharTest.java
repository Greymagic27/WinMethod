package io.github.greymagic27.win_method.WinDef;

import io.github.greymagic27.win_method.Pointer;
import java.lang.foreign.MemorySegment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PUCharTest {

    @Test
    void testConstructorStoresValue() {
        MemorySegment segment = MemorySegment.ofAddress(42);
        assertEquals(segment, new PUCHAR(segment).segment);
    }

    @Test
    void testNullSegment() {
        PUCHAR puchar = new PUCHAR(MemorySegment.NULL);
        assertEquals(MemorySegment.NULL, puchar.segment);
    }

    @Test
    void testPointerValue() {
        Pointer pointer = new Pointer(MemorySegment.ofAddress(0x1234));
        PUCHAR puchar = new PUCHAR(pointer.segment);
        assertEquals(pointer.segment, puchar.segment);
    }

    @Test
    void testToStringContainsValue() {
        assertTrue(new PUCHAR(MemorySegment.ofAddress(0x777)).toString().contains("777"));
    }

    @Test
    void testInheritance() {
        assertInstanceOf(Pointer.class, new PUCHAR(MemorySegment.NULL));
    }
}