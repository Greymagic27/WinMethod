package io.github.greymagic27.jna_clone.WinDef;

import io.github.greymagic27.jna_clone.WinNT.HANDLE;
import java.lang.foreign.MemorySegment;

/**
 * A {@link HANDLE} to an instance. This is the base address of the module in memory. This is the same as {@link HMODULE}
 */
public class HINSTANCE extends HANDLE {
    public HINSTANCE(MemorySegment segment) {
        super(segment);
    }
}
