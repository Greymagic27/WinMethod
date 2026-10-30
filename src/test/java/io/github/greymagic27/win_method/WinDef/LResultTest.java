package io.github.greymagic27.win_method.WinDef;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LResultTest {

    @Test
    void testConstructorStoresValue() {
        assertEquals(42, new LRESULT(42).longValue());
        assertEquals(0, new LRESULT(0).longValue());
        assertEquals(-123, new LRESULT(-123).longValue());
    }

    @Test
    void testMaximumValue() {
        assertEquals(Long.MAX_VALUE, new LRESULT(Long.MAX_VALUE).longValue());
    }

    @Test
    void testMinimumValue() {
        assertEquals(Long.MIN_VALUE, new LRESULT(Long.MIN_VALUE).longValue());
    }

    @Test
    void testToStringContainsValue() {
        assertTrue(new LRESULT(777).toString().contains("777"));
    }
}