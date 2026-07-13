package io.github.greymagic27.jna_clone.methods;

import io.github.greymagic27.jna_clone.WinDef.BOOL;
import io.github.greymagic27.jna_clone.WinDef.HWND;
import io.github.greymagic27.jna_clone.WinDef.LRESULT;
import io.github.greymagic27.jna_clone.platform.User32;
import io.github.greymagic27.jna_clone.platform.WinDef;
import io.github.greymagic27.jna_clone.platform.WinUser;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WindowTest {
    private Thread windowThread;

    @AfterEach
    void tearDown() {
        HWND hwnd = Window.getCurrentWindow();
        if (hwnd != null) User32.INSTANCE.DestroyWindow(hwnd);
        if (windowThread != null && windowThread.isAlive()) windowThread.interrupt();
    }

    private void createTestWindow() {
        windowThread = new Thread(() -> {
            Window.createWindow(WinUser.Wndproc.defaultWndProc(), "Test", 800, 600);
            User32.INSTANCE.ShowWindow(Window.getCurrentWindow(), WinUser.SW_HIDE);
            Window.start();
        });
        windowThread.setDaemon(true);
        windowThread.start();
        long timeout = System.currentTimeMillis() + 2000;
        while (Window.getCurrentWindow() == null && System.currentTimeMillis() < timeout) Thread.onSpinWait();
        assertNotNull(Window.getCurrentWindow());
    }

    @Test
    void testWindowCreation() {
        createTestWindow();
        HWND hwnd = Window.getCurrentWindow();
        assertNotNull(hwnd);
        assertNotEquals(0, hwnd.segment.address());
    }

    @Test
    void testAllWindowPositions() {
        createTestWindow();
        WinDef.RECT intial = new WinDef.RECT();
        User32.INSTANCE.GetWindowRect(Window.getCurrentWindow(), intial);
        int width = intial.right.intValue() - intial.left.intValue();
        int height = intial.bottom.intValue() - intial.top.intValue();
        int screenWidth = User32.INSTANCE.GetSystemMetrics(WinUser.SM_CXSCREEN);
        int screenHeight = User32.INSTANCE.GetSystemMetrics(WinUser.SM_CYSCREEN);
        record ExpectedPosition(int x, int y) {
        }
        var positions = java.util.Map.of(WindowPosition.CENTER, new ExpectedPosition((screenWidth - width) / 2, (screenHeight - height) / 2), WindowPosition.TOP, new ExpectedPosition((screenWidth - width) / 2, 0), WindowPosition.BOTTOM, new ExpectedPosition((screenWidth - width) / 2, screenHeight - height), WindowPosition.LEFT, new ExpectedPosition(0, (screenHeight - height) / 2), WindowPosition.RIGHT, new ExpectedPosition(screenWidth - width, (screenHeight - height) / 2), WindowPosition.TOP_LEFT, new ExpectedPosition(0, 0), WindowPosition.TOP_RIGHT, new ExpectedPosition(screenWidth - width, 0), WindowPosition.BOTTOM_LEFT, new ExpectedPosition(0, screenHeight - height), WindowPosition.BOTTOM_RIGHT, new ExpectedPosition(screenWidth - width, screenHeight - height));
        for (var entry : positions.entrySet()) {
            Window.setWindowPosition(entry.getKey());
            WinDef.RECT rect = new WinDef.RECT();
            BOOL result = User32.INSTANCE.GetWindowRect(Window.getCurrentWindow(), rect);
            assertTrue(result.booleanValue(), "GetWindowRect failed for " + entry.getKey());
            assertEquals(entry.getValue().x(), rect.left.intValue(), "Incorrect X position for " + entry.getKey());
            assertEquals(entry.getValue().y(), rect.top.intValue(), "Incorrect Y position for " + entry.getKey());
        }
    }

    @Test
    void testSetWindowPositionWithoutWindow() {
        assertThrows(IllegalStateException.class, () -> Window.setWindowPosition(WindowPosition.CENTER));
    }

    @Test
    void testCustomWndProc() throws InterruptedException {
        AtomicBoolean called = new AtomicBoolean(false);
        CountDownLatch ready = new CountDownLatch(1);
        windowThread = new Thread(() -> {
            Window.createWindow((hWnd, uMsg, wParam, lParam) -> {
                if (uMsg == WinUser.WM_CLOSE) {
                    called.set(true);
                    User32.INSTANCE.DestroyWindow(hWnd);
                    return new LRESULT(0);
                }
                return User32.INSTANCE.DefWindowProcW(hWnd, uMsg, wParam, lParam);
            }, "Custom WndProc Test", 800, 600);
            User32.INSTANCE.ShowWindow(Window.getCurrentWindow(), WinUser.SW_HIDE);
            ready.countDown();
            Window.start();
        });
        windowThread.setDaemon(true);
        windowThread.start();
        assertTrue(ready.await(2, TimeUnit.SECONDS));
        User32.INSTANCE.PostMessageW(Window.getCurrentWindow(), WinUser.WM_CLOSE, null, null).booleanValue();
        long timeout = System.currentTimeMillis() + 2000;
        while (!called.get() && System.currentTimeMillis() < timeout) Thread.onSpinWait();
        assertTrue(called.get(), "Custom Wndproc was not called");
    }
}