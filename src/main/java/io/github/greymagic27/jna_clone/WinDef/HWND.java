package io.github.greymagic27.jna_clone.WinDef;

import io.github.greymagic27.jna_clone.HANDLE;
import java.lang.foreign.MemorySegment;

public class HWND extends HANDLE {

    public HWND(MemorySegment segment) {
        super(segment);
    }
}
