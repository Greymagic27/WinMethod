package io.github.greymagic27.win_method.WinNT;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LongTest {

    @Test
    void testConstructorStoresValue() {
        assertEquals(42, new LONG(42).intValue());
        assertEquals(0, new LONG(0).intValue());
        assertEquals(-123, new LONG(-123).intValue());
    }

    @Test
    void testMaximumValue() {
        assertEquals(Integer.MAX_VALUE, new LONG(Integer.MAX_VALUE).intValue());
    }

    @Test
    void testMinimumValue() {
        assertEquals(Integer.MIN_VALUE, new LONG(Integer.MIN_VALUE).intValue());
    }

    @Test
    void testToStringContainsValue() {
        assertTrue(new LONG(777).toString().contains("777"));
    }
}
