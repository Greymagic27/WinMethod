package io.github.greymagic27.jna_clone.WinDef;

import org.jspecify.annotations.NonNull;

public final class WPARAM {

    private final int value;


    public WPARAM(int value) {
        this.value = value;
    }


    public int intValue() {
        return value;
    }

    @Override
    public @NonNull String toString() {
        return String.valueOf(intValue());
    }
}
