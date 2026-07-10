package io.github.greymagic27.jna_clone.WinDef;

import org.jspecify.annotations.NonNull;

public class LONG {

    private int value = 0;

    public LONG() {

    }

    public LONG(int value) {
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
