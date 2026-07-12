package io.github.greymagic27.jna_clone.WinDef;

import io.github.greymagic27.jna_clone.WinNT.HANDLE;
import java.lang.foreign.MemorySegment;

/**
 * A {@link HANDLE} to a menu
 */
public class HMENU extends HANDLE {
    public HMENU(MemorySegment segment) {
        super(segment);
    }
}
