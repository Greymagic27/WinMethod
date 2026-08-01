package io.github.greymagic27.win_method.WinNT;

import java.lang.foreign.MemorySegment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LpwstrTest {

    @Test
    void testMemorySegmentConstructorStoresValue() {
        MemorySegment segment = MemorySegment.ofAddress(0x1234);
        LPWSTR lpwstr = new LPWSTR(segment);
        assertEquals(segment, lpwstr.segment);
    }

    @Test
    void testStringConstructorAllocatesReadableValue() {
        LPWSTR lpwstr = new LPWSTR("Hello");
        assertEquals("Hello", lpwstr.getWideString(0));
    }

    @Test
    void testStringConstructorRoundTripsEmptyString() {
        LPWSTR lpwstr = new LPWSTR("");
        assertEquals("", lpwstr.getWideString(0));
    }

    @Test
    void testStringConstructorRoundTripsUnicodeCharacters() {
        LPWSTR lpwstr = new LPWSTR("héllo wörld 中文");
        assertEquals("héllo wörld 中文", lpwstr.getWideString(0));
    }

    @Test
    void testStringConstructorWithNullValueProducesNullSegment() {
        LPWSTR lpwstr = new LPWSTR((String) null);
        assertEquals(MemorySegment.NULL, lpwstr.segment);
        assertTrue(lpwstr.isNull());
    }

    @Test
    void testStringConstructorWithNonNullValueIsNotNull() {
        LPWSTR lpwstr = new LPWSTR("not null");
        assertFalse(lpwstr.isNull());
    }

    @Test
    void testToStringContainsAddress() {
        LPWSTR lpwstr = new LPWSTR(MemorySegment.ofAddress(0x777));
        assertTrue(lpwstr.toString().contains("777"));
    }

    @Test
    void testEqualsComparesByAddress() {
        MemorySegment segment = MemorySegment.ofAddress(0x999);
        LPWSTR a = new LPWSTR(segment);
        LPWSTR b = new LPWSTR(segment);
        assertEquals(a, b);
    }
}