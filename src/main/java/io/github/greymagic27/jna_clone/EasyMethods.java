package io.github.greymagic27.jna_clone;

import io.github.greymagic27.jna_clone.WinDef.HINSTANCE;
import io.github.greymagic27.jna_clone.WinDef.HWND;
import io.github.greymagic27.jna_clone.WinDef.LRESULT;
import io.github.greymagic27.jna_clone.platform.Kernel32;
import io.github.greymagic27.jna_clone.platform.User32;
import io.github.greymagic27.jna_clone.platform.WinUser;
import java.util.Objects;

@SuppressWarnings("unused")
public class EasyMethods {

    private static HWND currentWindow;
    private static WindowPosition windowPosition;
    private static int currentWidth;
    private static int currentHeight;

    /**
     * Creates a basic window
     * @param title Title of the window
     * @param width Width of the window
     * @param height Height of the window
     */
    public static void createWindow(String title, int width, int height) {
        currentWidth = width;
        currentHeight = height;
        HINSTANCE hInstance = Kernel32.INSTANCE.GetModuleHandleW(null);
        WinUser.WndProc wndProc = (hWnd, uMsg, wParam, lParam) -> {
            LRESULT result = User32.INSTANCE.DefWindowProcW(hWnd, uMsg, wParam, lParam);
            if (uMsg == WinUser.WM_DESTROY) {
                currentWindow = null;
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
        currentWindow = User32.INSTANCE.CreateWindowExW(0, "WindowClass", title, WinUser.WS_OVERLAPPEDWINDOW, 0, 0, width, height, null, null, hInstance, null);
        User32.INSTANCE.ShowWindow(currentWindow, WinUser.SW_SHOW);
        User32.INSTANCE.UpdateWindow(currentWindow);
    }

    /**
     * Called to start the program. Without this, the window will open and close immediately
     */
    public static void start() {
        setWindowPosition(Objects.requireNonNullElse(windowPosition, WindowPosition.CENTER));
        WinUser.MSG msg = new WinUser.MSG();
        while (User32.INSTANCE.GetMessageW(msg, null, 0, 0) != 0) {
            User32.INSTANCE.TranslateMessage(msg);
            User32.INSTANCE.DispatchMessageW(msg);
        }
    }

    /**
     * Sets the window position on the screen
     * @param position This defaults to CENTER if not specified
     */
    public static void setWindowPosition(WindowPosition position) {
        if (currentWindow == null) throw new IllegalStateException("No window has been created");
        windowPosition = position;
        int screenWidth = User32.INSTANCE.GetSystemMetrics(WinUser.SM_CXSCREEN);
        int screenHeight = User32.INSTANCE.GetSystemMetrics(WinUser.SM_CYSCREEN);
        int x;
        int y;
        switch (position) {
            case CENTER -> {
                x = (screenWidth - currentWidth) / 2;
                y = (screenHeight - currentHeight) / 2;
            }
            case TOP -> {
                x = (screenWidth - currentWidth) / 2;
                y = 0;
            }
            case BOTTOM -> {
                x = (screenWidth - currentWidth) / 2;
                y = screenHeight - currentHeight;
            }
            case LEFT -> {
                x = 0;
                y = (screenHeight - currentHeight) / 2;
            }
            case RIGHT -> {
                x = screenWidth - currentWidth;
                y = (screenHeight - currentHeight) / 2;
            }
            case TOP_LEFT -> {
                x = 0;
                y = 0;
            }
            case TOP_RIGHT -> {
                x = screenWidth - currentWidth;
                y = 0;
            }
            case BOTTOM_LEFT -> {
                x = 0;
                y = screenHeight - currentHeight;
            }
            case BOTTOM_RIGHT -> {
                x = screenWidth - currentWidth;
                y = screenHeight - currentHeight;
            }
            default -> throw new IllegalStateException("Unexpected value: " + position);
        }
        User32.INSTANCE.SetWindowPos(currentWindow, null, x, y, currentWidth, currentHeight, WinUser.SWP_NOZORDER);
    }

    /**
     * Returns the current window HWND
     */
    public static HWND getCurrentWindow() {
        return currentWindow;
    }

    /**
     * Specifies where you would like the window to appear on the screen
     */
    public enum WindowPosition {
        CENTER, TOP, BOTTOM, LEFT, RIGHT, TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT
    }
}