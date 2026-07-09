package io.github.greymagic27.jna_clone.EasyMethods;

import io.github.greymagic27.jna_clone.WinDef.HINSTANCE;
import io.github.greymagic27.jna_clone.WinDef.HWND;
import io.github.greymagic27.jna_clone.WinDef.LRESULT;
import io.github.greymagic27.jna_clone.platform.Kernel32;
import io.github.greymagic27.jna_clone.platform.User32;
import io.github.greymagic27.jna_clone.platform.WinUser;

public class Window {

    private static final String className = "WindowClass";

    static void main() {
        HINSTANCE hInstance = Kernel32.INSTANCE.GetModuleHandleW(null);
        WinUser.WndProc wndProc = (hWnd, uMsg, wParam, lParam) -> {
            if (uMsg == WinUser.WM_DESTROY) {
                User32.INSTANCE.PostQuitMessage(0);
                return new LRESULT(0);
            }
            return User32.INSTANCE.DefWindowProcW(hWnd, uMsg, wParam, lParam);
        };
        WinUser.WNDCLASSEXW wc = new WinUser.WNDCLASSEXW();
        wc.cbSize = wc.size();
        wc.style = WinUser.CS_HREDRAW | WinUser.CS_VREDRAW;
        wc.lpfnWndProc = wndProc;
        wc.hInstance = hInstance;
        wc.lpszClassName = className;
        wc.hCursor = User32.INSTANCE.LoadCursorW(null, WinUser.IDC_ARROW);
        User32.INSTANCE.RegisterClassExW(wc);
        HWND hwnd = User32.INSTANCE.CreateWindowExW(0, className, "Test Window", WinUser.WS_OVERLAPPEDWINDOW, WinUser.CW_USEDEFAULT, WinUser.CW_USEDEFAULT, 800, 600, null, null, hInstance, null);
        User32.INSTANCE.ShowWindow(hwnd, WinUser.SW_SHOW);
        User32.INSTANCE.UpdateWindow(hwnd);
        WinUser.MSG msg = new WinUser.MSG();
        while (User32.INSTANCE.GetMessageW(msg, null, 0, 0).booleanValue()) {
            User32.INSTANCE.TranslateMessage(msg);
            User32.INSTANCE.DispatchMessage(msg);
        }
    }
}
