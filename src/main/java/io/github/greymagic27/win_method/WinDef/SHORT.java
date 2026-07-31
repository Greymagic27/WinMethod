package io.github.greymagic27.win_method.WinDef;

import org.jspecify.annotations.NonNull;

/// A 16-bit integer. The range is -32768 through 32767 decimal.
public class SHORT {

    private final short value;

    /// @param value A {@link Short value}
    public SHORT(short value) {
        this.value = value;
    }

    /// Returns the short value
    public short shortValue() {
        return value;
    }

    /// @return Returns the value of {@link #shortValue()} as a string
    @Override
    public @NonNull String toString() {
        return String.valueOf(shortValue());
    }
}
