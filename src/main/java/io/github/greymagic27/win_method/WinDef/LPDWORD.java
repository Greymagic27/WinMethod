package io.github.greymagic27.win_method.WinDef;

import io.github.greymagic27.win_method.Pointer;
import java.lang.foreign.MemorySegment;
import org.jspecify.annotations.NonNull;

/// A {@link Pointer} to a {@link io.github.greymagic27.win_method.IntSafe.DWORD}
public class LPDWORD extends Pointer {

    /// Creates a pointer from a memory segment
    ///
    /// @param segment The memory segment to wrap
    public LPDWORD(MemorySegment segment) {
        super(segment);
    }

    /// Creates a pointer from an existing {@link Pointer}
    ///
    /// @param pointer The pointer to wrap
    public LPDWORD(@NonNull Pointer pointer) {
        super(pointer.segment);
    }
}
