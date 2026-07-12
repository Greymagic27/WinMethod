package io.github.greymagic27.jna_clone.WinDef;

import io.github.greymagic27.jna_clone.WinNT.HANDLE;
import java.lang.foreign.MemorySegment;

/**
 * A {@link HANDLE} to an icon
 */
public class HICON extends HANDLE {
    public HICON(MemorySegment segment) {
        super(segment);
    }
}
