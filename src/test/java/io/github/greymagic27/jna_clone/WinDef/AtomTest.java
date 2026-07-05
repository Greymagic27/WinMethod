package io.github.greymagic27.jna_clone.WinDef;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AtomTest {

    @Test
    void testConstructorStoresValue() {
        assertEquals(42, new ATOM((short) 42).shortValue());
        assertEquals(0, new ATOM((short) 0).shortValue());
        assertEquals(-123, new ATOM((short) -123).shortValue());
    }

    @Test
    void testShortValue() {
        assertEquals(7L, new ATOM((short) 7).shortValue());
    }

    @Test
    void testToStringContainsValue() {
        assertTrue(new ATOM((short) 777).toString().contains("777"));
    }
}
