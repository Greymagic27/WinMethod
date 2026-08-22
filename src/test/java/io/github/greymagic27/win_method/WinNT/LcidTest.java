package io.github.greymagic27.win_method.WinNT;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LcidTest {

    @Test
    void testConstructorStoresValue() {
        assertEquals(1033, new LCID(1033).intValue());
        assertEquals(0, new LCID(0).intValue());
        assertEquals(-123, new LCID(-123).intValue());
    }

    @Test
    void testLcidValue() {
        assertEquals(1033, new LCID(1033).intValue());
    }

    @Test
    void testToStringContainsValue() {
        assertTrue(new LCID(1033).toString().contains("1033"));
    }
}