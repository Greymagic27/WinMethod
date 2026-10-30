package io.github.greymagic27.win_method.WinDef;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UintTest {

    @Test
    void testConstructorStoresValue() {
        assertEquals(42, new UINT(42).intValue());
        assertEquals(0, new UINT(0).intValue());
        assertEquals(-123, new UINT(-123).intValue());
    }

    @Test
    void testMaximumUnsignedValue() {
        assertEquals(-1, new UINT(0xFFFFFFFF).intValue());
    }

    @Test
    void testToStringContainsValue() {
        assertTrue(new UINT(777).toString().contains("777"));
    }
}