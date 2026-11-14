package io.github.greymagic27.win_method.BaseTsd;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ULongPtrTest {

    @Test
    void testConstructorStoresValue() {
        assertEquals(42L, new ULONG_PTR(42).longValue());
        assertEquals(0L, new ULONG_PTR(0).longValue());
        assertEquals(-123L, new ULONG_PTR(-123).longValue());
    }

    @Test
    void testMaximumUnsignedValue() {
        assertEquals(-1L, new ULONG_PTR(-1L).longValue());
    }

    @Test
    void testToStringContainsValue() {
        assertTrue(new ULONG_PTR(777).toString().contains("777"));
    }
}