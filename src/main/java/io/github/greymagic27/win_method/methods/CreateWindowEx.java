package io.github.greymagic27.win_method.methods;

import io.github.greymagic27.win_method.IntSafe.DWORD;
import io.github.greymagic27.win_method.WinDef.HINSTANCE;
import io.github.greymagic27.win_method.WinDef.HMENU;
import io.github.greymagic27.win_method.WinDef.HWND;
import io.github.greymagic27.win_method.WinDef.LPVOID;
import io.github.greymagic27.win_method.WinNT.LPCWSTR;
import io.github.greymagic27.win_method.platform.User32;
import io.github.greymagic27.win_method.types.WinUser;
import java.lang.foreign.MemorySegment;

/// Helpers to create windows using {@link User32#CreateWindowExW(DWORD, LPCWSTR, LPCWSTR, DWORD, int, int, int, int, HWND, HMENU, HINSTANCE, LPVOID)}
public class CreateWindowEx {

    /// Creates a list box child window with the specified name, identifier, position, and dimensions.
    ///
    /// @param windowName The window name
    /// @param parent     A handle to the parent window of the list box
    /// @param id         The identifier of the list box
    /// @param x          The initial horizontal position of the list box
    /// @param y          The initial vertical position of the list box
    /// @param width      The width, in device units, of the list box
    /// @param height     The height, in device units, of the list box
    /// @return If the function succeeds, the return value is a handle to the new list box. If the function fails, the return value is NULL
    public static HWND createListBoxWindow(String windowName, HWND parent, int id, int x, int y, int width, int height) {
        return User32.INSTANCE.CreateWindowExW(new DWORD(0), new LPCWSTR("LISTBOX"), new LPCWSTR(windowName), new DWORD(WinUser.WS_CHILD | WinUser.WS_VISIBLE | WinUser.WS_BORDER | WinUser.WS_VSCROLL | WinUser.LBS_NOTIFY.intValue() | WinUser.LBS_HASSTRINGS.intValue()), x, y, width, height, parent, new HMENU(MemorySegment.ofAddress(id)), null, null);
    }

    /// Creates an edit child window with the specified name, identifier, position, and dimensions.
    ///
    /// @param windowName        The window name
    /// @param parent            A handle to the parent window of the edit control
    /// @param id                The identifier of the edit control
    /// @param x                 The initial horizontal position of the edit control
    /// @param y                 The initial vertical position of the edit control
    /// @param width             The width, in device units, of the edit control
    /// @param height            The height, in device units, of the edit control
    /// @param scrollBarsVisible Determines if vertical and horizontal scroll bars will be visible on the window
    public static HWND createEditWindow(String windowName, HWND parent, int id, int x, int y, int width, int height, boolean scrollBarsVisible) {
        return User32.INSTANCE.CreateWindowExW(new DWORD(0), new LPCWSTR("EDIT"), new LPCWSTR(windowName), new DWORD(WinUser.WS_CHILD | WinUser.WS_VISIBLE | WinUser.WS_BORDER | WinUser.ES_MULTILINE.intValue() | WinUser.ES_AUTOVSCROLL.intValue() | WinUser.ES_AUTOHSCROLL.intValue() | WinUser.ES_WANTRETURN.intValue() | (scrollBarsVisible ? WinUser.WS_VSCROLL | WinUser.WS_HSCROLL : 0)), x, y, width, height, parent, new HMENU(MemorySegment.ofAddress(id)), null, null);
    }

    /// Creates a static child window with the specified name, identifier, position, and dimensions.
    ///
    /// @param windowName The window name
    /// @param parent     A handle to the parent window of the static control
    /// @param id         The identifier of the static control
    /// @param x          The initial horizontal position of the static control
    /// @param y          The initial vertical position of the static control
    /// @param width      The width, in device units, of the static control
    /// @param height     The height, in device units, of the static control
    /// @return If the function succeeds, the return value is a handle to the new static control. If the function fails, the return value is NULL
    public static HWND createStaticWindow(String windowName, HWND parent, int id, int x, int y, int width, int height) {
        return User32.INSTANCE.CreateWindowExW(new DWORD(0), new LPCWSTR("STATIC"), new LPCWSTR(windowName), new DWORD(WinUser.WS_OVERLAPPED | WinUser.WS_VISIBLE), x, y, width, height, parent, new HMENU(MemorySegment.ofAddress(id)), null, null);
    }

    /// Creates a button child window with the specified name, identifier, position, and dimensions.
    ///
    /// @param windowName The window name
    /// @param parent     A handle to the parent window of the button
    /// @param id         The identifier of the button
    /// @param x          The initial horizontal position of the button
    /// @param y          The initial vertical position of the button
    /// @param width      The width, in device units, of the button
    /// @param height     The height, in device units, of the button
    /// @return If the function succeeds, the return value is a handle to the new button. If the function fails, the return value is NULL
    public static HWND createButtonWindow(String windowName, HWND parent, int id, int x, int y, int width, int height) {
        return User32.INSTANCE.CreateWindowExW(new DWORD(0), new LPCWSTR("BUTTON"), new LPCWSTR(windowName), new DWORD(WinUser.WS_CHILD | WinUser.WS_VISIBLE), x, y, width, height, parent, new HMENU(MemorySegment.ofAddress(id)), null, null);
    }

    /// Creates a button child window with the specified name, identifier, position, and dimensions.
    ///
    /// @param windowName      The window name
    /// @param additionalStyle Any additional styles. {@link WinUser#WS_CHILD} and {@link WinUser#WS_VISIBLE} are set by default
    /// @param parent          A handle to the parent window of the button
    /// @param id              The identifier of the button
    /// @param x               The initial horizontal position of the button
    /// @param y               The initial vertical position of the button
    /// @param width           The width, in device units, of the button
    /// @param height          The height, in device units, of the button
    /// @return If the function succeeds, the return value is a handle to the new button. If the function fails, the return value is NULL
    public static HWND createButtonWindow(String windowName, int additionalStyle, HWND parent, int id, int x, int y, int width, int height) {
        return User32.INSTANCE.CreateWindowExW(new DWORD(0), new LPCWSTR("BUTTON"), new LPCWSTR(windowName), new DWORD(WinUser.WS_CHILD | WinUser.WS_VISIBLE | additionalStyle), x, y, width, height, parent, new HMENU(MemorySegment.ofAddress(id)), null, null);
    }

    /// Creates an OK button child window with the specified identifier, position, and dimensions.
    ///
    /// @param parent A handle to the parent window of the button
    /// @param x      The initial horizontal position of the button
    /// @param y      The initial vertical position of the button
    /// @param width  The width, in device units, of the button
    /// @param height The height, in device units, of the button
    /// @return If the function succeeds, a handle to the new OK button. If the function fails, the return value is NULL
    public static HWND createOkButtonWindow(HWND parent, int x, int y, int width, int height) {
        return CreateWindowEx.createButtonWindow("Ok", parent, WinUser.IDOK, x, y, width, height);
    }

    /// Creates a Cancel button child window with the specified identifier, position, and dimensions.
    ///
    /// @param parent A handle to the parent window of the button
    /// @param x      The initial horizontal position of the button
    /// @param y      The initial vertical position of the button
    /// @param width  The width, in device units, of the button
    /// @param height The height, in device units, of the button
    /// @return If the function succeeds, a handle to the new Cancel button. If the function fails, the return value is NULL
    public static HWND createCancelButtonWindow(HWND parent, int x, int y, int width, int height) {
        return CreateWindowEx.createButtonWindow("Cancel", parent, WinUser.IDCANCEL, x, y, width, height);
    }
}
