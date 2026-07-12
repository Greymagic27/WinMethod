package io.github.greymagic27.jna_clone;

import io.github.greymagic27.jna_clone.WinDef.HINSTANCE;
import io.github.greymagic27.jna_clone.WinDef.HWND;
import io.github.greymagic27.jna_clone.WinDef.LRESULT;
import io.github.greymagic27.jna_clone.platform.Kernel32;
import io.github.greymagic27.jna_clone.platform.User32;
import io.github.greymagic27.jna_clone.platform.WinUser;

@SuppressWarnings("unused")
public class EasyMethods {

    public static void createWindow(String title, int width, int height) {
        HINSTANCE hInstance = Kernel32.INSTANCE.GetModuleHandleW(null);
        WinUser.WndProc wndProc = (hWnd, uMsg, wParam, lParam) -> {
            LRESULT result = User32.INSTANCE.DefWindowProcW(hWnd, uMsg, wParam, lParam);
            if (uMsg == WinUser.WM_DESTROY) {
                User32.INSTANCE.PostQuitMessage(0);
                return new LRESULT(0);
            }
            return result;
        };
        WinUser.WNDCLASSEXW wc = new WinUser.WNDCLASSEXW();
        wc.cbSize = wc.size();
        wc.lpfnWndProc = wndProc;
        wc.hInstance = hInstance;
        wc.lpszClassName = "WindowClass";
        User32.INSTANCE.RegisterClassExW(wc);
        HWND hwnd = User32.INSTANCE.CreateWindowExW(0, "WindowClass", title, WinUser.WS_OVERLAPPEDWINDOW, 0, 0, width, height, null, null, hInstance, null);
        User32.INSTANCE.ShowWindow(hwnd, WinUser.SW_SHOW);
        User32.INSTANCE.UpdateWindow(hwnd);
        WinUser.MSG msg = new WinUser.MSG();
        while (User32.INSTANCE.GetMessageW(msg, null, 0, 0) != 0) {
            User32.INSTANCE.TranslateMessage(msg);
            User32.INSTANCE.DispatchMessageW(msg);
        }
    }
}