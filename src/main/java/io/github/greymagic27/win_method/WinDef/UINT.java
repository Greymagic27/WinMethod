package io.github.greymagic27.win_method.WinDef;

import org.jspecify.annotations.NonNull;

/// An unsigned INT. The range is 0 through 4294967295 decimal
public class UINT {

    private final int value;

    /// @param value A {@link Integer} value
    public UINT(int value) {
        this.value = value;
    }

    /// Returns the int value
    public int intValue() {
        return value;
    }

    /// @return Returns the value of {@link #intValue()} as a string
    @Override
    public @NonNull String toString() {
        return String.valueOf(intValue());
    }
}
