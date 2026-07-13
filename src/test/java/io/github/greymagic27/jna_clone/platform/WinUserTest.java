package io.github.greymagic27.jna_clone.platform;

import io.github.greymagic27.jna_clone.WinDef.HWND;
import io.github.greymagic27.jna_clone.WinDef.LPARAM;
import io.github.greymagic27.jna_clone.WinDef.LRESULT;
import io.github.greymagic27.jna_clone.WinDef.WPARAM;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.github.greymagic27.jna_clone.platform.WinUser.SM_CXSCREEN;
import static io.github.greymagic27.jna_clone.platform.WinUser.SM_CYSCREEN;
import static io.github.greymagic27.jna_clone.platform.WinUser.SWP_NOZORDER;
import static io.github.greymagic27.jna_clone.platform.WinUser.SW_HIDE;
import static io.github.greymagic27.jna_clone.platform.WinUser.SW_SHOW;
import static io.github.greymagic27.jna_clone.platform.WinUser.WM_CLOSE;
import static io.github.greymagic27.jna_clone.platform.WinUser.WM_COMMAND;
import static io.github.greymagic27.jna_clone.platform.WinUser.WM_DESTROY;
import static io.github.greymagic27.jna_clone.platform.WinUser.WM_SIZE;
import static io.github.greymagic27.jna_clone.platform.WinUser.WS_BORDER;
import static io.github.greymagic27.jna_clone.platform.WinUser.WS_CAPTION;
import static io.github.greymagic27.jna_clone.platform.WinUser.WS_CHILD;
import static io.github.greymagic27.jna_clone.platform.WinUser.WS_DISABLED;
import static io.github.greymagic27.jna_clone.platform.WinUser.WS_HSCROLL;
import static io.github.greymagic27.jna_clone.platform.WinUser.WS_MAXIMIZE;
import static io.github.greymagic27.jna_clone.platform.WinUser.WS_MINIMIZE;
import static io.github.greymagic27.jna_clone.platform.WinUser.WS_OVERLAPPED;
import static io.github.greymagic27.jna_clone.platform.WinUser.WS_OVERLAPPEDWINDOW;
import static io.github.greymagic27.jna_clone.platform.WinUser.WS_POPUP;
import static io.github.greymagic27.jna_clone.platform.WinUser.WS_SYSMENU;
import static io.github.greymagic27.jna_clone.platform.WinUser.WS_VISIBLE;
import static io.github.greymagic27.jna_clone.platform.WinUser.WS_VSCROLL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class WinUserTest {

    @Test
    void testWsValues() {
        assertEquals(0x00000000, WS_OVERLAPPED);
        assertEquals(0x00CF0000, WS_OVERLAPPEDWINDOW);
        assertEquals(0x80000000, WS_POPUP);
        assertEquals(0x40000000, WS_CHILD);
        assertEquals(0x20000000, WS_MINIMIZE);
        assertEquals(0x10000000, WS_VISIBLE);
        assertEquals(0x08000000, WS_DISABLED);
        assertEquals(0x01000000, WS_MAXIMIZE);
        assertEquals(0x00C00000, WS_CAPTION);
        assertEquals(0x00800000, WS_BORDER);
        assertEquals(0x00200000, WS_VSCROLL);
        assertEquals(0x00100000, WS_HSCROLL);
        assertEquals(0x00080000, WS_SYSMENU);
    }

    @Test
    void testWmValues() {
        assertEquals(0x0002, WM_DESTROY);
        assertEquals(0x0005, WM_SIZE);
        assertEquals(0x0111, WM_COMMAND);
        assertEquals(0x0010, WM_CLOSE);
    }

    @Test
    void testSwValues() {
        assertEquals(5, SW_SHOW);
        assertEquals(0, SW_HIDE);
    }

    @Test
    void testSmValues() {
        assertEquals(0, SM_CXSCREEN);
        assertEquals(1, SM_CYSCREEN);
    }

    @Test
    void testMiscValues() {
        assertEquals(0x004, SWP_NOZORDER);
    }

    @Test
    void testWndClassEx() {
        WinUser.WNDCLASSEXW wndClass = new WinUser.WNDCLASSEXW();
        assertEquals(80, wndClass.size());
    }

    @Test
    void testMsg() {
        WinUser.MSG msg = new WinUser.MSG();
        assertEquals(48, msg.size());
    }

    @Test
    void testWndProc() {
        HWND hwnd = new HWND(0x12345678);
        int msg = WM_DESTROY;
        WPARAM wparam = new WPARAM(1);
        LPARAM lparam = new LPARAM(1);
        WinUser.Wndproc wndproc = (hWnd, uMsg, wParam, lParam) -> {
            assertEquals(hwnd.segment.address(), hWnd.segment.address());
            Assertions.assertEquals(msg, uMsg);
            Assertions.assertEquals(wparam.longValue(), wParam.longValue());
            Assertions.assertEquals(lparam.longValue(), lParam.longValue());
            return new LRESULT(0);
        };
        LRESULT result = wndproc.callback(hwnd, msg, wparam, lparam);
        assertNotNull(result);
        assertEquals(0, result.longValue());
    }
}