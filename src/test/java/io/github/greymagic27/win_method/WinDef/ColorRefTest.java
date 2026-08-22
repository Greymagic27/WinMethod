package io.github.greymagic27.win_method.WinDef;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ColorRefTest {

    @Test
    void testConstructorStoresValue() {
        assertEquals(42, new COLORREF(42).intValue());
        assertEquals(0, new COLORREF(0).intValue());
        assertEquals(-123, new COLORREF(-123).intValue());
    }

    @Test
    void testColorrefValue() {
        assertEquals(0x112233, new COLORREF(0x112233).intValue());
    }

    @Test
    void testToStringContainsValue() {
        assertTrue(new COLORREF(0xFF00FF).toString().contains("16711935"));
    }
}