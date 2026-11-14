package io.github.greymagic27.win_method.WinDef;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WordTest {

    @Test
    void testShortConstructor() {
        assertEquals(42, new WORD((short) 42).shortValue());
        assertEquals(0, new WORD((short) 0).shortValue());
        assertEquals(-123, new WORD((short) -123).shortValue());
    }

    @Test
    void testLongConstructor() {
        assertEquals(42, new WORD(42L).shortValue());
        assertEquals(0, new WORD(0L).shortValue());
        assertEquals(-1, new WORD(65535L).shortValue());
    }

    @Test
    void testToString() {
        assertEquals("777", new WORD(777L).toString());
        assertEquals("0", new WORD(0L).toString());
        assertEquals("65535", new WORD(65535L).toString());
    }
}