package io.github.greymagic27.win_method.methods;

import io.github.greymagic27.win_method.IntSafe.DWORD;
import io.github.greymagic27.win_method.WinDef.HFONT;
import io.github.greymagic27.win_method.WinNT.LPCWSTR;
import io.github.greymagic27.win_method.platform.GDI32;
import io.github.greymagic27.win_method.types.WinGdi;

public class Font {

    /// Creates a font with the specified font name and size
    ///
    /// @param name The font name
    /// @param size The font size in pixels
    /// @return The created font
    public static HFONT create(String name, int size) {
        return GDI32.INSTANCE.CreateFontW(-size, 0, 0, 0, WinGdi.FW_NORMAL, new DWORD(0), new DWORD(0), new DWORD(0), new DWORD(WinGdi.ANSI_CHARSET), new DWORD(WinGdi.OUT_DEFAULT_PRECIS), new DWORD(WinGdi.CLIP_DEFAULT_PRECIS), new DWORD(WinGdi.CLEARTYPE_QUALITY), new DWORD(0), new LPCWSTR(name));
    }
}
