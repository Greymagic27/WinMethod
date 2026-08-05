package io.github.greymagic27.win_method.WinNT;

import org.jspecify.annotations.NonNull;

/// A 32-bit signed integer. The range is -2147483648 through 2147483647 decimal
public class LONG {

    private final int value;

    /// Creates a {@code LONG} from an {@code int} value
    ///
    /// @param value The value to store
    public LONG(int value) {
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
