package io.github.greymagic27.jna_clone.WinDef;

import java.lang.foreign.MemorySegment;

/**
 * A {@link io.github.greymagic27.jna_clone.WinNT.HANDLE} to a module. This is the base address of the module in memory. This is the same as {@link HINSTANCE}
 */
public class HMODULE extends HINSTANCE {
    public HMODULE(MemorySegment segment) {
        super(segment);
    }
}
