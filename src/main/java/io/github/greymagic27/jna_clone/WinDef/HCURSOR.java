package io.github.greymagic27.jna_clone.WinDef;

import java.lang.foreign.MemorySegment;

public class HCURSOR extends HICON {
    public HCURSOR(MemorySegment segment) {
        super(segment);
    }
}
