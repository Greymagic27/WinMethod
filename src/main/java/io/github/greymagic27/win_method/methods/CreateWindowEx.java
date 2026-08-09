package io.github.greymagic27.win_method.methods;

import io.github.greymagic27.win_method.IntSafe.DWORD;
import io.github.greymagic27.win_method.WinDef.HMENU;
import io.github.greymagic27.win_method.WinDef.HWND;
import io.github.greymagic27.win_method.WinNT.LPCWSTR;
import io.github.greymagic27.win_method.platform.User32;
import io.github.greymagic27.win_method.types.WinUser;
import java.lang.foreign.MemorySegment;

public class CreateWindowEx {

    public static HWND createListBoxWindow(String windowName, HWND parent, int id, int x, int y, int width, int height) {
        return User32.INSTANCE.CreateWindowExW(new DWORD(0), new LPCWSTR("LISTBOX"), new LPCWSTR(windowName), new DWORD(WinUser.WS_CHILD | WinUser.WS_VISIBLE | WinUser.WS_BORDER | WinUser.WS_VSCROLL | WinUser.LBS_NOTIFY.intValue() | WinUser.LBS_HASSTRINGS.intValue()), x, y, width, height, parent, new HMENU(MemorySegment.ofAddress(id)), null, null);
    }

    public static HWND createEditWindow(String windowName, HWND parent, int id, int x, int y, int width, int height) {
        return User32.INSTANCE.CreateWindowExW(new DWORD(0), new LPCWSTR("EDIT"), new LPCWSTR(windowName), new DWORD(WinUser.WS_CHILD | WinUser.WS_VISIBLE | WinUser.WS_BORDER | WinUser.WS_VSCROLL | WinUser.WS_HSCROLL | WinUser.ES_MULTILINE.intValue() | WinUser.ES_AUTOVSCROLL.intValue() | WinUser.ES_AUTOHSCROLL.intValue() | WinUser.ES_WANTRETURN.intValue()), x, y, width, height, parent, new HMENU(MemorySegment.ofAddress(id)), null, null);
    }

    public static HWND createStaticWindow(String windowName, HWND parent, int id, int x, int y, int width, int height) {
        return User32.INSTANCE.CreateWindowExW(new DWORD(0), new LPCWSTR("STATIC"), new LPCWSTR(windowName), new DWORD(WinUser.WS_OVERLAPPED | WinUser.WS_VISIBLE), x, y, width, height, parent, new HMENU(MemorySegment.ofAddress(id)), null, null);
    }
}
