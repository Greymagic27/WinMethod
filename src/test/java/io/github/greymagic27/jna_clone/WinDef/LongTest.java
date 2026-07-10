package io.github.greymagic27.jna_clone.WinDef;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LongTest {

    @Test
    void testConstructorStoresValue() {
        assertEquals(42, new LONG(42).intValue());
        assertEquals(0, new LONG(0).intValue());
        assertEquals(-123, new LONG(-123).intValue());
    }

    @Test
    void testLongValue() {
        assertEquals(7, new LONG(7).intValue());
    }

    @Test
    void testToStringContainsValue() {
        assertTrue(new LONG(777).toString().contains("777"));
    }
}
