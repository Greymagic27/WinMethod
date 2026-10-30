package io.github.greymagic27.win_method.WinDef;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LParamTest {

    @Test
    void testConstructorStoresValue() {
        assertEquals(42, new LPARAM(42).longValue());
        assertEquals(0, new LPARAM(0).longValue());
        assertEquals(-123, new LPARAM(-123).longValue());
    }

    @Test
    void testMaximumValue() {
        assertEquals(Long.MAX_VALUE, new LPARAM(Long.MAX_VALUE).longValue());
    }

    @Test
    void testMinimumValue() {
        assertEquals(Long.MIN_VALUE, new LPARAM(Long.MIN_VALUE).longValue());
    }

    @Test
    void testToStringContainsValue() {
        assertTrue(new LPARAM(777).toString().contains("777"));
    }
}
