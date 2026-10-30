package io.github.greymagic27.win_method.WinDef;

import io.github.greymagic27.win_method.Pointer;
import java.lang.foreign.MemorySegment;

/// A {@link Pointer} to a UCHAR
public class PUCHAR extends Pointer {
    /// Creates a pointer from a memory segment
    ///
    /// @param segment The memory segment to wrap
    public PUCHAR(MemorySegment segment) {
        super(segment);
    }
}
