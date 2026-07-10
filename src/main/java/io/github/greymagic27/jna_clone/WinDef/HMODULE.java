package io.github.greymagic27.jna_clone.WinDef;

import java.lang.foreign.MemorySegment;

public class HMODULE extends HINSTANCE {
    public HMODULE(MemorySegment segment) {
        super(segment);
    }
}
