package io.github.greymagic27.jna_clone.WinDef;

import org.jspecify.annotations.NonNull;

public class BOOL {

    private final int value;

    public BOOL(int value) {
        this.value = value;
    }

    public boolean booleanValue() {
        return value != 0;
    }

    public int intValue() {
        return value;
    }

    @Override
    public @NonNull String toString() {
        return String.valueOf(booleanValue());
    }
}