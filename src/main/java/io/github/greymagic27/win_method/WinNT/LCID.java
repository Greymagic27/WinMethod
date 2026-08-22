package io.github.greymagic27.win_method.WinNT;

import io.github.greymagic27.win_method.IntSafe.DWORD;

/// A locale identifier. For more information, see [Locale Identifiers](https://learn.microsoft.com/en-us/windows/desktop/Intl/locale-identifiers)
public class LCID extends DWORD {
    /// Creates a {@code DWORD} from an {@code int} value
    ///
    /// @param value The value to store
    public LCID(int value) {
        super(value);
    }
}
