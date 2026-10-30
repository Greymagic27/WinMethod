package io.github.greymagic27.win_method.WinDef;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UShortTest {

    @Test
    void testConstructorStoresValue() {
        assertEquals(42, new USHORT((short) 42).shortValue());
        assertEquals(0, new USHORT((short) 0).shortValue());
        assertEquals(-123, new USHORT((short) -123).shortValue());
    }

    @Test
    void testMaximumUnsignedValue() {
        assertEquals(4294967295L, new USHORT((short) 4294967295L).shortValue());
    }

    @Test
    void testToStringContainsValue() {
        assertTrue(new USHORT((short) 777).toString().contains("777"));
    }
}