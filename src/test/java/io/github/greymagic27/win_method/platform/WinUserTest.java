package io.github.greymagic27.win_method.platform;

import io.github.greymagic27.win_method.WinDef.HWND;
import io.github.greymagic27.win_method.WinDef.LPARAM;
import io.github.greymagic27.win_method.WinDef.LRESULT;
import io.github.greymagic27.win_method.WinDef.WPARAM;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.github.greymagic27.win_method.platform.WinUser.ES_AUTOVSCROLL;
import static io.github.greymagic27.win_method.platform.WinUser.ES_MULTILINE;
import static io.github.greymagic27.win_method.platform.WinUser.ES_WANTRETURN;
import static io.github.greymagic27.win_method.platform.WinUser.IDOK;
import static io.github.greymagic27.win_method.platform.WinUser.MB_ERRORICON;
import static io.github.greymagic27.win_method.platform.WinUser.MB_OK;
import static io.github.greymagic27.win_method.platform.WinUser.MF_POPUP;
import static io.github.greymagic27.win_method.platform.WinUser.MF_SEPARATOR;
import static io.github.greymagic27.win_method.platform.WinUser.MF_STRING;
import static io.github.greymagic27.win_method.platform.WinUser.SM_CXSCREEN;
import static io.github.greymagic27.win_method.platform.WinUser.SM_CYSCREEN;
import static io.github.greymagic27.win_method.platform.WinUser.SWP_NOZORDER;
import static io.github.greymagic27.win_method.platform.WinUser.SW_HIDE;
import static io.github.greymagic27.win_method.platform.WinUser.SW_SHOW;
import static io.github.greymagic27.win_method.platform.WinUser.VK_ADD;
import static io.github.greymagic27.win_method.platform.WinUser.VK_BACK;
import static io.github.greymagic27.win_method.platform.WinUser.VK_CANCEL;
import static io.github.greymagic27.win_method.platform.WinUser.VK_CAPITAL;
import static io.github.greymagic27.win_method.platform.WinUser.VK_CLEAR;
import static io.github.greymagic27.win_method.platform.WinUser.VK_CONTROL;
import static io.github.greymagic27.win_method.platform.WinUser.VK_DECIMAL;
import static io.github.greymagic27.win_method.platform.WinUser.VK_DELETE;
import static io.github.greymagic27.win_method.platform.WinUser.VK_DIVIDE;
import static io.github.greymagic27.win_method.platform.WinUser.VK_DOWN;
import static io.github.greymagic27.win_method.platform.WinUser.VK_END;
import static io.github.greymagic27.win_method.platform.WinUser.VK_ESCAPE;
import static io.github.greymagic27.win_method.platform.WinUser.VK_EXECUTE;
import static io.github.greymagic27.win_method.platform.WinUser.VK_HELP;
import static io.github.greymagic27.win_method.platform.WinUser.VK_HOME;
import static io.github.greymagic27.win_method.platform.WinUser.VK_INSERT;
import static io.github.greymagic27.win_method.platform.WinUser.VK_LBUTTON;
import static io.github.greymagic27.win_method.platform.WinUser.VK_LEFT;
import static io.github.greymagic27.win_method.platform.WinUser.VK_MBUTTON;
import static io.github.greymagic27.win_method.platform.WinUser.VK_MENU;
import static io.github.greymagic27.win_method.platform.WinUser.VK_MULTIPLY;
import static io.github.greymagic27.win_method.platform.WinUser.VK_NEXT;
import static io.github.greymagic27.win_method.platform.WinUser.VK_OEM_COMMA;
import static io.github.greymagic27.win_method.platform.WinUser.VK_OEM_MINUS;
import static io.github.greymagic27.win_method.platform.WinUser.VK_OEM_PERIOD;
import static io.github.greymagic27.win_method.platform.WinUser.VK_OEM_PLUS;
import static io.github.greymagic27.win_method.platform.WinUser.VK_PAUSE;
import static io.github.greymagic27.win_method.platform.WinUser.VK_PRINT;
import static io.github.greymagic27.win_method.platform.WinUser.VK_PRIOR;
import static io.github.greymagic27.win_method.platform.WinUser.VK_RBUTTON;
import static io.github.greymagic27.win_method.platform.WinUser.VK_RETURN;
import static io.github.greymagic27.win_method.platform.WinUser.VK_RIGHT;
import static io.github.greymagic27.win_method.platform.WinUser.VK_SELECT;
import static io.github.greymagic27.win_method.platform.WinUser.VK_SEPARATOR;
import static io.github.greymagic27.win_method.platform.WinUser.VK_SHIFT;
import static io.github.greymagic27.win_method.platform.WinUser.VK_SNAPSHOT;
import static io.github.greymagic27.win_method.platform.WinUser.VK_SPACE;
import static io.github.greymagic27.win_method.platform.WinUser.VK_SUBTRACT;
import static io.github.greymagic27.win_method.platform.WinUser.VK_TAB;
import static io.github.greymagic27.win_method.platform.WinUser.VK_UP;
import static io.github.greymagic27.win_method.platform.WinUser.WM_CLOSE;
import static io.github.greymagic27.win_method.platform.WinUser.WM_COMMAND;
import static io.github.greymagic27.win_method.platform.WinUser.WM_DESTROY;
import static io.github.greymagic27.win_method.platform.WinUser.WM_KEYDOWN;
import static io.github.greymagic27.win_method.platform.WinUser.WM_MOUSEWHEEL;
import static io.github.greymagic27.win_method.platform.WinUser.WM_PAINT;
import static io.github.greymagic27.win_method.platform.WinUser.WM_SIZE;
import static io.github.greymagic27.win_method.platform.WinUser.WS_BORDER;
import static io.github.greymagic27.win_method.platform.WinUser.WS_CAPTION;
import static io.github.greymagic27.win_method.platform.WinUser.WS_CHILD;
import static io.github.greymagic27.win_method.platform.WinUser.WS_DISABLED;
import static io.github.greymagic27.win_method.platform.WinUser.WS_HSCROLL;
import static io.github.greymagic27.win_method.platform.WinUser.WS_MAXIMIZE;
import static io.github.greymagic27.win_method.platform.WinUser.WS_MINIMIZE;
import static io.github.greymagic27.win_method.platform.WinUser.WS_OVERLAPPED;
import static io.github.greymagic27.win_method.platform.WinUser.WS_OVERLAPPEDWINDOW;
import static io.github.greymagic27.win_method.platform.WinUser.WS_POPUP;
import static io.github.greymagic27.win_method.platform.WinUser.WS_SYSMENU;
import static io.github.greymagic27.win_method.platform.WinUser.WS_VISIBLE;
import static io.github.greymagic27.win_method.platform.WinUser.WS_VSCROLL;
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
        assertEquals(0x000F, WM_PAINT);
        assertEquals(0x0100, WM_KEYDOWN);
        assertEquals(0x020A, WM_MOUSEWHEEL);
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
    void testEsValues() {
        assertEquals(0x0004, ES_MULTILINE);
        assertEquals(0x0040, ES_AUTOVSCROLL);
        assertEquals(0x1000, ES_WANTRETURN);
    }

    @Test
    void testMfValues() {
        assertEquals(0x00000000, MF_STRING);
        assertEquals(0x00000010, MF_POPUP);
        assertEquals(0x00000800, MF_SEPARATOR);
    }

    @Test
    void testVkValues() {
        assertEquals(0x01, VK_LBUTTON);
        assertEquals(0x02, VK_RBUTTON);
        assertEquals(0x03, VK_CANCEL);
        assertEquals(0x04, VK_MBUTTON);
        assertEquals(0x08, VK_BACK);
        assertEquals(0x09, VK_TAB);
        assertEquals(0x0C, VK_CLEAR);
        assertEquals(0x0D, VK_RETURN);
        assertEquals(0x10, VK_SHIFT);
        assertEquals(0x11, VK_CONTROL);
        assertEquals(0x12, VK_MENU);
        assertEquals(0x13, VK_PAUSE);
        assertEquals(0x14, VK_CAPITAL);
        assertEquals(0x1B, VK_ESCAPE);
        assertEquals(0x20, VK_SPACE);
        assertEquals(0x21, VK_PRIOR);
        assertEquals(0x22, VK_NEXT);
        assertEquals(0x23, VK_END);
        assertEquals(0x24, VK_HOME);
        assertEquals(0x25, VK_LEFT);
        assertEquals(0x26, VK_UP);
        assertEquals(0x27, VK_RIGHT);
        assertEquals(0x28, VK_DOWN);
        assertEquals(0x29, VK_SELECT);
        assertEquals(0x2A, VK_PRINT);
        assertEquals(0x2B, VK_EXECUTE);
        assertEquals(0x2C, VK_SNAPSHOT);
        assertEquals(0x2D, VK_INSERT);
        assertEquals(0x2E, VK_DELETE);
        assertEquals(0x2F, VK_HELP);
        assertEquals(0x6A, VK_MULTIPLY);
        assertEquals(0x6B, VK_ADD);
        assertEquals(0x6C, VK_SEPARATOR);
        assertEquals(0x6D, VK_SUBTRACT);
        assertEquals(0x6E, VK_DECIMAL);
        assertEquals(0x6F, VK_DIVIDE);
        assertEquals(0xBB, VK_OEM_PLUS);
        assertEquals(0xBC, VK_OEM_COMMA);
        assertEquals(0xBD, VK_OEM_MINUS);
        assertEquals(0xBE, VK_OEM_PERIOD);
    }

    @Test
    void testMbValues() {
        assertEquals(0x00000000, MB_OK);
        assertEquals(0x00000010L, MB_ERRORICON);
    }

    @Test
    void testMiscValues() {
        assertEquals(0x004, SWP_NOZORDER);
        assertEquals(1, IDOK);
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