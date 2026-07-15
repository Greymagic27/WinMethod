package io.github.greymagic27.win_method.WinDef;

import io.github.greymagic27.win_method.WinNT.HANDLE;
import java.lang.foreign.MemorySegment;

/**
 * A {@link HANDLE} to an instance. This is the base address of the module in memory. This is the same as {@link HMODULE}
 */
public class HINSTANCE extends HANDLE {
    public HINSTANCE(MemorySegment segment) {
        super(segment);
    }
}
