package io.github.greymagic27.win_method.WinNT;

import org.jspecify.annotations.NonNull;

/// A 64-bit unsigned integer. The range is 0 through 18446744073709551615 decimal
public class ULONGLONG {

    private final long value;

    /// @param value A {@link Long} value
    public ULONGLONG(long value) {
        this.value = value;
    }

    /// Returns the long value
    public long longValue() {
        return value;
    }

    /// @return Returns the value of {@link #longValue()} as a string
    @Override
    public @NonNull String toString() {
        return String.valueOf(longValue());
    }
}
