package io.github.greymagic27.jna_clone.WinDef;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WParamTest {

    @Test
    void testConstructorStoresValue() {
        assertEquals(42, new WPARAM(42).intValue());
        assertEquals(0, new WPARAM(0).intValue());
        assertEquals(-123, new WPARAM(-123).intValue());
    }

    @Test
    void testLongValue() {
        assertEquals(7L, new WPARAM(7).intValue());
    }

    @Test
    void testToStringContainsValue() {
        assertTrue(new WPARAM(777).toString().contains("777"));
    }
}
