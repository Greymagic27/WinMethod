package io.github.greymagic27.jna_clone.WinDef;

import org.jetbrains.annotations.NotNull;

public final class WPARAM {

    private final int value;

    public WPARAM(int value) {
        this.value = value;
    }

    public int intValue() {
        return value;
    }

    @Override
    public @NotNull String toString() {
        return String.valueOf(intValue());
    }
}
