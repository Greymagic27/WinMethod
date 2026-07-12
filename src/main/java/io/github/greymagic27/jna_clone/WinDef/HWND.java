package io.github.greymagic27.jna_clone.WinDef;

import io.github.greymagic27.jna_clone.WinNT.HANDLE;
import java.lang.foreign.MemorySegment;

/**
 * A {@link HANDLE} to a window
 */
public class HWND extends HANDLE {
    public HWND(MemorySegment segment) {
        super(segment);
    }

    public HWND(long address) {
        super(address);
    }
}
