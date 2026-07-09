package io.github.greymagic27.jna_clone.WinDef;

import org.jspecify.annotations.NonNull;

public class WORD {

    private final short value;


    public WORD(short value) {
        this.value = value;
    }

    public short shortValue() {
        return value;
    }

    @Override
    public @NonNull String toString() {
        return String.valueOf(shortValue());
    }
}
