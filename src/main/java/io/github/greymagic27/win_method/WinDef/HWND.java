package io.github.greymagic27.win_method.WinDef;

import io.github.greymagic27.win_method.WinNT.HANDLE;
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
