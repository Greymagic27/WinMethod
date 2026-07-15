package io.github.greymagic27.win_method.WinDef;

import io.github.greymagic27.win_method.WinNT.HANDLE;
import java.lang.foreign.MemorySegment;

/**
 * A {@link HANDLE} to an icon
 */
public class HICON extends HANDLE {
    public HICON(MemorySegment segment) {
        super(segment);
    }
}
