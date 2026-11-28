package io.github.greymagic27.win_method.WinDef;

import org.jspecify.annotations.NonNull;

/// A 16-bit unsigned integer. The range is 0 through 65535 decimal
public class WORD {

    private final short value;

    /// Creates a {@code WORD} from a {@code long} value
    ///
    /// @param value The value to store
    public WORD(long value) {
        this.value = (short) value;
    }

    /// Returns the short value
    public short shortValue() {
        return value;
    }

    /// @return Returns the value of {@link #shortValue()} as a string
    @Override
    public @NonNull String toString() {
        return String.valueOf(Short.toUnsignedInt(shortValue()));
    }
}
