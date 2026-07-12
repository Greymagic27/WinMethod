package io.github.greymagic27.jna_clone;

import io.github.greymagic27.jna_clone.WinDef.BOOL;
import io.github.greymagic27.jna_clone.WinDef.HWND;
import io.github.greymagic27.jna_clone.platform.User32;
import io.github.greymagic27.jna_clone.platform.WinDef;
import io.github.greymagic27.jna_clone.platform.WinUser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EasyMethodsTest {

    private Thread windowThread;

    @AfterEach
    void tearDown() {
        HWND hwnd = EasyMethods.getCurrentWindow();
        if (hwnd != null) User32.INSTANCE.DestroyWindow(hwnd);
        if (windowThread != null && windowThread.isAlive()) windowThread.interrupt();
    }

    private void createTestWindow() {
        windowThread = new Thread(() -> {
            EasyMethods.createWindow(null, 800, 600);
            User32.INSTANCE.ShowWindow(EasyMethods.getCurrentWindow(), WinUser.SW_HIDE);
            EasyMethods.start();
        });
        windowThread.setDaemon(true);
        windowThread.start();
        long timeout = System.currentTimeMillis() + 2000;
        while (EasyMethods.getCurrentWindow() == null && System.currentTimeMillis() < timeout) Thread.onSpinWait();
        assertNotNull(EasyMethods.getCurrentWindow());
    }

    @Test
    void testWindowCreation() {
        createTestWindow();
        HWND hwnd = EasyMethods.getCurrentWindow();
        assertNotNull(hwnd);
        assertNotEquals(0, hwnd.segment.address());
    }

    @Test
    void testAllWindowPositions() {
        createTestWindow();
        WinDef.RECT intial = new WinDef.RECT();
        User32.INSTANCE.GetWindowRect(EasyMethods.getCurrentWindow(), intial);
        int width = intial.right.intValue() - intial.left.intValue();
        int height = intial.bottom.intValue() - intial.top.intValue();
        int screenWidth = User32.INSTANCE.GetSystemMetrics(WinUser.SM_CXSCREEN);
        int screenHeight = User32.INSTANCE.GetSystemMetrics(WinUser.SM_CYSCREEN);
        record ExpectedPosition(int x, int y) {
        }
        var positions = java.util.Map.of(EasyMethods.WindowPosition.CENTER, new ExpectedPosition((screenWidth - width) / 2, (screenHeight - height) / 2), EasyMethods.WindowPosition.TOP, new ExpectedPosition((screenWidth - width) / 2, 0), EasyMethods.WindowPosition.BOTTOM, new ExpectedPosition((screenWidth - width) / 2, screenHeight - height), EasyMethods.WindowPosition.LEFT, new ExpectedPosition(0, (screenHeight - height) / 2), EasyMethods.WindowPosition.RIGHT, new ExpectedPosition(screenWidth - width, (screenHeight - height) / 2), EasyMethods.WindowPosition.TOP_LEFT, new ExpectedPosition(0, 0), EasyMethods.WindowPosition.TOP_RIGHT, new ExpectedPosition(screenWidth - width, 0), EasyMethods.WindowPosition.BOTTOM_LEFT, new ExpectedPosition(0, screenHeight - height), EasyMethods.WindowPosition.BOTTOM_RIGHT, new ExpectedPosition(screenWidth - width, screenHeight - height));
        for (var entry : positions.entrySet()) {
            EasyMethods.setWindowPosition(entry.getKey());
            WinDef.RECT rect = new WinDef.RECT();
            BOOL result = User32.INSTANCE.GetWindowRect(EasyMethods.getCurrentWindow(), rect);
            assertTrue(result.booleanValue(), "GetWindowRect failed for " + entry.getKey());
            assertEquals(entry.getValue().x(), rect.left.intValue(), "Incorrect X position for " + entry.getKey());
            assertEquals(entry.getValue().y(), rect.top.intValue(), "Incorrect Y position for " + entry.getKey());
        }
    }

    @Test
    void testSetWindowPositionWithoutWindow() {
        assertThrows(IllegalStateException.class, () -> EasyMethods.setWindowPosition(EasyMethods.WindowPosition.CENTER));
    }
}
