package io.github.greymagic27.win_method.platform;

import io.github.greymagic27.win_method.BaseTsd.UINT_PTR;
import io.github.greymagic27.win_method.IntSafe.DWORD;
import io.github.greymagic27.win_method.Library;
import io.github.greymagic27.win_method.WinDef.ATOM;
import io.github.greymagic27.win_method.WinDef.BOOL;
import io.github.greymagic27.win_method.WinDef.HBRUSH;
import io.github.greymagic27.win_method.WinDef.HDC;
import io.github.greymagic27.win_method.WinDef.HINSTANCE;
import io.github.greymagic27.win_method.WinDef.HMENU;
import io.github.greymagic27.win_method.WinDef.HWND;
import io.github.greymagic27.win_method.WinDef.LPARAM;
import io.github.greymagic27.win_method.WinDef.LPVOID;
import io.github.greymagic27.win_method.WinDef.LRESULT;
import io.github.greymagic27.win_method.WinDef.UINT;
import io.github.greymagic27.win_method.WinDef.WPARAM;
import io.github.greymagic27.win_method.WinNT.LPCWSTR;
import io.github.greymagic27.win_method.WinNT.LPWSTR;
import io.github.greymagic27.win_method.WinNT.SHORT;
import io.github.greymagic27.win_method.types.WinDef;
import io.github.greymagic27.win_method.types.WinUser;

/// Interface for User32.dll
public interface User32 extends Library {
    /// The instance
    User32 INSTANCE = Library.load(User32.class);

    /// Registers a window class for subsequent use in calls to the {@link #CreateWindowExW(DWORD, LPCWSTR, LPCWSTR, DWORD, int, int, int, int, HWND, HMENU, HINSTANCE, LPVOID)} function.
    ///
    /// @param unnamedParam1 A {@link io.github.greymagic27.win_method.Pointer} to a {@link WinUser.WNDCLASSEXW} structure
    /// @return Return value is a class atom that uniquely identifies the class being registered. If the function fails, the return value is zero
    ATOM RegisterClassExW(WinUser.WNDCLASSEXW unnamedParam1);

    /// Destroys the specified window. The function sends WM_DESTROY and WM_NCDESTROY messages to the window to deactivate and remove keyboard focus
    ///
    /// @param hWnd A handle to the window to be destroyed
    /// @return Return value is nonzero. If the function fails, the return value is zero
    BOOL DestroyWindow(HWND hWnd);

    /// Dispatches a message to a window procedure
    ///
    /// @param msg Pointer to a structure that contains the message
    /// @return Specifies the value returned by the window procedure
    BOOL DispatchMessageW(WinUser.MSG msg);

    /// Retrieves the dimensions of the bounding rectangle of the specified window. These are relative to the upper-left corner of the screen
    ///
    /// @param hWnd   A  {@link io.github.greymagic27.win_method.WinNT.HANDLE} to the window
    /// @param lpRect Pointer to a {@link WinDef.RECT} structure
    /// @return Return value is nonzero. If the function fails, the return value is zero
    BOOL GetWindowRect(HWND hWnd, WinDef.RECT lpRect);

    /// Retrieves the coordinates of the window's client area
    ///
    /// @param hWnd   A {@link io.github.greymagic27.win_method.WinNT.HANDLE} to the window whose client coordinates are to be retrieved
    /// @param lpRect A {@link io.github.greymagic27.win_method.Pointer} to a {@link WinDef.RECT} structure that retrieves the client coordinates
    /// @return Return value is nonzero. If the function fails, the return value is zero
    BOOL GetClientRect(HWND hWnd, WinDef.RECT lpRect);

    /// Adds a rectangle to the specified window's update region. The update region is the portion of the client that must be redrawn
    ///
    /// @param hWnd   A {@link io.github.greymagic27.win_method.WinNT.HANDLE} to the window whose update region has changed
    /// @param lpRect A {@link io.github.greymagic27.win_method.Pointer} to a {@link WinDef.RECT} structure that contains the client coordinates of the rectangle to be added to the update region
    /// @param bErase Specifies whether the background within the update region is to be erased when the update region is processed
    /// @return If the function succeeds, the return value is nonzero. If the function fails, the return value is zero
    BOOL InvalidateRect(HWND hWnd, WinDef.RECT lpRect, BOOL bErase);

    /// Sets the specified window's show state
    ///
    /// @param hWnd     Handle to the window
    /// @param nCmdShow Controls how the window is to be shown
    /// @return If previously visible, returns nonzero. If previously hidden, returns zero
    BOOL ShowWindow(HWND hWnd, int nCmdShow);

    /// Changes the size, position and Z order of a child, pop-up or top-level window
    ///
    /// @param hWnd            Handle to the window
    /// @param hWndInsertAfter Handle to the window to precede the positioned window in the Z order
    /// @param X               New position of the left side of the window, in client coordinates
    /// @param Y               New position of the top of the window, in client coordinates
    /// @param cx              The new width of the window, in pixels
    /// @param cy              The new height of the window, in pixels
    /// @param uFlags          The window sizing and positioning flags. These can be a combination of SWP flags
    /// @return Return value is nonzero. If the function fails, the return value is zero
    BOOL SetWindowPos(HWND hWnd, HWND hWndInsertAfter, int X, int Y, int cx, int cy, UINT uFlags);

    /// Translates virtual-key messages into character messages. These are posted to the calling thread's message queue, to be read the next time the thread calls {@link #GetMessageW(WinUser.MSG, HWND, UINT, UINT)} or PeekMessage functions
    ///
    /// @param msg Pointer to a {@link WinUser.MSG} structure that contains message information
    /// @return If the message is translated, the return value is nonzero. If not translated, the return value is zero
    BOOL TranslateMessage(WinUser.MSG msg);

    /// Updates the client area of the specified window by sending a WM_PAINT message to the window if the window's update region is not empty
    ///
    /// @param hWnd Handle to the window to be updated
    /// @return if the function succeeds, the return value is nonzero. If the function fails, the return value is zero
    BOOL UpdateWindow(HWND hWnd);

    /// Places a message in the message queue associated with the thread that created the window, and returns without waiting for the thread to process the message
    ///
    /// @param hWnd   A {@link io.github.greymagic27.win_method.WinNT.HANDLE} to the window whose procedure is to receive the message
    /// @param Msg    The message to be posted
    /// @param wParam Additional message-specific information
    /// @param lParam Additional message-specific information
    /// @return If the function succeeds, the return value is nonzero. If the function fails, the return value is zero
    BOOL PostMessageW(HWND hWnd, UINT Msg, WPARAM wParam, LPARAM lParam);

    /// Retrieves a message from the calling thread's message queue
    ///
    /// @param lpMsg         Pointer to a {@link WinUser.MSG} structure that receives message information
    /// @param hWnd          Handle to the window whose messages are to be retrieved. The window must belong to the current thread
    /// @param wMsgFilterMin Integer value of the lowest message value to be retrieved. Use WM_KEYFIRST to specify first keyboard message or WM_MOUSEFIRST to specify first mouse message
    /// @param wMsgFilterMax Integer value of the highest message value to be retrieved. Use WM_KEYLAST to specify last keyboard message or WM_MOUSELAST to specify last mouse message
    /// @return If the function retrieves a message other than WM_QUIT, the return value is nonzero. If the function retrieves WM_QUT, the return value is zero. If there is an error, the return value is -1
    BOOL GetMessageW(WinUser.MSG lpMsg, HWND hWnd, UINT wMsgFilterMin, UINT wMsgFilterMax);

    /// Changes the positon and dimensions of the specified window
    /// Top-level windows are relative to the upper-left corner of the screen
    /// Children windows are relative to the upper-left corner of the parent window's client area
    ///
    /// @param hWnd     A {@link io.github.greymagic27.win_method.WinNT.HANDLE} to the window
    /// @param X        The new positon of the left side of the window
    /// @param Y        The new position of the top of the window
    /// @param nWidth   The new width of the window
    /// @param nHeight  The new height of the window
    /// @param bRepaint Indicates whether the window is to be repainted. If **TRUE** the window receives a message. If **FALSE** no repainting occurs
    /// @return If the function succeeds, the return value is nonzero. If the function fails, the return value is zero
    BOOL MoveWindow(HWND hWnd, int X, int Y, int nWidth, int nHeight, BOOL bRepaint);

    /// Appends a new item to the end of the specified menu bar, drop-down menu, submenu or shortcut menu
    ///
    /// @param hMenu      A {@link io.github.greymagic27.win_method.WinNT.HANDLE} to the menu bar, drop-down menu, submenu or shortcut menu to be changed
    /// @param uFlags     Controls the appearance and behaviour of the new menu item. This can be a combination of [the following values](https://learn.microsoft.com/en-us/windows/win32/api/winuser/nf-winuser-appendmenuw)
    /// @param uIDNewItem The identifier of the new menu item or, if the uFlags parameter is set to MF_POPUP, a handle to the drop-down menu or submenu
    /// @param lpNewItem  The content of the new menu item. The interpretation depends on whether the uFlags parameter includes [the following values](https://learn.microsoft.com/en-us/windows/win32/api/winuser/nf-winuser-appendmenuw)
    /// @return If the function succeeds, the return value is nonzero. If the function fails, the return value is zero
    BOOL AppendMenuW(HMENU hMenu, UINT uFlags, UINT_PTR uIDNewItem, LPCWSTR lpNewItem);

    /// Assigns a new menu to the specified window
    ///
    /// @param hWnd  A {@link io.github.greymagic27.win_method.WinNT.HANDLE} to the window to which the menu is to be assigned
    /// @param hMenu A {@link io.github.greymagic27.win_method.WinNT.HANDLE} to the new menu. If NULL, the window's current menu is removed
    /// @return If the function succeeds, the return value is nonzero. If the function fails, the return value is zero
    BOOL SetMenu(HWND hWnd, HMENU hMenu);

    /// Destroys the specified menu and frees and memory that the menu occupies
    ///
    /// @param hMenu A {@link io.github.greymagic27.win_method.WinNT.HANDLE} to the menu to be destroyed
    /// @return If the function succeeds, the return value is nonzero. If the function fails, the return value is zero
    BOOL DestroyMenu(HMENU hMenu);

    /// Changes the text of the specified window's title bar
    ///
    /// @param hWnd     A {@link io.github.greymagic27.win_method.WinNT.HANDLE} to the window or control whose text is to be changed
    /// @param lpString The new title or control text
    /// @return If the function succeeds, the return value is nonzero. If the function fails, the return value is zero
    BOOL SetWindowTextW(HWND hWnd, LPCWSTR lpString);

    /// Marks the end of painting in the specified window
    ///
    /// @param hWnd    {@link io.github.greymagic27.win_method.WinNT.HANDLE} to the window that has been repainted
    /// @param lpPaint {@link io.github.greymagic27.win_method.Pointer} to a {@link WinUser.PAINTSTRUCT} structure that contains the painting information retrieved by {@link #BeginPaint(HWND, WinUser.PAINTSTRUCT)}
    /// @return The return value is always nonzero
    BOOL EndPaint(HWND hWnd, WinUser.PAINTSTRUCT lpPaint);

    /// Redraws the menu bar of the specified window
    ///
    /// @param hWnd A {@link io.github.greymagic27.win_method.WinNT.HANDLE} to the window whose menu bar is to be redrawn
    /// @return If the function succeeds, the return value is nonzero. If the function fails, the return value is zero
    BOOL DrawMenuBar(HWND hWnd);

    /// Enables or disables mouse and keyboard input to the specified window or control
    ///
    /// @param hWnd    A {@link io.github.greymagic27.win_method.WinNT.HANDLE} to the window to be enabled or disabled
    /// @param bEnable Indicates whether to enable or disable the window. If this parameter is TRUE (1), the window is enabled. If the parameter is FALSE (0), the window is disabled
    /// @return If the window was previously disabled, the return value is nonzero. If the window was not previously disabled, the return value is zero
    BOOL EnableWindow(HWND hWnd, BOOL bEnable);

    /// Retrieves the specified system metric or system configuration setting
    ///
    /// @param nIndex System metric or configuration setting to be retrieved
    /// @return If the function succeeds, the return value is the requested system metric or configuration setting. If the function fails, the return value is 0
    int GetSystemMetrics(int nIndex);

    /// Displays a modal dialogue box that contains a system icon, set of buttons, and a brief application-specific message
    ///
    /// @param hWnd      A {@link io.github.greymagic27.win_method.WinNT.HANDLE} to the owner window of the message box to be created. If NULL, the message box has no owner window
    /// @param lpText    The message to be displayed. Multiple lines can be displayed by separating the lines using a carriage return and/or linefeed character between each line
    /// @param lpCaption The dialogue box title. If NULL, this defaults to Error
    /// @param uType     The contents and behaviour of the dialogue box. This can be a combination of flags from the [following groups](https://learn.microsoft.com/en-us/windows/win32/api/winuser/nf-winuser-messageboxw)
    /// @return If the message box has a cancel button, the function returns IDCANCEL. If an MB_OK button is displayed, the return value will be IDOK
    int MessageBoxW(HWND hWnd, LPCWSTR lpText, LPCWSTR lpCaption, UINT uType);

    /// Fills a rectangle by using the specified brush. This function includes the left and top borders, but excludes the right and bottom borders of the rectangle
    ///
    /// @param hdc  A {@link io.github.greymagic27.win_method.WinNT.HANDLE} to the device context
    /// @param lprc A {@link io.github.greymagic27.win_method.Pointer} to a {@link WinDef.RECT} structure that contains the logical coordinates of the rectangle to be filled
    /// @param hbr  A {@link io.github.greymagic27.win_method.WinNT.HANDLE} to the brush used to fill the rectangle
    /// @return If the function succeeds, the return value is nonzero. If the function fails, the return value is zero
    int FillRect(HDC hdc, WinDef.RECT lprc, HBRUSH hbr);

    /// Releases a device context (DC), freeing it for use by other applications
    ///
    /// @param hWnd A {@link io.github.greymagic27.win_method.WinNT.HANDLE} to the window whose DC is to be released
    /// @param hdc  A {@link io.github.greymagic27.win_method.WinNT.HANDLE} to the DC to be released
    /// @return The return value indicates whether the DC was released. If the DC was released, the return value is 1. If the DC was not released, the return value is zero
    int ReleaseDC(HWND hWnd, HDC hdc);

    /// Copies the text of the specified window's title bar (if it has one) into a buffer
    ///
    /// @param hWnd      A {@link io.github.greymagic27.win_method.WinNT.HANDLE} to the window or control containing the text
    /// @param lpString  The buffer that will receive the text. If the string is as long or longer than the buffer, the string is truncated and terminated with a null character
    /// @param nMaxCount The maximum number of characters to copy to the buffer, including the null character. If the text exceeds this limit, the text is truncated
    /// @return If the function succeeds, the return value is the length, in characters, of the copied string, not including the terminating null character
    int GetWindowTextW(HWND hWnd, LPWSTR lpString, int nMaxCount);

    /// Retrieves the length, in characters, of the specified window's title bar text (if the window has a title bar)
    ///
    /// @param hWnd A {@link io.github.greymagic27.win_method.WinNT.HANDLE} to the window or control
    /// @return If the function succeeds, the return value is the length, in characters, of the text. If the window has no text, the return value is zero
    int GetWindowTextLengthW(HWND hWnd);

    /// Calls the default window procedure to provide default processing for any window messages that an application does not process
    ///
    /// @param hWnd   Handle to the window procedure that received the message
    /// @param Msg    The message
    /// @param wParam Additional message information. The content of this depends on the value of the Msg parameter
    /// @param lParam Additional message information. The content of this depends on the value of the Msg parameter
    /// @return Return value is the result of the message processing and depends on the message
    LRESULT DefWindowProcW(HWND hWnd, UINT Msg, WPARAM wParam, LPARAM lParam);

    /// Sends the specified message to a window or windows
    ///
    /// @param hWnd   A {@link io.github.greymagic27.win_method.WinNT.HANDLE} to the window whose window procedure will receive the message
    /// @param Msg    The message to be sent
    /// @param wParam Additional message-specific information
    /// @param lParam Additional message-specific information
    /// @return The return value specifies the result of the message processing; it depends on the message sent
    LRESULT SendMessageW(HWND hWnd, UINT Msg, WPARAM wParam, LPARAM lParam);

    /// Creates an overlapped, pop-up or child window with an extended window style
    ///
    /// @param dwExStyle    The <a href="https://learn.microsoft.com/en-us/windows/desktop/winmsg/extended-window-styles">extended window style</a> of the window
    /// @param lpClassName  A null-terminated string or class atom
    /// @param lpWindowName The window name. This will be displayed in the title bar if the window title specifies a title bar
    /// @param dwStyle      The style of the window being created. This can be a combination of <a href="https://learn.microsoft.com/en-us/windows/win32/winmsg/window-styles">window style values</a> and <a href="https://learn.microsoft.com/en-us/windows/win32/api/winuser/nf-winuser-createwindowexw#remarks">control styles</a>
    /// @param X            The initial horizontal position of the window
    /// @param Y            The initial vertical position of the window
    /// @param nWidth       The width, in device units, of the window
    /// @param nHeight      The height, in device units, of the window
    /// @param hWndParent   A handle to the parent or owner window of the window being created
    /// @param hMenu        A handle to a menu or child-window identifier depending on window style
    /// @param hInstance    A handle to the instance of the module to be associated with the window
    /// @param lpParam      Pointer to a value to be passed to the window through the CREATESTRUCT structure pointed to by the lParam param of the WM_CREATE message
    /// @return If the function succeeds, the return value is a handle to the new window. If the function fails, the return value is NULL
    HWND CreateWindowExW(DWORD dwExStyle, LPCWSTR lpClassName, LPCWSTR lpWindowName, DWORD dwStyle, int X, int Y, int nWidth, int nHeight, HWND hWndParent, HMENU hMenu, HINSTANCE hInstance, LPVOID lpParam);

    /// Creates a menu. This is initially empty but can be filled with menu items by using the [InsertMenuItem](https://learn.microsoft.com/en-us/windows/desktop/api/winuser/nf-winuser-insertmenuitemw), {@link #AppendMenuW(HMENU, UINT, UINT_PTR, LPCWSTR)} and [InsertMenu](https://learn.microsoft.com/en-us/windows/desktop/api/winuser/nf-winuser-insertmenuw) functions
    ///
    /// @return If the function succeeds, the return value is a {@link io.github.greymagic27.win_method.WinNT.HANDLE} to the newly created menu. If the function fails, the return value is NULL
    HMENU CreateMenu();

    /// Creates a drop-down menu, submenu or shortcut menu. This is initially empty but can be filled with menu items by using the [InsertMenuItem](https://learn.microsoft.com/en-us/windows/desktop/api/winuser/nf-winuser-insertmenuitemw), {@link #AppendMenuW(HMENU, UINT, UINT_PTR, LPCWSTR)} and [InsertMenu](https://learn.microsoft.com/en-us/windows/desktop/api/winuser/nf-winuser-insertmenuw) functions
    ///
    /// @return If the function succeeds, the return value is a {@link io.github.greymagic27.win_method.WinNT.HANDLE} to the newly created menu. If the function fails, the return value is NULL
    HMENU CreatePopupMenu();

    /// Retrieves the status of the specified key
    ///
    /// @param nVirtKey A virtual key
    /// @return Specifies the status of the specified virtual key
    SHORT GetKeyState(int nVirtKey);

    /// Prepares the specified window for painting and fills a {@link WinUser.PAINTSTRUCT} structure with information about the painting
    ///
    /// @param hWnd    {@link io.github.greymagic27.win_method.WinNT.HANDLE} to the window to be repainted
    /// @param lpPaint {@link io.github.greymagic27.win_method.Pointer} to the {@link WinUser.PAINTSTRUCT} structure that will receive painting information
    /// @return If the function succeeds, the return value is the handle to a display device context for the specified window. If the function fails, the return value is NULL
    HDC BeginPaint(HWND hWnd, WinUser.PAINTSTRUCT lpPaint);

    /// Retries a {@link io.github.greymagic27.win_method.WinNT.HANDLE} to a device context for the client area of a specific window or for the entire screen
    ///
    /// @param hWnd A {@link io.github.greymagic27.win_method.WinNT.HANDLE} to the window whose DC is to be retried. If NULL, the function retries the DC for the entire screen
    /// @return If the function succeeds, the return value is a {@link io.github.greymagic27.win_method.WinNT.HANDLE} to the DC for the specified window's client area. If the function fails, the return value is NULL
    HDC GetDC(HWND hWnd);

    /// Retries a {@link io.github.greymagic27.win_method.WinNT.HANDLE} to the specified window's parent or owner
    ///
    /// @param hWnd A {@link io.github.greymagic27.win_method.WinNT.HANDLE} to the window whose parent window handle is to be retrieved
    /// @return If the window is a child window, the return value is a handle to the parent window. If the window is a top-level window with the WS_POPUP style, the return value is a handle to the owner window. If the function fails, the return value is NULL
    HWND GetParent(HWND hWnd);

    /// Indicates to the system that a thread has made a request to terminate. It is typically used in response to a WM_DESTROY message.
    ///
    /// @param nExitCode The application exit code. This is used as the wParam parameter of the WM_QUIT message
    void PostQuitMessage(int nExitCode);
}
