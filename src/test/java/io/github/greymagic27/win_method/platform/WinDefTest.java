package io.github.greymagic27.win_method.platform;

import io.github.greymagic27.win_method.WinDef.LONG;
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
        assertEquals(10, point.x.intValue());
        assertEquals(20, point.y.intValue());
    }

    @Test
    void testRect() {
        WinDef.RECT rect = new WinDef.RECT();
        LONG left = new LONG(5);
        LONG top = new LONG(10);
        LONG right = new LONG(15);
        LONG bottom = new LONG(20);
        rect.left = left;
        rect.top = top;
        rect.right = right;
        rect.bottom = bottom;
        assertNotNull(rect.left);
        assertNotNull(rect.top);
        assertNotNull(rect.right);
        assertNotNull(rect.bottom);
        assertEquals(5, rect.left.intValue());
        assertEquals(10, rect.top.intValue());
        assertEquals(15, rect.right.intValue());
        assertEquals(20, rect.bottom.intValue());
    }
}