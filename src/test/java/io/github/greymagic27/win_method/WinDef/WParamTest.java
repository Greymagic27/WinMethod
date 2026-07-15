package io.github.greymagic27.win_method.WinDef;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WParamTest {

    @Test
    void testConstructorStoresValue() {
        assertEquals(42L, new WPARAM(42L).longValue());
        assertEquals(0L, new WPARAM(0L).longValue());
        assertEquals(-123L, new WPARAM(-123L).longValue());
    }

    @Test
    void testLongValue() {
        assertEquals(7L, new WPARAM(7L).longValue());
    }

    @Test
    void testToStringContainsValue() {
        assertTrue(new WPARAM(777L).toString().contains("777"));
    }
}
