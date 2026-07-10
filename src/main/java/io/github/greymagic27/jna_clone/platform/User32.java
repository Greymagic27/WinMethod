package io.github.greymagic27.jna_clone.platform;

import io.github.greymagic27.jna_clone.Library;
import io.github.greymagic27.jna_clone.Pointer;
import io.github.greymagic27.jna_clone.WinDef.ATOM;
import io.github.greymagic27.jna_clone.WinDef.BOOL;
import io.github.greymagic27.jna_clone.WinDef.HCURSOR;
import io.github.greymagic27.jna_clone.WinDef.HINSTANCE;
import io.github.greymagic27.jna_clone.WinDef.HMENU;
import io.github.greymagic27.jna_clone.WinDef.HWND;
import io.github.greymagic27.jna_clone.WinDef.LPARAM;
import io.github.greymagic27.jna_clone.WinDef.LPVOID;
import io.github.greymagic27.jna_clone.WinDef.LRESULT;
import io.github.greymagic27.jna_clone.WinDef.WPARAM;

public interface User32 extends Library {
    User32 INSTANCE = Library.load(User32.class);

    void PostQuitMessage(int nExitCode);

    LRESULT DefWindowProcW(HWND hWnd, int Msg, WPARAM wParam, LPARAM lParam);

    BOOL ShowWindow(HWND hWnd, int nCmdShow);

    BOOL UpdateWindow(HWND hWnd);

    BOOL DestroyWindow(HWND hWnd);

    BOOL TranslateMessage(WinUser.MSG msg);

    BOOL DispatchMessageW(WinUser.MSG msg);

    BOOL GetMessageW(WinUser.MSG lpMsg, HWND hWnd, int wMsgFilterMin, int wMsgFilterMax);

    /**
     @deprecated This function has been superseded by the LoadImage function (with LR_DEFAULTSIZE and LR_SHARED flags set).
     <a href="https://learn.microsoft.com/en-us/windows/win32/api/winuser/nf-winuser-loadimagew">...</a>
     */
    HCURSOR LoadCursorW(HINSTANCE hInstance, Pointer lpCursorName);

    ATOM RegisterClassExW(WinUser.WNDCLASSEXW pointer);

    HWND CreateWindowExW(int dwExStyle, String lpClassName, String lpWindowName, int dwStyle, int X, int Y, int nWidth, int nHeight, HWND hWndParent, HMENU hMenu, HINSTANCE hInstance, LPVOID lpParam);
}
