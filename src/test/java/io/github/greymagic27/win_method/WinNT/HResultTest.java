package io.github.greymagic27.win_method.WinNT;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HResultTest {

    @Test
    void testConstructorStoresValue() {
        assertEquals(42, new HRESULT(42).intValue());
        assertEquals(0, new HRESULT(0).intValue());
        assertEquals(-123, new HRESULT(-123).intValue());
    }

    @Test
    void testMaximumValue() {
        assertEquals(Integer.MAX_VALUE, new HRESULT(Integer.MAX_VALUE).intValue());
    }

    @Test
    void testMinimumValue() {
        assertEquals(Integer.MIN_VALUE, new HRESULT(Integer.MIN_VALUE).intValue());
    }

    @Test
    void testToStringContainsValue() {
        assertTrue(new HRESULT(777).toString().contains("777"));
    }

    @Test
    void testExtendsLONG() {
        assertInstanceOf(LONG.class, new HRESULT(42));
    }
}