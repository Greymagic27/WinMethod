package io.github.greymagic27.win_method.WinDef;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ULongTest {

    @Test
    void testConstructorStoresValue() {
        assertEquals(42, new ULONG(42).longValue());
        assertEquals(0, new ULONG(0).longValue());
        assertEquals(-123, new ULONG(-123).longValue());
    }

    @Test
    void testMaximumUnsignedValue() {
        assertEquals(4294967295L, new ULONG(4294967295L).longValue());
    }

    @Test
    void testToStringContainsValue() {
        assertTrue(new ULONG(777L).toString().contains("777"));
    }
}