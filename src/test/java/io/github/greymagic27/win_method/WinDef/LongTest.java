package io.github.greymagic27.win_method.WinDef;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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

    @Test
    void testEqualsFunction() {
        LONG first = new LONG(42);
        LONG second = new LONG(42);
        LONG third = new LONG(43);
        Object other = 42;
        assertEquals(first, second);
        assertNotEquals(first, third);
        assertNotEquals(other, first);
        assertNotEquals(null, first);
    }

    @Test
    void testHashCodeFunction() {
        LONG first = new LONG(42);
        LONG second = new LONG(42);
        assertEquals(first.hashCode(), second.hashCode());
    }
}
