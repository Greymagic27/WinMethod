package io.github.greymagic27.win_method.types;

import io.github.greymagic27.win_method.WinDef.BOOL;
import io.github.greymagic27.win_method.WinDef.BYTE;
import io.github.greymagic27.win_method.WinDef.HDC;
import io.github.greymagic27.win_method.WinDef.HWND;
import io.github.greymagic27.win_method.WinDef.LPARAM;
import io.github.greymagic27.win_method.WinDef.LRESULT;
import io.github.greymagic27.win_method.WinDef.UINT;
import io.github.greymagic27.win_method.WinDef.WPARAM;
import io.github.greymagic27.win_method.WinNT.LONG;
import io.github.greymagic27.win_method.WinNT.LPCWSTR;
import java.lang.foreign.MemorySegment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.github.greymagic27.win_method.types.WinUser.BM_SETIMAGE;
import static io.github.greymagic27.win_method.types.WinUser.BS_BITMAP;
import static io.github.greymagic27.win_method.types.WinUser.CS_HREDRAW;
import static io.github.greymagic27.win_method.types.WinUser.CS_VREDRAW;
import static io.github.greymagic27.win_method.types.WinUser.EM_GETLINE;
import static io.github.greymagic27.win_method.types.WinUser.EM_GETSEL;
import static io.github.greymagic27.win_method.types.WinUser.EM_LINEFROMCHAR;
import static io.github.greymagic27.win_method.types.WinUser.EM_LINEINDEX;
import static io.github.greymagic27.win_method.types.WinUser.EM_LINELENGTH;
import static io.github.greymagic27.win_method.types.WinUser.EM_REPLACESEL;
import static io.github.greymagic27.win_method.types.WinUser.EM_SETSEL;
import static io.github.greymagic27.win_method.types.WinUser.ES_AUTOHSCROLL;
import static io.github.greymagic27.win_method.types.WinUser.ES_AUTOVSCROLL;
import static io.github.greymagic27.win_method.types.WinUser.ES_MULTILINE;
import static io.github.greymagic27.win_method.types.WinUser.ES_WANTRETURN;
import static io.github.greymagic27.win_method.types.WinUser.GWL_STYLE;
import static io.github.greymagic27.win_method.types.WinUser.IDCANCEL;
import static io.github.greymagic27.win_method.types.WinUser.IDNO;
import static io.github.greymagic27.win_method.types.WinUser.IDOK;
import static io.github.greymagic27.win_method.types.WinUser.IDYES;
import static io.github.greymagic27.win_method.types.WinUser.IMAGE_BITMAP;
import static io.github.greymagic27.win_method.types.WinUser.IMAGE_CURSOR;
import static io.github.greymagic27.win_method.types.WinUser.IMAGE_ICON;
import static io.github.greymagic27.win_method.types.WinUser.LBN_DBLCICK;
import static io.github.greymagic27.win_method.types.WinUser.LBS_HASSTRINGS;
import static io.github.greymagic27.win_method.types.WinUser.LBS_NOTIFY;
import static io.github.greymagic27.win_method.types.WinUser.LB_ADDSTRING;
import static io.github.greymagic27.win_method.types.WinUser.LB_GETCURSEL;
import static io.github.greymagic27.win_method.types.WinUser.LB_RESETCONTENT;
import static io.github.greymagic27.win_method.types.WinUser.LR_LOADFROMFILE;
import static io.github.greymagic27.win_method.types.WinUser.LR_SHARED;
import static io.github.greymagic27.win_method.types.WinUser.MB_ERRORICON;
import static io.github.greymagic27.win_method.types.WinUser.MB_ICONWARNING;
import static io.github.greymagic27.win_method.types.WinUser.MB_OK;
import static io.github.greymagic27.win_method.types.WinUser.MB_YESNO;
import static io.github.greymagic27.win_method.types.WinUser.MF_POPUP;
import static io.github.greymagic27.win_method.types.WinUser.MF_SEPARATOR;
import static io.github.greymagic27.win_method.types.WinUser.MF_STRING;
import static io.github.greymagic27.win_method.types.WinUser.SM_CXSCREEN;
import static io.github.greymagic27.win_method.types.WinUser.SM_CYSCREEN;
import static io.github.greymagic27.win_method.types.WinUser.SWP_NOZORDER;
import static io.github.greymagic27.win_method.types.WinUser.SW_HIDE;
import static io.github.greymagic27.win_method.types.WinUser.SW_SHOW;
import static io.github.greymagic27.win_method.types.WinUser.TPM_BOTTOMALIGN;
import static io.github.greymagic27.win_method.types.WinUser.TPM_CENTERALIGN;
import static io.github.greymagic27.win_method.types.WinUser.TPM_LEFTALIGN;
import static io.github.greymagic27.win_method.types.WinUser.TPM_LEFTBUTTON;
import static io.github.greymagic27.win_method.types.WinUser.TPM_NONOTIFY;
import static io.github.greymagic27.win_method.types.WinUser.TPM_RETURNCMD;
import static io.github.greymagic27.win_method.types.WinUser.TPM_RIGHTALIGN;
import static io.github.greymagic27.win_method.types.WinUser.TPM_RIGHTBUTTON;
import static io.github.greymagic27.win_method.types.WinUser.TPM_TOPALIGN;
import static io.github.greymagic27.win_method.types.WinUser.TPM_VCENTERALIGN;
import static io.github.greymagic27.win_method.types.WinUser.VK_0;
import static io.github.greymagic27.win_method.types.WinUser.VK_1;
import static io.github.greymagic27.win_method.types.WinUser.VK_2;
import static io.github.greymagic27.win_method.types.WinUser.VK_3;
import static io.github.greymagic27.win_method.types.WinUser.VK_4;
import static io.github.greymagic27.win_method.types.WinUser.VK_5;
import static io.github.greymagic27.win_method.types.WinUser.VK_6;
import static io.github.greymagic27.win_method.types.WinUser.VK_7;
import static io.github.greymagic27.win_method.types.WinUser.VK_8;
import static io.github.greymagic27.win_method.types.WinUser.VK_9;
import static io.github.greymagic27.win_method.types.WinUser.VK_A;
import static io.github.greymagic27.win_method.types.WinUser.VK_ADD;
import static io.github.greymagic27.win_method.types.WinUser.VK_B;
import static io.github.greymagic27.win_method.types.WinUser.VK_BACK;
import static io.github.greymagic27.win_method.types.WinUser.VK_C;
import static io.github.greymagic27.win_method.types.WinUser.VK_CANCEL;
import static io.github.greymagic27.win_method.types.WinUser.VK_CAPITAL;
import static io.github.greymagic27.win_method.types.WinUser.VK_CLEAR;
import static io.github.greymagic27.win_method.types.WinUser.VK_CONTROL;
import static io.github.greymagic27.win_method.types.WinUser.VK_D;
import static io.github.greymagic27.win_method.types.WinUser.VK_DECIMAL;
import static io.github.greymagic27.win_method.types.WinUser.VK_DELETE;
import static io.github.greymagic27.win_method.types.WinUser.VK_DIVIDE;
import static io.github.greymagic27.win_method.types.WinUser.VK_DOWN;
import static io.github.greymagic27.win_method.types.WinUser.VK_E;
import static io.github.greymagic27.win_method.types.WinUser.VK_END;
import static io.github.greymagic27.win_method.types.WinUser.VK_ESCAPE;
import static io.github.greymagic27.win_method.types.WinUser.VK_EXECUTE;
import static io.github.greymagic27.win_method.types.WinUser.VK_F;
import static io.github.greymagic27.win_method.types.WinUser.VK_G;
import static io.github.greymagic27.win_method.types.WinUser.VK_H;
import static io.github.greymagic27.win_method.types.WinUser.VK_HELP;
import static io.github.greymagic27.win_method.types.WinUser.VK_HOME;
import static io.github.greymagic27.win_method.types.WinUser.VK_I;
import static io.github.greymagic27.win_method.types.WinUser.VK_INSERT;
import static io.github.greymagic27.win_method.types.WinUser.VK_J;
import static io.github.greymagic27.win_method.types.WinUser.VK_K;
import static io.github.greymagic27.win_method.types.WinUser.VK_L;
import static io.github.greymagic27.win_method.types.WinUser.VK_LBUTTON;
import static io.github.greymagic27.win_method.types.WinUser.VK_LEFT;
import static io.github.greymagic27.win_method.types.WinUser.VK_M;
import static io.github.greymagic27.win_method.types.WinUser.VK_MBUTTON;
import static io.github.greymagic27.win_method.types.WinUser.VK_MENU;
import static io.github.greymagic27.win_method.types.WinUser.VK_MULTIPLY;
import static io.github.greymagic27.win_method.types.WinUser.VK_N;
import static io.github.greymagic27.win_method.types.WinUser.VK_NEXT;
import static io.github.greymagic27.win_method.types.WinUser.VK_O;
import static io.github.greymagic27.win_method.types.WinUser.VK_OEM_COMMA;
import static io.github.greymagic27.win_method.types.WinUser.VK_OEM_MINUS;
import static io.github.greymagic27.win_method.types.WinUser.VK_OEM_PERIOD;
import static io.github.greymagic27.win_method.types.WinUser.VK_OEM_PLUS;
import static io.github.greymagic27.win_method.types.WinUser.VK_P;
import static io.github.greymagic27.win_method.types.WinUser.VK_PAUSE;
import static io.github.greymagic27.win_method.types.WinUser.VK_PRINT;
import static io.github.greymagic27.win_method.types.WinUser.VK_PRIOR;
import static io.github.greymagic27.win_method.types.WinUser.VK_Q;
import static io.github.greymagic27.win_method.types.WinUser.VK_R;
import static io.github.greymagic27.win_method.types.WinUser.VK_RBUTTON;
import static io.github.greymagic27.win_method.types.WinUser.VK_RETURN;
import static io.github.greymagic27.win_method.types.WinUser.VK_RIGHT;
import static io.github.greymagic27.win_method.types.WinUser.VK_S;
import static io.github.greymagic27.win_method.types.WinUser.VK_SELECT;
import static io.github.greymagic27.win_method.types.WinUser.VK_SEPARATOR;
import static io.github.greymagic27.win_method.types.WinUser.VK_SHIFT;
import static io.github.greymagic27.win_method.types.WinUser.VK_SNAPSHOT;
import static io.github.greymagic27.win_method.types.WinUser.VK_SPACE;
import static io.github.greymagic27.win_method.types.WinUser.VK_SUBTRACT;
import static io.github.greymagic27.win_method.types.WinUser.VK_T;
import static io.github.greymagic27.win_method.types.WinUser.VK_TAB;
import static io.github.greymagic27.win_method.types.WinUser.VK_U;
import static io.github.greymagic27.win_method.types.WinUser.VK_UP;
import static io.github.greymagic27.win_method.types.WinUser.VK_V;
import static io.github.greymagic27.win_method.types.WinUser.VK_W;
import static io.github.greymagic27.win_method.types.WinUser.VK_X;
import static io.github.greymagic27.win_method.types.WinUser.VK_Y;
import static io.github.greymagic27.win_method.types.WinUser.VK_Z;
import static io.github.greymagic27.win_method.types.WinUser.WM_APP;
import static io.github.greymagic27.win_method.types.WinUser.WM_CANCELMODE;
import static io.github.greymagic27.win_method.types.WinUser.WM_CHAR;
import static io.github.greymagic27.win_method.types.WinUser.WM_CLOSE;
import static io.github.greymagic27.win_method.types.WinUser.WM_COMMAND;
import static io.github.greymagic27.win_method.types.WinUser.WM_CREATE;
import static io.github.greymagic27.win_method.types.WinUser.WM_DESTROY;
import static io.github.greymagic27.win_method.types.WinUser.WM_KEYDOWN;
import static io.github.greymagic27.win_method.types.WinUser.WM_MOUSEWHEEL;
import static io.github.greymagic27.win_method.types.WinUser.WM_PAINT;
import static io.github.greymagic27.win_method.types.WinUser.WM_SETFONT;
import static io.github.greymagic27.win_method.types.WinUser.WM_SIZE;
import static io.github.greymagic27.win_method.types.WinUser.WS_BORDER;
import static io.github.greymagic27.win_method.types.WinUser.WS_CAPTION;
import static io.github.greymagic27.win_method.types.WinUser.WS_CHILD;
import static io.github.greymagic27.win_method.types.WinUser.WS_DISABLED;
import static io.github.greymagic27.win_method.types.WinUser.WS_HSCROLL;
import static io.github.greymagic27.win_method.types.WinUser.WS_MAXIMIZE;
import static io.github.greymagic27.win_method.types.WinUser.WS_MINIMIZE;
import static io.github.greymagic27.win_method.types.WinUser.WS_OVERLAPPED;
import static io.github.greymagic27.win_method.types.WinUser.WS_OVERLAPPEDWINDOW;
import static io.github.greymagic27.win_method.types.WinUser.WS_POPUP;
import static io.github.greymagic27.win_method.types.WinUser.WS_SYSMENU;
import static io.github.greymagic27.win_method.types.WinUser.WS_VISIBLE;
import static io.github.greymagic27.win_method.types.WinUser.WS_VSCROLL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        assertEquals(0x0001, WM_CREATE);
        assertEquals(0x0030, WM_SETFONT);
        assertEquals(0x8000, WM_APP);
        assertEquals(0x001F, WM_CANCELMODE);
        assertEquals(0x0102, WM_CHAR);
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
        assertEquals(0x0004, ES_MULTILINE.intValue());
        assertEquals(0x0040, ES_AUTOVSCROLL.intValue());
        assertEquals(0x1000, ES_WANTRETURN.intValue());
        assertEquals(0x0080, ES_AUTOHSCROLL.intValue());
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
        assertEquals(0x30, VK_0);
        assertEquals(0x31, VK_1);
        assertEquals(0x32, VK_2);
        assertEquals(0x33, VK_3);
        assertEquals(0x34, VK_4);
        assertEquals(0x35, VK_5);
        assertEquals(0x36, VK_6);
        assertEquals(0x37, VK_7);
        assertEquals(0x38, VK_8);
        assertEquals(0x39, VK_9);
        assertEquals(0x41, VK_A);
        assertEquals(0x42, VK_B);
        assertEquals(0x43, VK_C);
        assertEquals(0x44, VK_D);
        assertEquals(0x45, VK_E);
        assertEquals(0x46, VK_F);
        assertEquals(0x47, VK_G);
        assertEquals(0x48, VK_H);
        assertEquals(0x49, VK_I);
        assertEquals(0x4A, VK_J);
        assertEquals(0x4B, VK_K);
        assertEquals(0x4C, VK_L);
        assertEquals(0x4D, VK_M);
        assertEquals(0x4E, VK_N);
        assertEquals(0x4F, VK_O);
        assertEquals(0x50, VK_P);
        assertEquals(0x51, VK_Q);
        assertEquals(0x52, VK_R);
        assertEquals(0x53, VK_S);
        assertEquals(0x54, VK_T);
        assertEquals(0x55, VK_U);
        assertEquals(0x56, VK_V);
        assertEquals(0x57, VK_W);
        assertEquals(0x58, VK_X);
        assertEquals(0x59, VK_Y);
        assertEquals(0x5A, VK_Z);
    }

    @Test
    void testMbValues() {
        assertEquals(0x00000000, MB_OK);
        assertEquals(0x00000004, MB_YESNO);
        assertEquals(0x00000010L, MB_ERRORICON);
        assertEquals(0x00000030, MB_ICONWARNING);
    }

    @Test
    void testCsValues() {
        assertEquals(0x0002, CS_HREDRAW);
        assertEquals(0x0001, CS_VREDRAW);
    }

    @Test
    void testLbValues() {
        assertEquals(0x0001, LBS_NOTIFY.intValue());
        assertEquals(0x0040, LBS_HASSTRINGS.intValue());
        assertEquals(2, LBN_DBLCICK);
        assertEquals(0x0184, LB_RESETCONTENT);
        assertEquals(0x0180, LB_ADDSTRING);
        assertEquals(0x0188, LB_GETCURSEL);
    }

    @Test
    void testLrValues() {
        assertEquals(0x00008000, LR_SHARED);
        assertEquals(0x00000010, LR_LOADFROMFILE);
    }

    @Test
    void testImageValues() {
        assertEquals(0, IMAGE_BITMAP);
        assertEquals(1, IMAGE_ICON);
        assertEquals(2, IMAGE_CURSOR);
    }

    @Test
    void testIdValues() {
        assertEquals(1, IDOK);
        assertEquals(2, IDCANCEL);
        assertEquals(6, IDYES);
        assertEquals(7, IDNO);
    }

    @Test
    void testEmValues() {
        assertEquals(0x00B1, EM_SETSEL);
        assertEquals(0x00C2, EM_REPLACESEL);
        assertEquals(0x00C9, EM_LINEFROMCHAR);
        assertEquals(0x00BB, EM_LINEINDEX);
        assertEquals(0x00C1, EM_LINELENGTH);
        assertEquals(0x00C4, EM_GETLINE);
        assertEquals(0x00B0, EM_GETSEL);
    }

    @Test
    void testTpmValues() {
        assertEquals(0x0004, TPM_CENTERALIGN);
        assertEquals(0x0000, TPM_LEFTALIGN);
        assertEquals(0x0008, TPM_RIGHTALIGN);
        assertEquals(0x0020, TPM_BOTTOMALIGN);
        assertEquals(0x0000, TPM_TOPALIGN);
        assertEquals(0x0010, TPM_VCENTERALIGN);
        assertEquals(0x0080, TPM_NONOTIFY);
        assertEquals(0x0100, TPM_RETURNCMD);
        assertEquals(0x0000, TPM_LEFTBUTTON);
        assertEquals(0x0002, TPM_RIGHTBUTTON);
    }

    @Test
    void testMiscValues() {
        assertEquals(0x004, SWP_NOZORDER);
        assertEquals(0x00000080, BS_BITMAP.intValue());
        assertEquals(-16, GWL_STYLE);
        assertEquals(0x00F7, BM_SETIMAGE);
    }

    @Test
    void testWndClassEx() {
        WinUser.WNDCLASSEXW wndClass = new WinUser.WNDCLASSEXW();
        wndClass.cbSize = new UINT(wndClass.size());
        wndClass.style = new UINT(CS_HREDRAW | CS_VREDRAW);
        wndClass.lpszClassName = new LPCWSTR("TestWindow");
        assertEquals(wndClass.size(), wndClass.cbSize.intValue());
        assertEquals(CS_HREDRAW | CS_VREDRAW, wndClass.style.intValue());
        assertEquals("TestWindow", wndClass.lpszClassName.getWideString(0));
    }

    @Test
    void testMsg() {
        WinUser.MSG msg = new WinUser.MSG();
        msg.message = new UINT(WM_KEYDOWN);
        msg.wParam = new WPARAM(123);
        msg.lParam = new LPARAM(456);
        assertEquals(WM_KEYDOWN, msg.message.intValue());
        assertEquals(123, msg.wParam.longValue());
        assertEquals(456, msg.lParam.longValue());
    }

    @Test
    void testWndProc() {
        HWND hwnd = new HWND(0x12345678);
        UINT msg = new UINT(WM_DESTROY);
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

    @Test
    void testPaintStruct() {
        WinUser.PAINTSTRUCT ps = new WinUser.PAINTSTRUCT();
        ps.hdc = new HDC(MemorySegment.ofAddress(0xDEADBEEFL));
        ps.fErase = new BOOL(1);
        ps.rcPaint = new WinDef.RECT();
        ps.rcPaint.left = new LONG(1);
        ps.rcPaint.top = new LONG(2);
        ps.rcPaint.right = new LONG(3);
        ps.rcPaint.bottom = new LONG(4);
        ps.fRestore = new BOOL(0);
        ps.fIncUpdate = new BOOL(0);
        for (int i = 0; i < ps.rgbReserved.length; i++) ps.rgbReserved[i] = new BYTE((byte) i);
        ps.write();
        ps.read();
        assertEquals(0xDEADBEEFL, ps.hdc.segment.address());
        assertTrue(ps.fErase.booleanValue());
        assertEquals(1, ps.rcPaint.left.intValue());
        assertEquals(2, ps.rcPaint.top.intValue());
        assertEquals(3, ps.rcPaint.right.intValue());
        assertEquals(4, ps.rcPaint.bottom.intValue());
        assertFalse(ps.fRestore.booleanValue());
        assertFalse(ps.fIncUpdate.booleanValue());
        for (int i = 0; i < ps.rgbReserved.length; i++) assertEquals((byte) i, ps.rgbReserved[i].byteValue());
    }
}