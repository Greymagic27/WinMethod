package io.github.greymagic27.win_method.WinNT;

import io.github.greymagic27.win_method.WinDef.BYTE;
import org.jspecify.annotations.NonNull;

/// A Boolean variable (should be TRUE or FALSE
public class BOOLEAN extends BYTE {

    /// Creates a {@code BYTE} from a {@code byte} value
    ///
    /// @param value The value to store
    public BOOLEAN(byte value) {
        super(value);
    }

    /// Creates a {@code BOOLEAN} from a {@code boolean} value
    ///
    /// @param value The Boolean value to store
    public BOOLEAN(boolean value) {
        super((byte) (value ? 1 : 0));
    }

    /// Returns the Boolean value
    public boolean booleanValue() {
        return byteValue() != 0;
    }

    /// @return Returns the value of {@link #booleanValue()} as a string
    @Override
    public @NonNull String toString() {
        return String.valueOf(booleanValue());
    }
}
