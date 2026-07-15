package io.github.greymagic27.win_method.methods;

import io.github.greymagic27.win_method.WinDef.HINSTANCE;
import io.github.greymagic27.win_method.WinDef.HWND;
import io.github.greymagic27.win_method.platform.Kernel32;
import io.github.greymagic27.win_method.platform.User32;
import io.github.greymagic27.win_method.platform.WinUser;
import java.util.Objects;

/**
 * Helpers to create a window without rewriting lines of code
 */
public class Window {
    private static HWND currentWindow;
    private static WindowPosition windowPosition;
    private static int currentWidth;
    private static int currentHeight;

    /**
     * Creates a basic window
     *
     * @param wndproc Custom wndproc. If you do not want to use a custom wndproc, use {@link WinUser.Wndproc#defaultWndProc()}
     * @param title   Title of the window
     * @param width   Width of the window
     * @param height  Height of the window
     */
    public static void createWindow(WinUser.Wndproc wndproc, String title, int width, int height) {
        currentWidth = width;
        currentHeight = height;
        Objects.requireNonNull(wndproc);
        String className = "WindowClass_" + System.nanoTime();
        HINSTANCE hInstance = Kernel32.INSTANCE.GetModuleHandleW(null);
        WinUser.WNDCLASSEXW wc = new WinUser.WNDCLASSEXW();
        wc.cbSize = wc.size();
        wc.lpfnWndProc = wndproc;
        wc.hInstance = hInstance;
        wc.lpszClassName = className;
        User32.INSTANCE.RegisterClassExW(wc);
        currentWindow = User32.INSTANCE.CreateWindowExW(0, className, title, WinUser.WS_OVERLAPPEDWINDOW, 0, 0, width, height, null, null, hInstance, null);
        User32.INSTANCE.ShowWindow(currentWindow, WinUser.SW_SHOW);
        User32.INSTANCE.UpdateWindow(currentWindow);
    }

    /**
     * Called to start the program. Without this, the window will open and close immediately
     */
    public static void start() {
        setWindowPosition(Objects.requireNonNullElse(windowPosition, WindowPosition.CENTER));
        WinUser.MSG msg = new WinUser.MSG();
        while (User32.INSTANCE.GetMessageW(msg, null, 0, 0).booleanValue()) {
            User32.INSTANCE.TranslateMessage(msg);
            User32.INSTANCE.DispatchMessageW(msg);
        }
    }

    /**
     * Sets the window position on the screen
     *
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
     * Returns the current window {@link HWND}
     */
    public static HWND getCurrentWindow() {
        return currentWindow;
    }

    /**
     * Resets window attributes to 0 or null
     */
    public static void reset() {
        currentWindow = null;
        currentHeight = 0;
        currentWidth = 0;
    }
}