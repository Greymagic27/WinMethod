package io.github.greymagic27.jna_clone.WinDef;

import java.lang.foreign.MemorySegment;

public final class HMODULE extends HINSTANCE{

    public HMODULE(MemorySegment segment) {
        super(segment);
    }
}
