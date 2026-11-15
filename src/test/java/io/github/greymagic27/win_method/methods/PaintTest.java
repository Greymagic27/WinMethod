package io.github.greymagic27.win_method.methods;

import io.github.greymagic27.win_method.WinDef.HWND;
import io.github.greymagic27.win_method.WinDef.UINT;
import io.github.greymagic27.win_method.platform.User32;
import io.github.greymagic27.win_method.types.WinDef;
import io.github.greymagic27.win_method.types.WinUser;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PaintTest {

    private HWND hwnd;

    @BeforeEach
    void setUp() {
        Window.createWindow(WinUser.Wndproc.defaultWndProc(), "PaintTest", 800, 600, false);
        hwnd = Window.getCurrentWindow();
    }

    @AfterEach
    void tearDown() {
        if (hwnd != null) User32.INSTANCE.DestroyWindow(hwnd);
        Window.reset();
    }

    @Test
    void testPaint() {
        AtomicBoolean called = new AtomicBoolean(false);
        AtomicInteger width = new AtomicInteger();
        AtomicInteger height = new AtomicInteger();
        Paint.paint(Window.getCurrentWindow(), (hdc, width1, height1) -> {
            called.set(true);
            assertNotNull(hdc);
            assertNotEquals(0, hdc.segment.address());
            width.set(width1);
            height.set(height1);
        });
        WinDef.RECT rect = new WinDef.RECT();
        assertTrue(User32.INSTANCE.GetClientRect(hwnd, rect).booleanValue());
        assertTrue(called.get());
        assertTrue(width.get() > 0);
        assertTrue(height.get() > 0);
        assertEquals(rect.right.intValue() - rect.left.intValue(), width.get());
        assertEquals(rect.bottom.intValue() - rect.top.intValue(), height.get());
    }

    @Test
    void testPaintZeroSizedWindow() {
        User32.INSTANCE.SetWindowPos(hwnd, null, 0, 0, 0, 0, new UINT(0));
        AtomicBoolean called = new AtomicBoolean(false);
        Paint.paint(hwnd, (_, _, _) -> called.set(true));
        assertFalse(called.get());
    }

    @Test
    void testPaintCallbackException() {
        assertThrows(RuntimeException.class, () ->
                Paint.paint(hwnd, (_, _, _) -> {
                    throw new RuntimeException("Test exception");
                })
        );
    }
}