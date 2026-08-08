package io.github.greymagic27.win_method.WinNT;

import org.jspecify.annotations.NonNull;

/// A 16-bit Unicode character
public class WCHAR {

    private final char value;

    /// @param value A {@link Character value}
    public WCHAR(char value) {
        this.value = value;
    }

    /// Converts a null-terminated array of {@link WCHAR} values to a string.
    ///
    /// @param value The array of {@link WCHAR} values
    /// @return The string represented by the array
    public static @NonNull String toString(WCHAR @NonNull [] value) {
        StringBuilder result = new StringBuilder(value.length);
        for (WCHAR character : value) {
            if (character.charValue() == '\0') break;
            result.append(character.charValue());
        }
        return result.toString();
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
