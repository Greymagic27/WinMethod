package io.github.greymagic27.win_method.WinDef;

import org.jspecify.annotations.NonNull;

public class UINT_PTR {

    private final long value;

    public UINT_PTR(long value) {
        this.value = value;
    }

    public long longValue() {
        return value;
    }

    @Override
    public @NonNull String toString() {
        return String.valueOf(longValue());
    }
}
