package io.github.greymagic27.win_method.WinDef;

import io.github.greymagic27.win_method.IntSafe.DWORD;

///    The red, green, blue (RGB) colour value (32 bits). See [COLORREF](https://learn.microsoft.com/en-us/windows/desktop/gdi/colorref) for information on this type
public class COLORREF extends DWORD {
    /// Creates a {@code DWORD} from an {@code int} value
    ///
    /// @param value The value to store
    public COLORREF(int value) {
        super(value);
    }
}
