package io.github.greymagic27.win_method.WinDef;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ByteTest {

    @Test
    void testConstructorStoresValue() {
        assertEquals(42, new BYTE((byte) 42).byteValue());
        assertEquals(0, new BYTE((byte) 0).byteValue());
        assertEquals(-123, new BYTE((byte) -123).byteValue());
    }

    @Test
    void testLongValue() {
        assertEquals(7L, new BYTE((byte) 7).byteValue());
    }

    @Test
    void testToStringContainsValue() {
        assertTrue(new BYTE((byte) 123).toString().contains("123"));
    }
}
