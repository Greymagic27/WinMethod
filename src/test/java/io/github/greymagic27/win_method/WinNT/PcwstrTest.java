package io.github.greymagic27.win_method.WinNT;

import io.github.greymagic27.win_method.Pointer;
import java.lang.foreign.MemorySegment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PcwstrTest {

    @Test
    void testMemorySegmentConstructorStoresValue() {
        MemorySegment segment = MemorySegment.ofAddress(0x1234);
        PCWSTR pcwstr = new PCWSTR(segment);
        assertEquals(segment, pcwstr.segment);
    }

    @Test
    void testStringConstructorAllocatesReadableValue() {
        PCWSTR pcwstr = new PCWSTR("Hello");
        assertEquals("Hello", pcwstr.getWideString(0));
    }

    @Test
    void testStringConstructorRoundTripsEmptyString() {
        PCWSTR pcwstr = new PCWSTR("");
        assertEquals("", pcwstr.getWideString(0));
    }

    @Test
    void testStringConstructorRoundTripsUnicodeCharacters() {
        PCWSTR pcwstr = new PCWSTR("héllo wörld 中文");
        assertEquals("héllo wörld 中文", pcwstr.getWideString(0));
    }

    @Test
    void testStringConstructorWithNullValueProducesNullSegment() {
        PCWSTR pcwstr = new PCWSTR((String) null);
        assertEquals(MemorySegment.NULL, pcwstr.segment);
        assertTrue(pcwstr.isNull());
    }

    @Test
    void testStringConstructorWithNonNullValueIsNotNull() {
        PCWSTR pcwstr = new PCWSTR("not null");
        assertFalse(pcwstr.isNull());
    }

    @Test
    void testToStringContainsAddress() {
        PCWSTR pcwstr = new PCWSTR(MemorySegment.ofAddress(0x777));
        assertTrue(pcwstr.toString().contains("777"));
    }

    @Test
    void testEqualsComparesByAddress() {
        MemorySegment segment = MemorySegment.ofAddress(0x999);
        PCWSTR a = new PCWSTR(segment);
        PCWSTR b = new PCWSTR(segment);
        assertEquals(a, b);
    }

    @Test
    void testPointerValue() {
        Pointer pointer = new Pointer(MemorySegment.ofAddress(0x1234));
        PCWSTR pcwstr = new PCWSTR(pointer.segment);
        assertEquals(pointer.segment, pcwstr.segment);
    }

    @Test
    void testInheritance() {
        assertInstanceOf(Pointer.class, new PCWSTR(MemorySegment.NULL));
    }
}