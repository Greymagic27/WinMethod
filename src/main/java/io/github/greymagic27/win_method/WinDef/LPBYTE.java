package io.github.greymagic27.win_method.WinDef;

import io.github.greymagic27.win_method.Pointer;
import java.lang.foreign.MemorySegment;

/// A {@link io.github.greymagic27.win_method.Pointer} to a {@link BYTE}
public class LPBYTE extends Pointer {

    /// Creates a pointer from a memory segment
    ///
    /// @param segment The memory segment to wrap
    public LPBYTE(MemorySegment segment) {
        super(segment);
    }
}
