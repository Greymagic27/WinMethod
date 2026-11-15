package io.github.greymagic27.win_method.methods;

import io.github.greymagic27.win_method.WinDef.HDC;
import io.github.greymagic27.win_method.WinDef.HWND;
import io.github.greymagic27.win_method.platform.User32;
import io.github.greymagic27.win_method.types.WinDef;
import io.github.greymagic27.win_method.types.WinUser;

/// Helpers for painting windows using {@link io.github.greymagic27.win_method.platform.User32#BeginPaint(HWND, WinUser.PAINTSTRUCT)}
public class Paint {

    /// Paints the specified window using the provided callback
    ///
    /// Automatically calls BeginPaint, retrieves the client dimensions, and calls EndPaint.
    ///
    /// @param hwnd     The handle to the window to paint
    /// @param callback The callback used to paint the window
    public static void paint(HWND hwnd, PaintCallback callback) {
        WinUser.PAINTSTRUCT ps = new WinUser.PAINTSTRUCT();
        HDC hdc = User32.INSTANCE.BeginPaint(hwnd, ps);
        try {
            WinDef.RECT rect = new WinDef.RECT();
            if (User32.INSTANCE.GetClientRect(hwnd, rect).booleanValue()) {
                int width = rect.right.intValue() - rect.left.intValue();
                int height = rect.bottom.intValue() - rect.top.intValue();
                if (width > 0 && height > 0 && !hdc.isNull()) callback.paint(hdc, width, height);
            }
        } finally {
            User32.INSTANCE.EndPaint(hwnd, ps);
        }
    }

    @FunctionalInterface
    public interface PaintCallback {
        /// Paints the window using the specified device context and dimensions
        ///
        /// @param hdc    The device context used for painting
        /// @param width  The client width
        /// @param height The client height
        void paint(HDC hdc, int width, int height);
    }
}
