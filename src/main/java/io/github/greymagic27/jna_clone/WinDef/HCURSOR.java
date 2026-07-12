package io.github.greymagic27.jna_clone.WinDef;

import java.lang.foreign.MemorySegment;

/**
 * A {@link io.github.greymagic27.jna_clone.WinNT.HANDLE} to a cursor
 */
public class HCURSOR extends HICON {
    public HCURSOR(MemorySegment segment) {
        super(segment);
    }
}
