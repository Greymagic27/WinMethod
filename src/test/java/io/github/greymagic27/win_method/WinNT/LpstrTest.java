package io.github.greymagic27.win_method.WinNT;

import java.lang.foreign.MemorySegment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LpstrTest {

    @Test
    void testMemorySegmentConstructorStoresValue() {
        MemorySegment segment = MemorySegment.ofAddress(0x1234);
        LPSTR lpstr = new LPSTR(segment);
        assertEquals(segment, lpstr.segment);
    }

    @Test
    void testStringConstructorAllocatesReadableValue() {
        LPSTR lpstr = new LPSTR("Hello");
        assertEquals("Hello", lpstr.getWideString(0));
    }

    @Test
    void testStringConstructorRoundTripsEmptyString() {
        LPSTR lpstr = new LPSTR("");
        assertEquals("", lpstr.getWideString(0));
    }

    @Test
    void testStringConstructorRoundTripsUnicodeCharacters() {
        LPSTR lpstr = new LPSTR("héllo wörld 中文");
        assertEquals("héllo wörld 中文", lpstr.getWideString(0));
    }

    @Test
    void testStringConstructorWithNullValueProducesNullSegment() {
        LPSTR lpstr = new LPSTR((String) null);
        assertEquals(MemorySegment.NULL, lpstr.segment);
        assertTrue(lpstr.isNull());
    }

    @Test
    void testStringConstructorWithNonNullValueIsNotNull() {
        LPSTR lpstr = new LPSTR("not null");
        assertFalse(lpstr.isNull());
    }

    @Test
    void testToStringContainsAddress() {
        LPSTR lpstr = new LPSTR(MemorySegment.ofAddress(0x777));
        assertTrue(lpstr.toString().contains("777"));
    }

    @Test
    void testEqualsComparesByAddress() {
        MemorySegment segment = MemorySegment.ofAddress(0x999);
        LPSTR a = new LPSTR(segment);
        LPSTR b = new LPSTR(segment);
        assertEquals(a, b);
    }
}