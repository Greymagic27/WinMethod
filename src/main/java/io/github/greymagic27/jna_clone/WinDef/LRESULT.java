package io.github.greymagic27.jna_clone.WinDef;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class LRESULT {

    private final long value;

    @Contract(pure = true)
    public LRESULT(long value) {
        this.value = value;
    }

    @Contract(pure = true)
    public long longValue() {
        return value;
    }

    @Override
    public @NotNull String toString() {
        return String.valueOf(longValue());
    }
}
