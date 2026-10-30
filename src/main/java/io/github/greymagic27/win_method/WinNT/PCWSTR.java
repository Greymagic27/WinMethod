package io.github.greymagic27.win_method.WinNT;

import io.github.greymagic27.win_method.Pointer;
import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.nio.charset.StandardCharsets;

/// A {@link io.github.greymagic27.win_method.Pointer} to a constant null-terminated string of 16-bit Unicode characters
public class PCWSTR extends Pointer {

    /// Creates a pointer from a memory segment
    ///
    /// @param segment The memory segment to wrap
    public PCWSTR(MemorySegment segment) {
        super(segment);
    }

    /// Allocates a null-terminated string of 16-bit Unicode characters
    ///
    /// @param value The string to be allocated
    public PCWSTR(String value) {
        super(value == null ? MemorySegment.NULL : Arena.ofAuto().allocateFrom(value, StandardCharsets.UTF_16LE));
    }
}