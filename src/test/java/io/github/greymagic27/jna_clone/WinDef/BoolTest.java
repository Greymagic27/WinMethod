package io.github.greymagic27.jna_clone.WinDef;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BoolTest {

    @Test
    void testBooleanConversion() {
        assertFalse(new BOOL(0).booleanValue());
        assertTrue(new BOOL(1).booleanValue());
        assertTrue(new BOOL(-1).booleanValue());
        assertTrue(new BOOL(42).booleanValue());
    }

    @Test
    void testIntValue() {
        assertEquals(7, new BOOL(7).intValue());
    }

    @Test
    void testToString() {
        assertEquals("false", new BOOL(0).toString());
        assertEquals("true", new BOOL(1).toString());
    }
}