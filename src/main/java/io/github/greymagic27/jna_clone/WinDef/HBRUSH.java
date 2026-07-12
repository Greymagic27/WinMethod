package io.github.greymagic27.jna_clone.WinDef;

import io.github.greymagic27.jna_clone.WinNT.HANDLE;
import java.lang.foreign.MemorySegment;

/**
 * A {@link HANDLE} to a brush
 */
public class HBRUSH extends HANDLE {
    public HBRUSH(MemorySegment segment) {
        super(segment);
    }
}
