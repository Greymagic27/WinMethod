package io.github.greymagic27.jna_clone.WinDef;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LResultTest {

    @Test
    void testConstructorStoresValue() {
        assertEquals(42, new LRESULT(42).longValue());
        assertEquals(0, new LRESULT(0).longValue());
        assertEquals(-123, new LRESULT(-123).longValue());
    }

    @Test
    void testEquality() {
        LRESULT a = new LRESULT(10);
        LRESULT b = new LRESULT(10);
        assertEquals(a.longValue(), b.longValue());
    }

    @Test
    void testInequality() {
        LRESULT a = new LRESULT(10L);
        LRESULT b = new LRESULT(11L);
        assertNotEquals(a, b);
    }

    @Test
    void testToStringContainsValue() {
        assertTrue(new LRESULT(777).toString().contains("777"));
    }
}