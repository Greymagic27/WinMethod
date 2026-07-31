package io.github.greymagic27.win_method.BaseTsd;

import org.jspecify.annotations.NonNull;

/// A signed {@link Long} type
public class LONG_PTR {

    private final long value;

    /// @param value A {@link Long} value
    public LONG_PTR(long value) {
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
