package io.github.greymagic27.win_method.IntSafe;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DwordTest {
    @Test
    void testConstructorStoresValue() {
        assertEquals(42, new DWORD(42).intValue());
        assertEquals(0, new DWORD(0).intValue());
        assertEquals(-123, new DWORD(-123).intValue());
    }

    @Test
    void testDwordValue() {
        assertEquals(7, new DWORD(7).intValue());
    }

    @Test
    void testToStringContainsValue() {
        assertTrue(new DWORD(777).toString().contains("777"));
    }
}