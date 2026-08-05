package io.github.greymagic27.win_method.WinDef;

import io.github.greymagic27.win_method.BaseTsd.UINT_PTR;

/// A message parameter
public class WPARAM extends UINT_PTR {

    /// Creates a {@code WPARAM} from a value
    ///
    /// @param value The value to store
    public WPARAM(long value) {
        super(value);
    }
}
