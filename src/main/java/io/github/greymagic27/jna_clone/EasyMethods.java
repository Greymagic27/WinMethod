package io.github.greymagic27.jna_clone;

import io.github.greymagic27.jna_clone.WinDef.HCURSOR;
import io.github.greymagic27.jna_clone.WinDef.HINSTANCE;
import io.github.greymagic27.jna_clone.WinDef.HWND;
import io.github.greymagic27.jna_clone.WinDef.LRESULT;
import io.github.greymagic27.jna_clone.platform.Kernel32;
import io.github.greymagic27.jna_clone.platform.User32;
import io.github.greymagic27.jna_clone.platform.WinDef;
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
            if (uMsg == WinUser.WM_SYSCOMMAND) {
                System.out.println("WM_SYSCOMMAND: " + Long.toHexString(wParam.intValue()));
            }
            if (uMsg == WinUser.WM_NCHITTEST) {
                return new LRESULT(2);
            }
            if (uMsg == 0x00A1) {
                System.out.println("L Button Down");
            }
            return result;
        };
        WinUser.WNDCLASSEXW wc = new WinUser.WNDCLASSEXW();
        wc.cbSize = wc.size();
        wc.style = WinUser.CS_HREDRAW | WinUser.CS_VREDRAW;
        wc.lpfnWndProc = wndProc;
        wc.hInstance = hInstance;
        wc.lpszClassName = "WindowClass";
        wc.hCursor = new HCURSOR(User32.INSTANCE.LoadImageW(null, WinUser.IDC_ARROW, WinUser.IMAGE_CURSOR, 0, 0, WinUser.LR_DEFAULTSIZE | WinUser.LR_SHARED).getPointer());
        User32.INSTANCE.RegisterClassExW(wc);
        HWND hwnd = User32.INSTANCE.CreateWindowExW(0, "WindowClass", title, WinUser.WS_OVERLAPPEDWINDOW, WinUser.CW_USEDEFAULT, WinUser.CW_USEDEFAULT, width, height, null, null, hInstance, null);
        System.out.printf("Style: 0x%X%n", User32.INSTANCE.GetWindowLongW(hwnd, WinUser.GWL_STYLE).intValue());
        WinDef.RECT rect = new WinDef.RECT();
        System.out.println("Window rect: " + User32.INSTANCE.GetWindowRect(hwnd, rect));
        System.out.println("Left: " + rect.left + " Top: " + rect.top + " Right: " + rect.right + " Bottom: " + rect.bottom);
        User32.INSTANCE.ShowWindow(hwnd, WinUser.SW_SHOW);
        User32.INSTANCE.UpdateWindow(hwnd);
        WinUser.MSG msg = new WinUser.MSG();
        while (User32.INSTANCE.GetMessageW(msg, null, 0, 0).booleanValue()) {
            User32.INSTANCE.TranslateMessage(msg);
            User32.INSTANCE.DispatchMessageW(msg);
        }
    }
}