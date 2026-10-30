package io.github.greymagic27.win_method.WinNT;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BooleanTest {

    @Test
    void testByteConstructorStoresValue() {
        assertEquals(1, new BOOLEAN((byte) 1).byteValue());
        assertEquals(0, new BOOLEAN((byte) 0).byteValue());
        assertEquals(2, new BOOLEAN((byte) 2).byteValue());
    }

    @Test
    void testBooleanConstructorStoresValue() {
        assertEquals(1, new BOOLEAN(true).byteValue());
        assertEquals(0, new BOOLEAN(false).byteValue());
    }

    @Test
    void testBooleanValue() {
        assertTrue(new BOOLEAN((byte) 1).booleanValue());
        assertTrue(new BOOLEAN((byte) 2).booleanValue());
        assertFalse(new BOOLEAN((byte) 0).booleanValue());
    }

    @Test
    void testToString() {
        assertEquals("true", new BOOLEAN(true).toString());
        assertEquals("false", new BOOLEAN(false).toString());
    }
}