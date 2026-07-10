package io.github.greymagic27.jna_clone.platform;

import io.github.greymagic27.jna_clone.Callback;
import io.github.greymagic27.jna_clone.Pointer;
import io.github.greymagic27.jna_clone.Structure;
import io.github.greymagic27.jna_clone.WinDef.HBRUSH;
import io.github.greymagic27.jna_clone.WinDef.HCURSOR;
import io.github.greymagic27.jna_clone.WinDef.HICON;
import io.github.greymagic27.jna_clone.WinDef.HINSTANCE;
import io.github.greymagic27.jna_clone.WinDef.HWND;
import io.github.greymagic27.jna_clone.WinDef.LPARAM;
import io.github.greymagic27.jna_clone.WinDef.LRESULT;
import io.github.greymagic27.jna_clone.WinDef.WPARAM;

public interface WinUser {

    int WS_OVERLAPPED = 0x00000000;
    int WS_OVERLAPPEDWINDOW = 0x00CF0000;
    int WS_POPUP = 0x80000000;
    int WS_CHILD = 0x40000000;
    int WS_MINIMIZE = 0x20000000;
    int WS_VISIBLE = 0x10000000;
    int WS_DISABLED = 0x08000000;
    int WS_MAXIMIZE = 0x01000000;
    int WS_CAPTION = 0x00C00000;
    int WS_BORDER = 0x00800000;
    int WS_VSCROLL = 0x00200000;
    int WS_HSCROLL = 0x00100000;
    int WS_SYSMENU = 0x00080000;

    int WM_DESTROY = 0x0002;
    int WM_CLOSE = 0x0010;
    int WM_KEYDOWN = 0x0100;
    int WM_PAINT = 0x000F;
    int WM_SYSCOMMAND = 0x0112;
    int WM_NCHITTEST = 0x0084;
    int WM_LBUTTONDOWN = 0x0201;

    int CS_HREDRAW = 0x0002;
    int CS_VREDRAW = 0x0001;

    int CW_USEDEFAULT = 0x80000000;

    int SW_SHOW = 5;
    int SW_HIDE = 0;

    int LR_DEFAULTSIZE = 0x00000040;
    int LR_SHARED = 0x00008000;

    int GWL_STYLE = -16;

    int IMAGE_CURSOR = 2;

    Pointer IDC_ARROW = Pointer.MAKEINTRESOURCEW(32512);

    interface WndProc extends Callback {
        LRESULT callback(HWND hWnd, int uMsg, WPARAM wParam, LPARAM lParam);
    }

    @SuppressWarnings("unused")
    @Structure.FieldOrder({"cbSize", "style", "lpfnWndProc", "cbClsExtra", "cbWndExtra", "hInstance", "hIcon", "hCursor", "hbrBackground", "lpszMenuName", "lpszClassName", "hIconSm"})
    class WNDCLASSEXW extends Structure {
        public int cbSize;
        public int style;
        public WndProc lpfnWndProc;
        public int cbClsExtra;
        public int cbWndExtra;
        public HINSTANCE hInstance;
        public HICON hIcon;
        public HCURSOR hCursor;
        public HBRUSH hbrBackground;
        public String lpszMenuName;
        public String lpszClassName;
        public HICON hIconSm;
    }

    @SuppressWarnings("unused")
    @Structure.FieldOrder({"hwnd", "message", "wParam", "lParam", "time", "point", "lPrivate"})
    class MSG extends Structure {
        public HWND hwnd;
        public int message;
        public WPARAM wParam;
        public LPARAM lParam;
        public int time;
        public WinDef.POINT point;
        public int lPrivate;
    }
}
