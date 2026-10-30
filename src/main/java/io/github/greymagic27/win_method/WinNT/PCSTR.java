package io.github.greymagic27.win_method.WinNT;

import io.github.greymagic27.win_method.Pointer;
import java.lang.foreign.MemorySegment;

/// A @{@link Pointer} to a constant null-terminated string of 8-bit Windows (ANSI) characters
public class PCSTR extends Pointer {

    /// Creates a pointer from a memory segment
    ///
    /// @param segment The memory segment to wrap
    public PCSTR(MemorySegment segment) {
        super(segment);
    }
}
