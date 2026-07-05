package io.github.greymagic27.jna_clone.WinDef;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class WORD {

    private final short value;

    @Contract(pure = true)
    public WORD(short value) {
        this.value = value;
    }

    public short shortValue() {
        return value;
    }

    @Override
    public @NotNull String toString() {
        return String.valueOf(shortValue());
    }
}
