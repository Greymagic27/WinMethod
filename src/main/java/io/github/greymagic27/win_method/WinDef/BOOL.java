package io.github.greymagic27.win_method.WinDef;

import org.jspecify.annotations.NonNull;

/// A Boolean variable (should be **TRUE** or **FALSE**)
public class BOOL {

    private final int value;

    /// Creates a {@code BOOL} from an {@code int} value
    ///
    /// @param value The integer value to store
    public BOOL(int value) {
        this.value = value;
    }

    /// Returns the boolean value
    public boolean booleanValue() {
        return value != 0;
    }

    /// Returns the int value
    public int intValue() {
        return value;
    }

    /// @return Returns the value of {{@link #booleanValue()}} as a string
    @Override
    public @NonNull String toString() {
        return String.valueOf(booleanValue());
    }
}