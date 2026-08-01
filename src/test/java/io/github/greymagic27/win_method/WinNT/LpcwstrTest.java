package io.github.greymagic27.win_method.WinNT;

import java.lang.foreign.MemorySegment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LpcwstrTest {

    @Test
    void testMemorySegmentConstructorStoresValue() {
        MemorySegment segment = MemorySegment.ofAddress(0x1234);
        LPCWSTR lpcwstr = new LPCWSTR(segment);
        assertEquals(segment, lpcwstr.segment);
    }

    @Test
    void testStringConstructorAllocatesReadableValue() {
        LPCWSTR lpcwstr = new LPCWSTR("Hello");
        assertEquals("Hello", lpcwstr.getWideString(0));
    }

    @Test
    void testStringConstructorRoundTripsEmptyString() {
        LPCWSTR lpcwstr = new LPCWSTR("");
        assertEquals("", lpcwstr.getWideString(0));
    }

    @Test
    void testStringConstructorRoundTripsUnicodeCharacters() {
        LPCWSTR lpcwstr = new LPCWSTR("héllo wörld 中文");
        assertEquals("héllo wörld 中文", lpcwstr.getWideString(0));
    }

    @Test
    void testStringConstructorWithNullValueProducesNullSegment() {
        LPCWSTR lpcwstr = new LPCWSTR((String) null);
        assertEquals(MemorySegment.NULL, lpcwstr.segment);
        assertTrue(lpcwstr.isNull());
    }

    @Test
    void testStringConstructorWithNonNullValueIsNotNull() {
        LPCWSTR lpcwstr = new LPCWSTR("not null");
        assertFalse(lpcwstr.isNull());
    }

    @Test
    void testToStringContainsAddress() {
        LPCWSTR lpcwstr = new LPCWSTR(MemorySegment.ofAddress(0x777));
        assertTrue(lpcwstr.toString().contains("777"));
    }

    @Test
    void testEqualsComparesByAddress() {
        MemorySegment segment = MemorySegment.ofAddress(0x999);
        LPCWSTR a = new LPCWSTR(segment);
        LPCWSTR b = new LPCWSTR(segment);
        assertEquals(a, b);
    }
}
