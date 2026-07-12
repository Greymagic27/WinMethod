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
import io.github.greymagic27.jna_clone.WinNT.HANDLE;

public interface User32 extends Library {
    User32 INSTANCE = Library.load(User32.class);

    ATOM RegisterClassExW(WinUser.WNDCLASSEXW pointer);

    BOOL ShowWindow(HWND hWnd, int nCmdShow);

    BOOL UpdateWindow(HWND hWnd);

    BOOL DestroyWindow(HWND hWnd);

    BOOL TranslateMessage(WinUser.MSG msg);

    BOOL DispatchMessageW(WinUser.MSG msg);

    int GetMessageW(WinUser.MSG lpMsg, HWND hWnd, int wMsgFilterMin, int wMsgFilterMax);

    int GetSystemMetrics(int nIndex); // TODO: Write test

    LRESULT DefWindowProcW(HWND hWnd, int Msg, WPARAM wParam, LPARAM lParam);

    HANDLE LoadImageW(HINSTANCE hInst, Pointer name, int type, int cx, int cy, int fuLoad);

    /**
     * @deprecated This function has been superseded by {@link #LoadImageW(HINSTANCE, Pointer, int, int, int, int)}
     */
    @Deprecated
    HCURSOR LoadCursorW(HINSTANCE hInstance, Pointer lpCursorName);

    HWND CreateWindowExW(int dwExStyle, String lpClassName, String lpWindowName, int dwStyle, int X, int Y, int nWidth, int nHeight, HWND hWndParent, HMENU hMenu, HINSTANCE hInstance, LPVOID lpParam);

    void PostQuitMessage(int nExitCode);
}
