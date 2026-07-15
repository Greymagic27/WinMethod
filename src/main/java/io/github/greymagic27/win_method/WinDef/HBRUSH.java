package io.github.greymagic27.win_method.WinDef;

import io.github.greymagic27.win_method.WinNT.HANDLE;
import java.lang.foreign.MemorySegment;

/**
 * A {@link HANDLE} to a brush
 */
public class HBRUSH extends HANDLE {
    public HBRUSH(MemorySegment segment) {
        super(segment);
    }
}
