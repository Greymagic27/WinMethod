package io.github.greymagic27.jna_clone.platform;

import io.github.greymagic27.jna_clone.WinDef.LONG;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class WinDefTest {

    @Test
    void testPoint() {
        WinDef.POINT point = new WinDef.POINT();
        LONG x = new LONG(10);
        LONG y = new LONG(20);
        point.x = x;
        point.y = y;
        assertNotNull(point.x);
        assertNotNull(point.y);
        assertEquals(10, point.x.longValue());
        assertEquals(20, point.y.longValue());
    }
}