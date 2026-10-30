package io.github.greymagic27.win_method.WinDef;

import io.github.greymagic27.win_method.Pointer;
import java.lang.foreign.MemorySegment;

/// A {@link Pointer} to a {@link ULONG}
public class PULONG extends Pointer {

    /// Creates a pointer from a memory segment
    ///
    /// @param segment The memory segment to wrap
    public PULONG(MemorySegment segment) {
        super(segment);
    }
}
