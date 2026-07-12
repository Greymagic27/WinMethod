package io.github.greymagic27.jna_clone.platform;

import io.github.greymagic27.jna_clone.WinDef.ATOM;
import io.github.greymagic27.jna_clone.WinDef.BOOL;
import io.github.greymagic27.jna_clone.WinDef.HINSTANCE;
import io.github.greymagic27.jna_clone.WinDef.HWND;
import io.github.greymagic27.jna_clone.WinDef.LPARAM;
import io.github.greymagic27.jna_clone.WinDef.LRESULT;
import io.github.greymagic27.jna_clone.WinDef.WPARAM;
import java.lang.foreign.MemorySegment;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.github.greymagic27.jna_clone.platform.WinUser.WS_OVERLAPPED;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class User32Test {
    private static final User32 user32 = User32.INSTANCE;
    private HWND hwnd;
    private WinUser.MSG msg;

    @BeforeEach
    void setUp() {
        hwnd = user32.CreateWindowExW(0, "STATIC", null, WS_OVERLAPPED, 100, 100, 500, 400, null, null, null, null);
        msg = new WinUser.MSG();
    }

    @AfterEach
    void tearDown() {
        user32.DestroyWindow(hwnd);
    }

    @Test
    void testRegisterClassEx() {
        WinUser.WNDCLASSEXW wndClass = new WinUser.WNDCLASSEXW();
        wndClass.cbSize = wndClass.size();
        wndClass.lpfnWndProc = user32::DefWindowProcW;
        wndClass.hInstance = new HINSTANCE(MemorySegment.NULL);
        wndClass.lpszClassName = "Test";
        ATOM atom = user32.RegisterClassExW(wndClass);
        assertNotNull(atom);
        assertTrue(Short.toUnsignedInt(atom.shortValue()) != 0);
    }

    @Test
    void testShowWindow() {
        BOOL result = user32.ShowWindow(hwnd, WinUser.SW_HIDE);
        assertNotNull(result);
    }

    @Test
    void testSetWindowPos() {
        BOOL result = user32.SetWindowPos(hwnd, null, 200, 300, 400, 300, WinUser.SWP_NOZORDER);
        assertTrue(result.booleanValue());
    }

    @Test
    void testUpdateWindow() {
        BOOL result = user32.UpdateWindow(hwnd);
        assertTrue(result.booleanValue());
    }

    @Test
    void testDestroyWindow() {
        HWND hwnd1 = user32.CreateWindowExW(0, "STATIC", "Temp", WS_OVERLAPPED, 0, 0, 10, 10, null, null, null, null);
        BOOL result = user32.DestroyWindow(hwnd1);
        assertTrue(result.booleanValue());
    }

    @Test
    void testTranslateMessage() {
        msg.hwnd = hwnd;
        msg.message = 0x0;
        msg.wParam = new WPARAM(0x41);
        msg.lParam = new LPARAM(0);
        BOOL result = user32.TranslateMessage(msg);
        assertNotNull(result);
    }

    @Test
    void testDispatchMessage() {
        msg.hwnd = hwnd;
        msg.message = 0x1;
        msg.wParam = new WPARAM(0);
        msg.lParam = new LPARAM(0);
        BOOL lresult = user32.DispatchMessageW(msg);
        assertNotNull(lresult);
    }

    @Test
    void testGetMessage() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicBoolean get = new AtomicBoolean(false);
        Thread t = new Thread(() -> {
            user32.PostQuitMessage(0);
            user32.GetMessageW(msg, null, 0, 0);
            get.set(true);
            latch.countDown();
        });
        t.start();
        assertTrue(latch.await(2, TimeUnit.SECONDS));
        assertTrue(get.get());
    }

    @Test
    void testGetWindowRect() {
        WinDef.RECT rect = new WinDef.RECT();
        BOOL result = user32.GetWindowRect(hwnd, rect);
        assertTrue(result.booleanValue());
        assertTrue(rect.right.intValue() > rect.left.intValue());
        assertTrue(rect.bottom.intValue() > rect.top.intValue());
    }

    @Test
    void testGetSystemMetrics() {
        int width = user32.GetSystemMetrics(WinUser.SM_CXSCREEN);
        int height = user32.GetSystemMetrics(WinUser.SM_CYSCREEN);
        assertTrue(width > 0);
        assertTrue(height > 0);
    }

    @Test
    void testDefWindowProc() {
        WPARAM wparam = new WPARAM(0);
        LPARAM lparam = new LPARAM(0);
        LRESULT result = user32.DefWindowProcW(hwnd, 0x000F, wparam, lparam);
        assertNotNull(result);
    }

    @Test
    void testCreateWindowEx() {
        assertNotNull(hwnd);
        assertNotEquals(0, hwnd.segment.address());
    }

    @Test
    void testPostQuitMessage() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicBoolean quit = new AtomicBoolean(false);
        Thread t = new Thread(() -> {
            user32.PostQuitMessage(0);
            int result = user32.GetMessageW(msg, null, 0, 0);
            if (result == 0) quit.set(true);
            latch.countDown();
        });
        t.start();
        assertTrue(latch.await(2, TimeUnit.SECONDS));
        assertTrue(quit.get());
    }
}