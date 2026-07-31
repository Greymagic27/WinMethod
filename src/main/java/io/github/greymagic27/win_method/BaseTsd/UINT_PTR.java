package io.github.greymagic27.win_method.BaseTsd;

import org.jspecify.annotations.NonNull;

/// An unsigned [INT_PTR](https://learn.microsoft.com/en-us/windows/win32/winprog/windows-data-types#int_ptr)
public class UINT_PTR {

    private final long value;

    /// @param value A {@link Long} value
    public UINT_PTR(long value) {
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
