package io.github.greymagic27.win_method.BaseTsd;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LongPtrTest {

    @Test
    void testConstructorStoresValue() {
        assertEquals(42L, new LONG_PTR(42).longValue());
        assertEquals(0L, new LONG_PTR(0).longValue());
        assertEquals(-123L, new LONG_PTR(-123).longValue());
    }

    @Test
    void testLongValue() {
        assertEquals(7L, new LONG_PTR(7).longValue());
    }

    @Test
    void testToStringContainsValue() {
        assertTrue(new LONG_PTR(777).toString().contains("777"));
    }
}
