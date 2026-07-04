package io.github.greymagic27.jna_clone.WinDef;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class BYTE {

    private final byte value;

    @Contract(pure = true)
    public BYTE(byte value) {
        this.value = value;
    }

    @Contract(pure = true)
    public byte byteValue() {
        return value;
    }

    @Override
    public @NotNull String toString() {
        return String.valueOf(byteValue());
    }
}
