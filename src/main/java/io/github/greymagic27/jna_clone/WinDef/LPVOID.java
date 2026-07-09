package io.github.greymagic27.jna_clone.WinDef;

import io.github.greymagic27.jna_clone.Pointer;
import org.jspecify.annotations.NonNull;

public class LPVOID {

    private final Pointer p;

    public LPVOID(Pointer p) {
        this.p = p;
    }

    public Pointer pointerValue() {
        return p;
    }

    @Override
    public @NonNull String toString() {
        return String.valueOf(pointerValue());
    }
}
