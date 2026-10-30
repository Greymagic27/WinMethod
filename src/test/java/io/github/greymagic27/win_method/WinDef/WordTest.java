package io.github.greymagic27.win_method.WinDef;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WordTest {

    @Test
    void testConstructorStoresValue() {
        assertEquals(42, new WORD((short) 42).shortValue());
        assertEquals(0, new WORD((short) 0).shortValue());
        assertEquals(-123, new WORD((short) -123).shortValue());
    }

    @Test
    void testMaximumUnsignedValue() {
        assertEquals(-1, new WORD((short) 0xFFFF).shortValue());
    }

    @Test
    void testToStringContainsValue() {
        assertTrue(new WORD((short) 777).toString().contains("777"));
    }
}
