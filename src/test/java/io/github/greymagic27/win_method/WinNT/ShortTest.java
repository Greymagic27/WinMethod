package io.github.greymagic27.win_method.WinNT;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ShortTest {

    @Test
    void testConstructorStoresValue() {
        assertEquals(42, new SHORT((short) 42).shortValue());
        assertEquals(0, new SHORT((short) 0).shortValue());
        assertEquals(-123, new SHORT((short) -123).shortValue());
    }

    @Test
    void testShortValue() {
        assertEquals(7, new SHORT((short) 7).shortValue());
    }

    @Test
    void testToStringContainsValue() {
        assertTrue(new SHORT((short) 777).toString().contains("777"));
    }
}