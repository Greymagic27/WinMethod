package io.github.greymagic27.win_method.WinDef;

import io.github.greymagic27.win_method.BaseTsd.LONG_PTR;

/// A message parameter
public class LPARAM extends LONG_PTR {

    /// Creates an {@code LPARAM} from a {@code long} value
    ///
    /// @param value The value to store
    public LPARAM(long value) {
        super(value);
    }
}
