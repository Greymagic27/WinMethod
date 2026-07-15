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
    void testLongValue() {
        assertEquals(7L, new LPARAM(7).longValue());
    }

    @Test
    void testToStringContainsValue() {
        assertTrue(new LPARAM(777).toString().contains("777"));
    }
}
