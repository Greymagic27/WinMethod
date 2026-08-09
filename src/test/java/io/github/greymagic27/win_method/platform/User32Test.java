package io.github.greymagic27.win_method.platform;

import io.github.greymagic27.win_method.BaseTsd.UINT_PTR;
import io.github.greymagic27.win_method.IntSafe.DWORD;
import io.github.greymagic27.win_method.WinDef.ATOM;
import io.github.greymagic27.win_method.WinDef.BOOL;
import io.github.greymagic27.win_method.WinDef.HBRUSH;
import io.github.greymagic27.win_method.WinDef.HDC;
import io.github.greymagic27.win_method.WinDef.HGDIOBJ;
import io.github.greymagic27.win_method.WinDef.HINSTANCE;
import io.github.greymagic27.win_method.WinDef.HMENU;
import io.github.greymagic27.win_method.WinDef.HWND;
import io.github.greymagic27.win_method.WinDef.LPARAM;
import io.github.greymagic27.win_method.WinDef.LRESULT;
import io.github.greymagic27.win_method.WinDef.UINT;
import io.github.greymagic27.win_method.WinDef.WPARAM;
import io.github.greymagic27.win_method.WinNT.LONG;
import io.github.greymagic27.win_method.WinNT.LPCWSTR;
import io.github.greymagic27.win_method.WinNT.SHORT;
import io.github.greymagic27.win_method.types.WinDef;
import io.github.greymagic27.win_method.types.WinGdi;
import io.github.greymagic27.win_method.types.WinUser;
import java.lang.foreign.MemorySegment;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class User32Test {
    private static final User32 user32 = User32.INSTANCE;
    private HWND window;
    private HDC hdc;
    private WinUser.MSG msg;
    private WinDef.RECT rect;

    @BeforeEach
    void setUp() {
        window = user32.CreateWindowExW(new DWORD(0), new LPCWSTR("STATIC"), null, new DWORD(WinUser.WS_OVERLAPPED), 100, 100, 500, 400, null, null, null, null);
        hdc = user32.GetDC(window);
        msg = new WinUser.MSG();
        rect = new WinDef.RECT();
    }

    @AfterEach
    void tearDown() {
        user32.ReleaseDC(window, hdc);
        user32.DestroyWindow(window);
    }

    @Test
    void testRegisterClassEx() {
        WinUser.WNDCLASSEXW wndClass = new WinUser.WNDCLASSEXW();
        wndClass.cbSize = new UINT(wndClass.size());
        wndClass.lpfnWndProc = user32::DefWindowProcW;
        wndClass.hInstance = new HINSTANCE(MemorySegment.NULL);
        wndClass.lpszClassName = new LPCWSTR("Test");
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
        BOOL result = user32.SetWindowPos(window, null, 200, 300, 400, 300, new UINT(WinUser.SWP_NOZORDER));
        assertTrue(result.booleanValue());
    }

    @Test
    void testUpdateWindow() {
        BOOL result = user32.UpdateWindow(window);
        assertTrue(result.booleanValue());
    }

    @Test
    void testDestroyWindow() {
        HWND hwnd1 = user32.CreateWindowExW(new DWORD(0), new LPCWSTR("STATIC"), new LPCWSTR("Temp"), new DWORD(WinUser.WS_OVERLAPPED), 0, 0, 10, 10, null, null, null, null);
        BOOL result = user32.DestroyWindow(hwnd1);
        assertTrue(result.booleanValue());
    }

    @Test
    void testTranslateMessage() {
        msg.hwnd = window;
        msg.message = new UINT(0x0);
        msg.wParam = new WPARAM(0x41);
        msg.lParam = new LPARAM(0);
        BOOL result = user32.TranslateMessage(msg);
        assertNotNull(result);
    }

    @Test
    void testDispatchMessage() {
        msg.hwnd = window;
        msg.message = new UINT(0x1);
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
            user32.GetMessageW(msg, null, new UINT(0), new UINT(0));
            get.set(true);
            latch.countDown();
        });
        t.start();
        assertTrue(latch.await(2, TimeUnit.SECONDS));
        assertTrue(get.get());
    }

    @Test
    void testGetWindowRect() {
        BOOL result = user32.GetWindowRect(window, rect);
        assertTrue(result.booleanValue());
        assertTrue(rect.right.intValue() > rect.left.intValue());
        assertTrue(rect.bottom.intValue() > rect.top.intValue());
    }


    @Test
    void testInvalidateRect() {
        rect.left = new LONG(0);
        rect.top = new LONG(0);
        rect.right = new LONG(100);
        rect.bottom = new LONG(100);
        BOOL result = user32.InvalidateRect(window, rect, new BOOL(0));
        assertTrue(result.booleanValue());
    }

    @Test
    void testGetClientRect() {
        BOOL result = user32.GetClientRect(window, rect);
        assertTrue(result.booleanValue());
        assertEquals(0, rect.left.intValue());
        assertEquals(0, rect.top.intValue());
        assertTrue(rect.right.intValue() > 0);
        assertTrue(rect.bottom.intValue() > 0);
        WinDef.RECT windowRect = new WinDef.RECT();
        assertTrue(user32.GetWindowRect(window, windowRect).booleanValue());
        assertTrue(rect.right.intValue() <= (windowRect.right.intValue() - windowRect.left.intValue()));
        assertTrue(rect.bottom.intValue() <= (windowRect.bottom.intValue() - windowRect.top.intValue()));
    }

    @Test
    void testFillRect() {
        assertNotNull(hdc);
        rect.left = new LONG(0);
        rect.top = new LONG(0);
        rect.right = new LONG(100);
        rect.bottom = new LONG(100);
        HGDIOBJ object = GDI32.INSTANCE.GetStockObject(WinGdi.WHITE_BRUSH);
        assertNotNull(object);
        HBRUSH hbrush = new HBRUSH(object);
        assertNotNull(hbrush);
        int result = user32.FillRect(hdc, rect, hbrush);
        assertTrue(result != 0);
        user32.ReleaseDC(window, hdc);
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
        LRESULT result = user32.DefWindowProcW(window, new UINT(0x000F), wparam, lparam);
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
            BOOL result = user32.GetMessageW(msg, null, new UINT(0), new UINT(0));
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
            wc.cbSize = new UINT(wc.size());
            wc.lpfnWndProc = (hWnd, uMsg, wParam, lParam) -> {
                if (uMsg.intValue() == testMessage) {
                    received.set(true);
                    return new LRESULT(0);
                }
                return user32.DefWindowProcW(hWnd, uMsg, wParam, lParam);
            };
            wc.hInstance = new HINSTANCE(MemorySegment.NULL);
            wc.lpszClassName = new LPCWSTR("PostMessageTestClass");
            user32.RegisterClassExW(wc);
            this.window = user32.CreateWindowExW(new DWORD(0), wc.lpszClassName, new LPCWSTR("PostMessageTest"), new DWORD(WinUser.WS_OVERLAPPED), 0, 0, 100, 100, null, null, null, null);
            ready.countDown();
            WinUser.MSG msg = new WinUser.MSG();
            while (user32.GetMessageW(msg, null, new UINT(0), new UINT(0)).booleanValue()) {
                user32.TranslateMessage(msg);
                user32.DispatchMessageW(msg);
            }
        });
        window.start();
        assertTrue(ready.await(2, TimeUnit.SECONDS));
        BOOL result = user32.PostMessageW(this.window, new UINT(testMessage), new WPARAM(0), new LPARAM(0));
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
        BOOL getRectResult = user32.GetWindowRect(window, rect);
        assertTrue(getRectResult.booleanValue());
        assertTrue(rect.right.intValue() - rect.left.intValue() >= 400);
        assertTrue(rect.bottom.intValue() - rect.top.intValue() >= 300);
    }

    @Test
    void testAppendMenu() {
        HMENU hmenu = user32.CreateMenu();
        assertNotNull(hmenu);
        assertNotEquals(0, hmenu.segment.address());
        BOOL result = user32.AppendMenuW(hmenu, new UINT(WinUser.MF_STRING), new UINT_PTR(1001), new LPCWSTR("Test Item"));
        assertTrue(result.booleanValue());
        user32.DestroyMenu(hmenu);
    }

    @Test
    void testAppendPopupMenu() {
        HMENU menuBar = user32.CreateMenu();
        HMENU popupMenu = user32.CreatePopupMenu();
        assertNotNull(menuBar);
        assertNotNull(popupMenu);
        BOOL result = user32.AppendMenuW(menuBar, new UINT(WinUser.MF_POPUP), new UINT_PTR(popupMenu.segment.address()), new LPCWSTR("Test"));
        assertTrue(result.booleanValue());
        user32.DestroyMenu(popupMenu);
        user32.DestroyMenu(menuBar);
    }

    @Test
    void testSetMenu() {
        HMENU hmenu = user32.CreateMenu();
        BOOL result = user32.SetMenu(window, hmenu);
        assertTrue(result.booleanValue());
        user32.DestroyMenu(hmenu);
    }

    @Test
    void testDestroyMenu() {
        HMENU menu = user32.CreateMenu();
        assertNotNull(menu);
        assertNotEquals(0, menu.segment.address());
        BOOL result = user32.DestroyMenu(menu);
        assertTrue(result.booleanValue());
    }

    @Test
    void testGetKeyState() {
        SHORT result = user32.GetKeyState(WinUser.VK_SHIFT);
        assertNotNull(result);
    }

    @Test
    void testMessageBox() {
        int result = user32.MessageBoxW(window, new LPCWSTR("Test"), new LPCWSTR("Test"), new UINT(WinUser.MB_OK));
        assertEquals(WinUser.IDOK, result);
    }

    @Test
    void testSetWindowText() {
        assertNotNull(window);
        assertNotEquals(0, window.segment.address());
        BOOL result = user32.SetWindowTextW(window, new LPCWSTR("New Window Title"));
        assertNotNull(result);
        assertTrue(result.booleanValue());
    }

    @Test
    void testBeginPaint() {
        assertTrue(user32.InvalidateRect(window, null, new BOOL(1)).booleanValue());
        assertTrue(user32.UpdateWindow(window).booleanValue());
        WinUser.PAINTSTRUCT paintstruct = new WinUser.PAINTSTRUCT();
        HDC hdc = user32.BeginPaint(window, paintstruct);
        assertNotNull(hdc);
        assertNotEquals(0, hdc.segment.address());
        assertNotNull(paintstruct.hdc);
        assertEquals(hdc.segment.address(), paintstruct.hdc.segment.address());
        assertTrue(user32.EndPaint(window, paintstruct).booleanValue());
    }

    @Test
    void testEndPaint() {
        WinUser.PAINTSTRUCT paintstruct = new WinUser.PAINTSTRUCT();
        assertNotNull(user32.BeginPaint(window, paintstruct));
        assertNotEquals(0, paintstruct.hdc.segment.address());
        BOOL result = user32.EndPaint(window, paintstruct);
        assertNotNull(result);
        assertTrue(result.booleanValue());
    }

    @Test
    void testDrawMenuBar() {
        HMENU hmenu = user32.CreateMenu();
        assertNotNull(hmenu);
        assertNotEquals(0, hmenu.segment.address());
        BOOL appendResult = user32.AppendMenuW(hmenu, new UINT(WinUser.MF_STRING), new UINT_PTR(1001), new LPCWSTR("Test Item"));
        assertTrue(appendResult.booleanValue());
        BOOL setMenuResult = user32.SetMenu(window, hmenu);
        assertTrue(setMenuResult.booleanValue());
        BOOL drawResult = user32.DrawMenuBar(window);
        assertNotNull(drawResult);
        assertTrue(drawResult.booleanValue());
        user32.DestroyMenu(hmenu);
    }

    @Test
    void testGetDC() {
        assertNotNull(hdc);
        assertNotEquals(0, hdc.segment.address());
    }

    @Test
    void testReleaseDC() {
        assertNotNull(hdc);
        assertNotEquals(0, hdc.segment.address());
        int result = user32.ReleaseDC(window, hdc);
        assertEquals(1, result);
    }

    @Test
    void testGetParent() {
        HWND child = user32.CreateWindowExW(new DWORD(0), new LPCWSTR("STATIC"), new LPCWSTR("Child"), new DWORD(WinUser.WS_CHILD | WinUser.WS_VISIBLE), 0, 0, 100, 100, window, new HMENU(MemorySegment.ofAddress(0)), null, null);
        assertNotNull(child);
        assertNotEquals(0, child.segment.address());
        HWND parent = user32.GetParent(child);
        assertNotNull(parent);
        assertEquals(window.segment.address(), parent.segment.address());
        user32.DestroyWindow(child);
    }

    @Test
    void testGetDlgCtrlID() {
        int id = 100;
        HWND child = user32.CreateWindowExW(new DWORD(0), new LPCWSTR("STATIC"), new LPCWSTR("Child"), new DWORD(WinUser.WS_CHILD | WinUser.WS_VISIBLE), 0, 0, 100, 100, window, new HMENU(MemorySegment.ofAddress(id)), null, null);
        assertNotNull(child);
        assertNotEquals(0, child.segment.address());
        int result = user32.GetDlgCtrlID(child);
        assertEquals(id, result);
        user32.DestroyWindow(child);
    }
}