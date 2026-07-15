package io.github.greymagic27.win_method.WinDef;

import io.github.greymagic27.win_method.WinNT.HANDLE;
import java.lang.foreign.MemorySegment;

/**
 * A {@link HANDLE} to a menu
 */
public class HMENU extends HANDLE {
    public HMENU(MemorySegment segment) {
        super(segment);
    }
}
