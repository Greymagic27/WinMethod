package io.github.greymagic27.win_method.WinNT;

import io.github.greymagic27.win_method.Pointer;
import java.lang.foreign.MemorySegment;

/// A {@link io.github.greymagic27.win_method.Pointer} to a constant null-terminated string of 16-bit Unicode characters
public class PCWSTR extends Pointer {

    /// Creates a pointer from a memory segment
    ///
    /// @param segment The memory segment to wrap
    public PCWSTR(MemorySegment segment) {
        super(segment);
    }
}