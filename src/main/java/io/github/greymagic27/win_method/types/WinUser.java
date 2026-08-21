package io.github.greymagic27.win_method.types;

import io.github.greymagic27.win_method.Callback;
import io.github.greymagic27.win_method.IntSafe.DWORD;
import io.github.greymagic27.win_method.Pointer;
import io.github.greymagic27.win_method.Structure;
import io.github.greymagic27.win_method.WinDef.BOOL;
import io.github.greymagic27.win_method.WinDef.BYTE;
import io.github.greymagic27.win_method.WinDef.HBRUSH;
import io.github.greymagic27.win_method.WinDef.HCURSOR;
import io.github.greymagic27.win_method.WinDef.HDC;
import io.github.greymagic27.win_method.WinDef.HICON;
import io.github.greymagic27.win_method.WinDef.HINSTANCE;
import io.github.greymagic27.win_method.WinDef.HMENU;
import io.github.greymagic27.win_method.WinDef.HWND;
import io.github.greymagic27.win_method.WinDef.LPARAM;
import io.github.greymagic27.win_method.WinDef.LPVOID;
import io.github.greymagic27.win_method.WinDef.LRESULT;
import io.github.greymagic27.win_method.WinDef.UINT;
import io.github.greymagic27.win_method.WinDef.WPARAM;
import io.github.greymagic27.win_method.WinNT.LONG;
import io.github.greymagic27.win_method.WinNT.LPCWSTR;
import io.github.greymagic27.win_method.platform.User32;
import java.lang.foreign.MemorySegment;
import org.jspecify.annotations.NonNull;

/// Values defined in Winuser.h
public interface WinUser {

    /// The window is an overlapped window. An overlapped window has a title bar and a border. Same as the WS_TILED style
    int WS_OVERLAPPED = 0x00000000;
    /// The window is an overlapped window. Same as the WS_TILEDWINDOW style
    int WS_OVERLAPPEDWINDOW = 0x00CF0000;
    /// The window is a pop-up window. This style cannot be used with the {@link #WS_CHILD} style
    int WS_POPUP = 0x80000000;
    /// The window is a child window. A window with this style cannot have a menu bar. This style cannot be used with the {@link #WS_POPUP} style
    int WS_CHILD = 0x40000000;
    /// The window is initially minimised. Same as the WS_ICONIC style
    int WS_MINIMIZE = 0x20000000;
    /// The window is initially visible.
    /// This style can be turned on and off by using the ShowWindow or SetWindowPos function
    int WS_VISIBLE = 0x10000000;
    /// The window is initially disabled. A disabled window cannot receive input from the user. To change this after a window has been created, use the EnableWindow function
    int WS_DISABLED = 0x08000000;
    /// The window is initially maximised
    int WS_MAXIMIZE = 0x01000000;
    /// The window has a title bar (includes the {@link #WS_BORDER} style)
    int WS_CAPTION = 0x00C00000;
    /// The window has a thin-line border
    int WS_BORDER = 0x00800000;
    /// The window has a vertical scroll bar
    int WS_VSCROLL = 0x00200000;
    /// The window has a horizontal scroll bar
    int WS_HSCROLL = 0x00100000;
    /// The window has a window menu on its title bar. The {@link #WS_CAPTION} style must also be specified
    int WS_SYSMENU = 0x00080000;

    /// The message sent when a window is being destroyed
    int WM_DESTROY = 0x0002;
    /// Sent to a window after its size has changed
    int WM_SIZE = 0x0005;
    /// Sent when the user invokes a command item from a menu, when a control sends a notification message to its parent window or when an accelerator keystroke is translated
    int WM_COMMAND = 0x0111;
    /// Sent as a signal that a window or an application should terminate
    int WM_CLOSE = 0x0010;
    /// Sent when the system or an application makes a request to paint a portion of the application's window
    int WM_PAINT = 0x000F;
    /// Posted to the window with the keyboard focus when a nonsystem key is pressed. A nonsystem key is a key that is pressed when the ALT key is not pressed
    int WM_KEYDOWN = 0x0100;
    /// Sent to the focus window when the mouse wheel is rotated
    int WM_MOUSEWHEEL = 0x020A;
    /// Sent when an application requests that a window be created by calling the {@link User32#CreateWindowExW(DWORD, LPCWSTR, LPCWSTR, DWORD, int, int, int, int, HWND, HMENU, HINSTANCE, LPVOID)} or CreateWindow function
    int WM_CREATE = 0x0001;
    /// Sets the font that a control is to use when drawing text
    int WM_SETFONT = 0x0030;
    /// Used to define private messages, usually of the form WM_APP+x, where x is an integer value
    int WM_APP = 0x8000;
    /// Sent to cancel certain modes, such as mouse capture
    int WM_CANCELMODE = 0x001F;

    /// Activates the window and displays it in its current size and position
    int SW_SHOW = 5;
    /// Hides the window and activates another window
    int SW_HIDE = 0;

    /// The width of the screen of the primary display monitor, in pixels
    int SM_CXSCREEN = 0;
    /// The height of the screen of the primary display monitor, in pixels
    int SM_CYSCREEN = 1;

    /// Retains the current Z order
    int SWP_NOZORDER = 0x0004;

    /// Specifies that the menu item is a text string
    int MF_STRING = 0x00000000;
    /// Specifies that the menu item opens a drop-down menu or submenu
    int MF_POPUP = 0x00000010;
    /// Draws a horizontal dividing line
    int MF_SEPARATOR = 0x00000800;

    /// The message box contains one push button: OK. This is the default
    int MB_OK = 0x00000000;
    /// The message box contains two push buttons: Yes and No
    int MB_YESNO = 0x00000004;
    /// A stop-sign icon appears in the message box.
    int MB_ERRORICON = 0x00000010;
    /// An exclamation-point icon appears in the message box
    int MB_ICONWARNING = 0x00000030;

    /// Left mouse button
    int VK_LBUTTON = 0x01;
    /// Right mouse button
    int VK_RBUTTON = 0x02;
    /// Control-break processing
    int VK_CANCEL = 0x03;
    /// Middle mouse button
    int VK_MBUTTON = 0x04;
    /// Backspace key
    int VK_BACK = 0x08;
    /// Tab key
    int VK_TAB = 0x09;
    /// Clear key
    int VK_CLEAR = 0x0C;
    /// Enter key
    int VK_RETURN = 0x0D;
    /// Shift key
    int VK_SHIFT = 0x10;
    /// Ctrl key
    int VK_CONTROL = 0x11;
    /// Alt key
    int VK_MENU = 0x12;
    /// Pause key
    int VK_PAUSE = 0x13;
    /// Caps lock key
    int VK_CAPITAL = 0x14;
    /// Esc key
    int VK_ESCAPE = 0x1B;
    /// Spacebar key
    int VK_SPACE = 0x20;
    /// Page up key
    int VK_PRIOR = 0x21;
    /// Page down key
    int VK_NEXT = 0x22;
    /// End key
    int VK_END = 0x23;
    /// Home key
    int VK_HOME = 0x24;
    /// Left arrow key
    int VK_LEFT = 0x25;
    /// Up arrow key
    int VK_UP = 0x26;
    /// Right arrow key
    int VK_RIGHT = 0x27;
    /// Down arrow key
    int VK_DOWN = 0x28;
    /// Select key
    int VK_SELECT = 0x29;
    /// Print key
    int VK_PRINT = 0x2A;
    /// Execute key
    int VK_EXECUTE = 0x2B;
    /// Print screen key
    int VK_SNAPSHOT = 0x2C;
    /// Insert key
    int VK_INSERT = 0x2D;
    /// Delete key
    int VK_DELETE = 0x2E;
    /// Help key
    int VK_HELP = 0x2F;
    /// Multiply key
    int VK_MULTIPLY = 0x6A;
    /// Add key
    int VK_ADD = 0x6B;
    /// Separator key
    int VK_SEPARATOR = 0x6C;
    /// Subtract key
    int VK_SUBTRACT = 0x6D;
    /// Decimal key
    int VK_DECIMAL = 0x6E;
    /// Divide key
    int VK_DIVIDE = 0x6F;
    /// For any country/region, the Equals and Plus key
    int VK_OEM_PLUS = 0xBB;
    /// For any country/region, the Comma and Less Than key
    int VK_OEM_COMMA = 0xBC;
    /// For any country/region, the Dash and Underscore key
    int VK_OEM_MINUS = 0xBD;
    /// For any country/region, the Period and Greater Than key
    int VK_OEM_PERIOD = 0xBE;
    /// 0 key
    int VK_0 = 0x30;
    /// 1 key
    int VK_1 = 0x31;
    /// 2 key
    int VK_2 = 0x32;
    /// 3 key
    int VK_3 = 0x33;
    /// 4 key
    int VK_4 = 0x34;
    /// 5 key
    int VK_5 = 0x35;
    /// 6 key
    int VK_6 = 0x36;
    /// 7 key
    int VK_7 = 0x37;
    /// 8 key
    int VK_8 = 0x38;
    /// 9 key
    int VK_9 = 0x39;
    /// A key
    int VK_A = 0x41;
    /// B key
    int VK_B = 0x42;
    /// C key
    int VK_C = 0x43;
    /// D key
    int VK_D = 0x44;
    /// E key
    int VK_E = 0x45;
    /// F key
    int VK_F = 0x46;
    /// G key
    int VK_G = 0x47;
    /// H key
    int VK_H = 0x48;
    /// I key
    int VK_I = 0x49;
    /// J key
    int VK_J = 0x4A;
    /// K key
    int VK_K = 0x4B;
    /// L key
    int VK_L = 0x4C;
    /// M key
    int VK_M = 0x4D;
    /// N key
    int VK_N = 0x4E;
    /// O key
    int VK_O = 0x4F;
    /// P key
    int VK_P = 0x50;
    /// Q key
    int VK_Q = 0x51;
    /// R key
    int VK_R = 0x52;
    /// S key
    int VK_S = 0x53;
    /// T key
    int VK_T = 0x54;
    /// U key
    int VK_U = 0x55;
    /// V key
    int VK_V = 0x56;
    /// W key
    int VK_W = 0x57;
    /// X key
    int VK_X = 0x58;
    /// Y key
    int VK_Y = 0x59;
    /// Z key
    int VK_Z = 0x5A;

    /// The OK button was selected
    int IDOK = 1;
    /// The Cancel button was selected
    int IDCANCEL = 2;
    /// The Yes button was selected
    int IDYES = 6;
    /// The No button was selected
    int IDNO = 7;

    /// Redraws the entire window if a movement or size adjustment changes the width of the client area
    int CS_HREDRAW = 0x0002;
    /// Redraws the entire window if a movement or size adjustment changes the height of the client area
    int CS_VREDRAW = 0x0001;

    /// Notifies the application that the user has double-clicked an item in a list box
    int LBN_DBLCICK = 2;

    /// Removes all items from a list box
    int LB_RESETCONTENT = 0x0184;
    /// Adds a string to a list box. If the list box does not have the {@code LBS_SORT} style, the string is added to the end of the list. Otherwise, the string is inserted into the list and the list is sorted
    int LB_ADDSTRING = 0x0180;
    /// Gets the index of the currently selected item, if any, in a single-selection list box
    int LB_GETCURSEL = 0x0188;

    /// Shares the image handle if the image is loaded multiple times
    int LR_SHARED = 0x00008000;
    /// Loads the standalone image from the file specified by name (icon, cursor, or bitmap file)
    int LR_LOADFROMFILE = 0x00000010;

    /// Retrieves the window styles
    int GWL_STYLE = -16;

    ///    Loads a bitmap
    int IMAGE_BITMAP = 0;
    ///    Loads an icon
    int IMAGE_ICON = 1;
    ///    Loads a cursor
    int IMAGE_CURSOR = 2;

    /// Associates a new image (icon or bitmap) with the button
    int BM_SETIMAGE = 0x00F7;

    /// Selects a range of characters in an edit control. You can send this message to either an edit control or a rich edit control
    int EM_SETSEL = 0x00B1;
    /// Replaces the selected text in an edit control or a rich edit control with the specified text
    int EM_REPLACESEL = 0x00C2;

    /// Centers the shortcut menu horizontally relative to the coordinate specified by the x parameter
    int TPM_CENTERALIGN = 0x0004;
    /// Positions the shortcut menu so that its left side is aligned with the coordinate specified by the x parameter
    int TPM_LEFTALIGN = 0x0000;
    /// Positions the shortcut menu so that its right side is aligned with the coordinate specified by the x parameter
    int TPM_RIGHTALIGN = 0x0008;
    /// Positions the shortcut menu so that its bottom side is aligned with the coordinate specified by the y parameter
    int TPM_BOTTOMALIGN = 0x0020;
    /// Positions the shortcut menu so that its top side is aligned with the coordinate specified by the y parameter
    int TPM_TOPALIGN = 0x0000;
    /// Centers the shortcut menu vertically relative to the coordinate specified by the y parameter
    int TPM_VCENTERALIGN = 0x0010;
    /// The function does not send notification messages when the user clicks a menu item
    int TPM_NONOTIFY = 0x0080;
    /// The function returns the menu item identifier of the user's selection in the return value
    int TPM_RETURNCMD = 0x0100;
    /// The user can select menu items with only the left mouse button
    int TPM_LEFTBUTTON = 0x0000;
    /// The user can select menu items with both the left and right mouse buttons
    int TPM_RIGHTBUTTON = 0x002;

    /// Designates a multiline edit control
    LONG ES_MULTILINE = new LONG(0x0004);
    /// Automatically scrolls text up one page when the user presses the ENTER key on the last line
    LONG ES_AUTOVSCROLL = new LONG(0x0040);
    /// Automatically scrolls text to the right by 10 characters when the user types a character at the end of the line. When the user presses the ENTER key, the control scrolls all text back to position zero
    LONG ES_AUTOHSCROLL = new LONG(0x0080);
    /// Specifies that a carriage return be inserted when the user presses the ENTER key while entering text into a multiline edit control in a dialogue box
    LONG ES_WANTRETURN = new LONG(0x1000);

    /// Causes the list box to send a notification code to the parent window whenever the user clicks a list box item, double-clicks an item, or cancels the selection
    LONG LBS_NOTIFY = new LONG(0x0001);
    /// Specifies that a list box contains items consisting of strings
    LONG LBS_HASSTRINGS = new LONG(0x0040);
    /// Specifies that the button displays a bitmap
    LONG BS_BITMAP = new LONG(0x00000080);

    /// Creates a pointer value from an integer resource
    ///
    /// @param i The integer resource
    /// @return A {@link Pointer} containing the integer resource value
    static @NonNull Pointer MAKEINTRESOURCEW(int i) {
        return new Pointer(MemorySegment.ofAddress(i));
    }

    /// A callback function which is defined in the application
    interface Wndproc extends Callback {
        /// Creates a standard window with default {@link #WM_DESTROY} handling
        ///
        /// @return Returns a standard window procedure callback
        static @NonNull Wndproc defaultWndProc() {
            return (hWnd, uMsg, wParam, lParam) -> {
                if (uMsg.intValue() == WinUser.WM_DESTROY) {
                    User32.INSTANCE.PostQuitMessage(0);
                    return new LRESULT(0);
                }
                return User32.INSTANCE.DefWindowProcW(hWnd, uMsg, wParam, lParam);
            };
        }

        /// A callback function that processes messages sent to a window
        ///
        /// @param hWnd   A handle to the window
        /// @param uMsg   The message
        /// @param wParam Additional message information
        /// @param lParam Additional message information
        /// @return Return value is the result of the message processing and depends on the message sent
        LRESULT callback(HWND hWnd, UINT uMsg, WPARAM wParam, LPARAM lParam);
    }

    /// Contains window class information
    @SuppressWarnings("unused")
    @Structure.AutoFieldOrder
    class WNDCLASSEXW extends Structure {
        /// The size, in bytes, of this structure
        public UINT cbSize;
        /// The class style(s). This can be a combination of <a href="https://learn.microsoft.com/en-us/windows/win32/winmsg/window-class-styles"></a>
        public UINT style;
        /// A pointer to the window procedure
        public Wndproc lpfnWndProc;
        /// The number of extra bytes to allocate following the window-class structure. This initialises to 0
        public int cbClsExtra;
        /// The number of extra bytes to allocate following the window instance. This initialises to 0
        public int cbWndExtra;
        /// A {@link io.github.greymagic27.win_method.WinNT.HANDLE} to the instance that contains the window procedure for the class
        public HINSTANCE hInstance;
        /// A {@link io.github.greymagic27.win_method.WinNT.HANDLE} to the class icon. This must be a handle to an icon resource. If NULL, the default icon is provided
        public HICON hIcon;
        /// A {@link io.github.greymagic27.win_method.WinNT.HANDLE} to the class cursor. This must be a handle a cursor resource. if NULL, the application must explicitly set the cursor shape whenever the mouse moves into the application's window
        public HCURSOR hCursor;
        /// A {@link io.github.greymagic27.win_method.WinNT.HANDLE} to the class background brush. This member can be a handle to a brush or a colour value, however this must be one of the standard system colours. If a colour value is given, it must be converted to an <a href="https://learn.microsoft.com/en-us/windows/win32/api/winuser/ns-winuser-wndclassexw">HBRUSH type</a>
        public HBRUSH hbrBackground;
        /// {@link Pointer} to a null-terminated character string that specifies the resource name of the clas menu. If an integer is used, it must be used via the {@link #MAKEINTRESOURCEW(int)} function
        public LPCWSTR lpszMenuName;
        /// A pointer to a null-terminated character string or is an atom. If this is an atom, it must be a class atom created by a previous call to the {@link User32#RegisterClassExW(WNDCLASSEXW)} function
        public LPCWSTR lpszClassName;
        /// A {@link io.github.greymagic27.win_method.WinNT.HANDLE} to a small icon that is associated with the window class. If NULL, the system searches the icon resource specified by the {@link HICON} member for an icon of appropriate size
        public HICON hIconSm;
    }

    /// Contains message information from a thread's message queue
    @SuppressWarnings("unused")
    @Structure.AutoFieldOrder
    class MSG extends Structure {
        /// A {@link io.github.greymagic27.win_method.WinNT.HANDLE} to the window whose window procedure receives the message. This is NULL when the message is a thread message
        public HWND hwnd;
        /// The message identifier
        public UINT message;
        /// Additional information about the message
        public WPARAM wParam;
        /// Additional information about the message
        public LPARAM lParam;
        /// The time at which the message was posted
        public DWORD time;
        /// The cursor positon, in screen coordinates, when the message was posted
        public WinDef.POINT pt;
    }

    /// Contains information for an application. This information can be used to paint the client area of a window owned by that application
    @SuppressWarnings("unused")
    @Structure.AutoFieldOrder
    class PAINTSTRUCT extends Structure {
        /// A {@link io.github.greymagic27.win_method.WinNT.HANDLE} to the display DC to be used for painting
        public HDC hdc;
        /// Indicates whether the background must be erased
        public BOOL fErase;
        /// A {@link WinDef.RECT} structure that specifies the upper left and lower right corners of the rectangle in which the painting is requested
        public WinDef.RECT rcPaint;
        /// Reserved; used internally by the system
        public BOOL fRestore;
        /// Reserved; used internally by the system
        public BOOL fIncUpdate;
        /// Reserved; used internally by the system
        @ArrayLength(32)
        public BYTE[] rgbReserved;
    }
}
