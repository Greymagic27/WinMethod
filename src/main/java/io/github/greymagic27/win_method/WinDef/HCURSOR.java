package io.github.greymagic27.win_method.WinDef;

import java.lang.foreign.MemorySegment;

/**
 * A {@link io.github.greymagic27.win_method.WinNT.HANDLE} to a cursor
 */
public class HCURSOR extends HICON {
    public HCURSOR(MemorySegment segment) {
        super(segment);
    }
}
