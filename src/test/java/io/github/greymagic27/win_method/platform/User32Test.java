package io.github.greymagic27.win_method.platform;

import io.github.greymagic27.win_method.WinDef.ATOM;
import io.github.greymagic27.win_method.WinDef.BOOL;
import io.github.greymagic27.win_method.WinDef.HINSTANCE;
import io.github.greymagic27.win_method.WinDef.HWND;
import io.github.greymagic27.win_method.WinDef.LPARAM;
import io.github.greymagic27.win_method.WinDef.LRESULT;
import io.github.greymagic27.win_method.WinDef.WPARAM;
import java.lang.foreign.MemorySegment;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class User32Test {
    private static final User32 user32 = User32.INSTANCE;
    private HWND window;
    private WinUser.MSG msg;

    @BeforeEach
    void setUp() {
        window = user32.CreateWindowExW(0, "STATIC", null, WinUser.WS_OVERLAPPED, 100, 100, 500, 400, null, null, null, null);
        msg = new WinUser.MSG();
    }

    @AfterEach
    void tearDown() {
        user32.DestroyWindow(window);
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
        BOOL result = user32.ShowWindow(window, WinUser.SW_HIDE);
        assertNotNull(result);
    }

    @Test
    void testSetWindowPos() {
        BOOL result = user32.SetWindowPos(window, null, 200, 300, 400, 300, WinUser.SWP_NOZORDER);
        assertTrue(result.booleanValue());
    }

    @Test
    void testUpdateWindow() {
        BOOL result = user32.UpdateWindow(window);
        assertTrue(result.booleanValue());
    }

    @Test
    void testDestroyWindow() {
        HWND hwnd1 = user32.CreateWindowExW(0, "STATIC", "Temp", WinUser.WS_OVERLAPPED, 0, 0, 10, 10, null, null, null, null);
        BOOL result = user32.DestroyWindow(hwnd1);
        assertTrue(result.booleanValue());
    }

    @Test
    void testTranslateMessage() {
        msg.hwnd = window;
        msg.message = 0x0;
        msg.wParam = new WPARAM(0x41);
        msg.lParam = new LPARAM(0);
        BOOL result = user32.TranslateMessage(msg);
        assertNotNull(result);
    }

    @Test
    void testDispatchMessage() {
        msg.hwnd = window;
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
        BOOL result = user32.GetWindowRect(window, rect);
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
        LRESULT result = user32.DefWindowProcW(window, 0x000F, wparam, lparam);
        assertNotNull(result);
    }

    @Test
    void testCreateWindowEx() {
        assertNotNull(window);
        assertNotEquals(0, window.segment.address());
    }

    @Test
    void testPostQuitMessage() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicBoolean quit = new AtomicBoolean(false);
        Thread t = new Thread(() -> {
            user32.PostQuitMessage(0);
            BOOL result = user32.GetMessageW(msg, null, 0, 0);
            if (!result.booleanValue()) quit.set(true);
            latch.countDown();
        });
        t.start();
        assertTrue(latch.await(2, TimeUnit.SECONDS));
        assertTrue(quit.get());
    }

    @Test
    void testPostMessage() throws InterruptedException {
        CountDownLatch ready = new CountDownLatch(1);
        AtomicBoolean received = new AtomicBoolean(false);
        int testMessage = WinUser.WM_COMMAND;
        Thread window = new Thread(() -> {
            WinUser.WNDCLASSEXW wc = new WinUser.WNDCLASSEXW();
            wc.cbSize = wc.size();
            wc.lpfnWndProc = (hWnd, uMsg, wParam, lParam) -> {
                if (uMsg == testMessage) {
                    received.set(true);
                    return new LRESULT(0);
                }
                return user32.DefWindowProcW(hWnd, uMsg, wParam, lParam);
            };
            wc.hInstance = new HINSTANCE(MemorySegment.NULL);
            wc.lpszClassName = "PostMessageTestClass";
            user32.RegisterClassExW(wc);
            this.window = user32.CreateWindowExW(0, "PostMessageTestClass", "PostMessageTest", WinUser.WS_OVERLAPPED, 0, 0, 100, 100, null, null, null, null);
            ready.countDown();
            WinUser.MSG msg = new WinUser.MSG();
            while (user32.GetMessageW(msg, null, 0, 0).booleanValue()) {
                user32.TranslateMessage(msg);
                user32.DispatchMessageW(msg);
            }
        });
        window.start();
        assertTrue(ready.await(2, TimeUnit.SECONDS));
        BOOL result = user32.PostMessageW(this.window, testMessage, new WPARAM(0), new LPARAM(0));
        assertTrue(result.booleanValue());
        long timeout = System.currentTimeMillis() + 2000;
        while (!received.get() && System.currentTimeMillis() < timeout) {
            Thread.onSpinWait();
        }
        assertTrue(received.get(), "Message was not received");
        user32.PostQuitMessage(0);
    }

    @Test
    void testMoveWindow() {
        BOOL result = user32.MoveWindow(window, 200, 300, 400, 300, new BOOL(1));
        assertTrue(result.booleanValue());
        WinDef.RECT rect = new WinDef.RECT();
        BOOL getRectResult = user32.GetWindowRect(window, rect);
        assertTrue(getRectResult.booleanValue());
        assertTrue(rect.right.intValue() - rect.left.intValue() >= 400);
        assertTrue(rect.bottom.intValue() - rect.top.intValue() >= 300);
    }
}