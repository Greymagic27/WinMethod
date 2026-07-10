package io.github.greymagic27.jna_clone.WinDef;

import io.github.greymagic27.jna_clone.WinNT.HANDLE;
import java.lang.foreign.MemorySegment;

public class HDC extends HANDLE {
    public HDC(MemorySegment segment) {
        super(segment);
    }
}
