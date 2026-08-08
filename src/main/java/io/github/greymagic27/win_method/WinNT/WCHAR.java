package io.github.greymagic27.win_method.WinNT;

import org.jspecify.annotations.NonNull;

/// A 16-bit Unicode character
public class WCHAR {

    private final char value;

    /// @param value A {@link Character value}
    public WCHAR(char value) {
        this.value = value;
    }

    /// Returns the char value
    public char charValue() {
        return value;
    }

    /// @return Returns the value of {@link #charValue()} as a string
    @Override
    public @NonNull String toString() {
        return String.valueOf(charValue());
    }
}
