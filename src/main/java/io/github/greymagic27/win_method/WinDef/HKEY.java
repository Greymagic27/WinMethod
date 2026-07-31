package io.github.greymagic27.win_method.WinDef;

import io.github.greymagic27.win_method.WinNT.HANDLE;
import java.lang.foreign.MemorySegment;

/// A {@link HANDLE} to a registry key
public class HKEY extends HANDLE {
    public HKEY(MemorySegment segment) {
        super(segment);
    }
}
