package io.github.greymagic27.win_method.WinDef;

import org.jspecify.annotations.NonNull;

/// An unsigned {@link io.github.greymagic27.win_method.WinNT.SHORT}. The range is 0 through 65535 decimal.
public class USHORT {

    private final short value;

    /// @param value A {@link Short} value
    public USHORT(short value) {
        this.value = value;
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
