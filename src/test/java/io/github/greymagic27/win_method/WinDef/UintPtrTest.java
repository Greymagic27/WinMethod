package io.github.greymagic27.win_method.WinDef;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UintPtrTest {

    @Test
    void testConstructorStoresValue() {
        assertEquals(42L, new UINT_PTR(42).longValue());
        assertEquals(0L, new UINT_PTR(0).longValue());
        assertEquals(-123L, new UINT_PTR(-123).longValue());
    }

    @Test
    void testLongValue() {
        assertEquals(7L, new UINT_PTR(7).longValue());
    }

    @Test
    void testToStringContainsValue() {
        assertTrue(new UINT_PTR(777).toString().contains("777"));
    }
}