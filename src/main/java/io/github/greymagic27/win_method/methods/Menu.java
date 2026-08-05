package io.github.greymagic27.win_method.methods;

import io.github.greymagic27.win_method.BaseTsd.UINT_PTR;
import io.github.greymagic27.win_method.WinDef.HMENU;
import io.github.greymagic27.win_method.WinDef.UINT;
import io.github.greymagic27.win_method.WinNT.LPCWSTR;
import io.github.greymagic27.win_method.platform.User32;
import io.github.greymagic27.win_method.types.WinUser;

/// Helpers to create menus using {@link User32#AppendMenuW(HMENU, UINT, UINT_PTR, LPCWSTR)}
public class Menu {

    /// Appends a new item to the end of the specified menu bar, drop-down menu, submenu or shortcut menu
    ///
    /// @param menu    The menu bar, drop-down menu, submenu or shortcut menu to be changed
    /// @param flags   Controls the appearance and behaviour of the new menu item. This can be a combination of the
    ///                  {@code MF_*} values defined by Win32
    /// @param id      The identifier of the new menu item or, if {@code flags} contains {@link WinUser#MF_POPUP},
    ///                  a handle to the drop-down menu or submenu
    /// @param newItem The content of the new menu item. The interpretation depends on whether the {@code flags}
    ///                  parameter includes certain {@code MF_*} values
    /// @return {@code true} if the function succeeds, otherwise {@code false}
    public static boolean appendMenu(HMENU menu, int flags, int id, String newItem) {
        return User32.INSTANCE.AppendMenuW(menu, new UINT(flags), new UINT_PTR(id), new LPCWSTR(newItem)).booleanValue();
    }

    /// Appends a string menu item to the end of the specified menu
    ///
    /// @param menu    The menu to be changed
    /// @param id      The identifier of the new menu item
    /// @param newItem The text displayed for the new menu item
    /// @return {@code true} if the function succeeds, otherwise {@code false}
    public static boolean appendString(HMENU menu, int id, String newItem) {
        return User32.INSTANCE.AppendMenuW(menu, new UINT(WinUser.MF_STRING), new UINT_PTR(id), new LPCWSTR(newItem)).booleanValue();
    }

    /// Appends a separator item to the end of the specified menu
    ///
    /// A separator item does not have an identifier or displayed text.
    ///
    /// @param menu The menu to be changed
    /// @return {@code true} if the function succeeds, otherwise {@code false}
    public static boolean appendSeparator(HMENU menu) {
        return User32.INSTANCE.AppendMenuW(menu, new UINT(WinUser.MF_SEPARATOR), new UINT_PTR(0), null).booleanValue();
    }

    /// Appends a standard "Exit" string menu item to the end of the specified menu
    ///
    /// @param menu The menu to be changed
    /// @return {@code true} if the function succeeds, otherwise {@code false}
    public static boolean appendExitMenuItem(HMENU menu) {
        return User32.INSTANCE.AppendMenuW(menu, new UINT(WinUser.MF_STRING), new UINT_PTR(1002), new LPCWSTR("Exit")).booleanValue();
    }

    /// Appends a popup submenu item to the end of the specified menu
    ///
    /// When {@code MF_POPUP} is specified, the identifier is interpreted as a handle to the drop-down menu or submenu
    /// rather than a menu item identifier.
    ///
    /// @param menu    The menu to be changed
    /// @param id      A handle to the drop-down menu or submenu
    /// @param newItem The text displayed for the popup menu item
    /// @return {@code true} if the function succeeds, otherwise {@code false}
    public static boolean appendPopupMenu(HMENU menu, long id, String newItem) {
        return User32.INSTANCE.AppendMenuW(menu, new UINT(WinUser.MF_POPUP), new UINT_PTR(id), new LPCWSTR(newItem)).booleanValue();
    }
}
