package io.github.greymagic27.win_method.WinDef;

import org.jspecify.annotations.NonNull;

/// A byte (8 bits)
public class BYTE {

    private final byte value;

    /// Creates a {@code BYTE} from a {@code byte} value
    ///
    /// @param value The value to store
    public BYTE(byte value) {
        this.value = value;
    }

    /// Returns the byte value
    public byte byteValue() {
        return value;
    }

    /// @return Returns the value of {@link #byteValue()} as a string
    @Override
    public @NonNull String toString() {
        return String.valueOf(byteValue());
    }
}
