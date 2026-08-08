package io.github.greymagic27.win_method.WinNT;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WCharTest {

    @Test
    void testConstructorStoresValue() {
        assertEquals('A', new WCHAR('A').charValue());
        assertEquals('\0', new WCHAR('\0').charValue());
        assertEquals('\uFFFF', new WCHAR('\uFFFF').charValue());
    }

    @Test
    void testCharValue() {
        assertEquals('G', new WCHAR('G').charValue());
    }

    @SuppressWarnings("UnnecessaryUnicodeEscape")
    @Test
    void testUnicodeCharacter() {
        WCHAR wchar = new WCHAR('\u03A9');
        assertEquals('\u03A9', wchar.charValue());
        assertEquals("Ω", wchar.toString());
    }

    @Test
    void testToStringContainsValue() {
        assertEquals("A", new WCHAR('A').toString());
    }

    @Test
    void testToStringArray() {
        WCHAR[] value = {new WCHAR('H'), new WCHAR('e'), new WCHAR('l'), new WCHAR('l'), new WCHAR('o'), new WCHAR('\0'), new WCHAR('X')};
        assertEquals("Hello", WCHAR.toString(value));
    }
}