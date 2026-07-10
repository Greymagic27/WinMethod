package io.github.greymagic27.jna_clone.WinDef;

import org.jspecify.annotations.NonNull;

public class BYTE {

    private final byte value;

    public BYTE(byte value) {
        this.value = value;
    }

    public byte byteValue() {
        return value;
    }

    @Override
    public @NonNull String toString() {
        return String.valueOf(byteValue());
    }
}
