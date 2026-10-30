package io.github.greymagic27.win_method.WinDef;

import org.jspecify.annotations.NonNull;

/// An unsigned {@link io.github.greymagic27.win_method.WinNT.LONG}. The range is 0 through 4294967295 decimal
public class ULONG {

    private final long value;

    /// @param value A {@link Long} value
    public ULONG(long value) {
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
