package io.github.greymagic27.jna_clone.platform;

import io.github.greymagic27.jna_clone.Callback;
import io.github.greymagic27.jna_clone.Structure;
import io.github.greymagic27.jna_clone.WinDef.HBRUSH;
import io.github.greymagic27.jna_clone.WinDef.HCURSOR;
import io.github.greymagic27.jna_clone.WinDef.HICON;
import io.github.greymagic27.jna_clone.WinDef.HINSTANCE;
import io.github.greymagic27.jna_clone.WinDef.HWND;
import io.github.greymagic27.jna_clone.WinDef.LPARAM;
import io.github.greymagic27.jna_clone.WinDef.LRESULT;
import io.github.greymagic27.jna_clone.WinDef.WPARAM;

/**
 * Values defined in WinUser.h
 */
public interface WinUser {

    /**
     * The window is an overlapped window. An overlapped window has a title bar and a border. Same as the WS_TILED style
     */
    int WS_OVERLAPPED = 0x00000000;
    /**
     * The window is an overlapped window. Same as the WS_TILEDWINDOW style
     */
    int WS_OVERLAPPEDWINDOW = 0x00CF0000;
    /**
     * The window is a pop-up window. This style cannot be used with the {@link #WS_CHILD} style
     */
    int WS_POPUP = 0x80000000;
    /**
     * The window is a child window. A window with this style cannot have a menu bar. This style cannot be used with the {@link #WS_POPUP} style
     */
    int WS_CHILD = 0x40000000;
    /**
     * The window is initially minimized. Same as the WS_ICONIC style
     */
    int WS_MINIMIZE = 0x20000000;
    /**
     * The window is initially visible.
     * This style can be turned on and off by using the ShowWindow or SetWindowPos function
     */
    int WS_VISIBLE = 0x10000000;
    /**
     * The window is initially disabled. A disabled window cannot receive input from the user. To change this after a window has been created, use the EnableWindow function
     */
    int WS_DISABLED = 0x08000000;
    /**
     * The window is initially maximized
     */
    int WS_MAXIMIZE = 0x01000000;
    /**
     * The window has a title bar (includes the {@link #WS_BORDER} style)
     */
    int WS_CAPTION = 0x00C00000;
    /**
     * The window has a thin-line border
     */
    int WS_BORDER = 0x00800000;
    /**
     * The window has a vertical scroll bar
     */
    int WS_VSCROLL = 0x00200000;
    /**
     * The window has a horizontal scroll bar
     */
    int WS_HSCROLL = 0x00100000;
    /**
     * The window has a window menu on its title bar. The {@link #WS_CAPTION} style must also be specified
     */
    int WS_SYSMENU = 0x00080000;

    /**
     * The message sent when a window is being destroyed
     */
    int WM_DESTROY = 0x0002;

    /**
     * Activates the window and displays it in its current size and position
     */
    int SW_SHOW = 5;
    /**
     * Hides the window and activates another window
     */
    int SW_HIDE = 0;

    /**
     * The width of the screen of the primary display monitor, in pixels
     */
    int SM_CXSCREEN = 0;
    /**
     * The height of the screen of the primary display monitor, in pixels
     */
    int SM_CYSCREEN = 1;

    /**
     * Retains the current Z order
     */
    int SWP_NOZORDER = 0x0004;

    /**
     * A callback function which is defined in the application
     */
    interface WndProc extends Callback {
        /**
         * A callback function that processes messages sent to a window
         *
         * @param hWnd   A handle to the window
         * @param uMsg   The message
         * @param wParam Additional message information
         * @param lParam Additional message information
         * @return Return value is the result of the message processing and depends on the message sent
         */
        LRESULT callback(HWND hWnd, int uMsg, WPARAM wParam, LPARAM lParam);
    }

    /**
     * Contains window class information
     */
    @SuppressWarnings("unused")
    @Structure.FieldOrder({"cbSize", "style", "lpfnWndProc", "cbClsExtra", "cbWndExtra", "hInstance", "hIcon", "hCursor", "hbrBackground", "lpszMenuName", "lpszClassName", "hIconSm"})
    class WNDCLASSEXW extends Structure {
        /**
         * The size, in bytes, of this structure
         */
        public int cbSize;
        /**
         * The class style(s). This can be a combination of <a href="https://learn.microsoft.com/en-us/windows/win32/winmsg/window-class-styles"></a>
         */
        public int style;
        /**
         * A pointer to the window procedure
         */
        public WndProc lpfnWndProc;
        /**
         * The number of extra bytes to allocate following the window-class structure. This initialises to 0
         */
        public int cbClsExtra;
        /**
         * The number of extra bytes to allocate following the window instance. This initialises to 0
         */
        public int cbWndExtra;
        /**
         * A {@link io.github.greymagic27.jna_clone.WinNT.HANDLE} to the instance that contains the window procedure for the class
         */
        public HINSTANCE hInstance;
        /**
         * A {@link io.github.greymagic27.jna_clone.WinNT.HANDLE} to the class icon. This must be a handle to an icon resource. If NULL, the default icon is provided
         */
        public HICON hIcon;
        /**
         * A {@link io.github.greymagic27.jna_clone.WinNT.HANDLE} to the class cursor. This must be a handle a cursor resource. if NULL, the application must explicitly set the cursor shape whenever the mouse moves into the application's window
         */
        public HCURSOR hCursor;
        /**
         * A {@link io.github.greymagic27.jna_clone.WinNT.HANDLE} to the class background brush. This member can be a handle to a brush or a colour value, however this must be one of the standard system colours. If a colour value is given, it must be converted to an <a href="https://learn.microsoft.com/en-us/windows/win32/api/winuser/ns-winuser-wndclassexw">HBRUSH type</a>
         */
        public HBRUSH hbrBackground;
        /**
         * Pointer to a null-terminated character string that specifies the resource name of the clas menu. If an integer is used, it must be used via the {@link io.github.greymagic27.jna_clone.Pointer#MAKEINTRESOURCEW(int)} function
         */
        public String lpszMenuName;
        /**
         * A pointer to a null-terminated character string or is an atom. If this is an atom, it must be a class atom created by a previous call to the {@link User32#RegisterClassExW(WNDCLASSEXW)} function
         */
        public String lpszClassName;
        /**
         * A {@link io.github.greymagic27.jna_clone.WinNT.HANDLE} to a small icon that is associated with the window class. If NULL, the system searches the icon resource specified by the {@link HICON} member for an icon of appropriate size
         */
        public HICON hIconSm;
    }

    /**
     * Contains message information from a thread's message queue
     */
    @SuppressWarnings("unused")
    @Structure.FieldOrder({"hwnd", "message", "wParam", "lParam", "time", "pt"})
    class MSG extends Structure {
        /**
         * A {@link io.github.greymagic27.jna_clone.WinNT.HANDLE} to the window whose window procedure receives the message. This is NULL when the message is a thread message
         */
        public HWND hwnd;
        /**
         * The message identifier
         */
        public int message;
        /**
         * Additional information about the message
         */
        public WPARAM wParam;
        /**
         * Additional information about the message
         */
        public LPARAM lParam;
        /**
         * The time at which the message was posted
         */
        public int time;
        /**
         * The cursor positon, in screen coordinates, when the message was posted
         */
        public WinDef.POINT pt;
    }
}
