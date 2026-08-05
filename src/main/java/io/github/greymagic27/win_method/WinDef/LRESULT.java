package io.github.greymagic27.win_method.WinDef;

import io.github.greymagic27.win_method.BaseTsd.LONG_PTR;

/// Signed result of message processing
public class LRESULT extends LONG_PTR {

    /// Creates an {@code LRESULT} from a {@code long} value
    ///
    /// @param value The value to store
    public LRESULT(long value) {
        super(value);
    }
}
