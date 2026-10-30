package io.github.greymagic27.win_method.WinNT;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ULongLongTest {
    @Test
    void testConstructorStoresValue() {
        assertEquals(42, new ULONGLONG(42).longValue());
        assertEquals(0, new ULONGLONG(0).longValue());
        assertEquals(-123, new ULONGLONG(-123).longValue());
    }

    @Test
    void testMaximumUnsignedValue() {
        assertEquals(4294967295L, new ULONGLONG(4294967295L).longValue());
    }

    @Test
    void testToStringContainsValue() {
        assertTrue(new ULONGLONG(777L).toString().contains("777"));
    }
}