package io.github.greymagic27.win_method.WinNT;

import io.github.greymagic27.win_method.Pointer;
import java.lang.foreign.MemorySegment;
import org.jspecify.annotations.NonNull;

/// A {@link Pointer} to a {@link HANDLE}
public class PHANDLE extends Pointer {

    /// Creates a pointer from a memory segment
    ///
    /// @param segment The memory segment to wrap
    public PHANDLE(MemorySegment segment) {
        super(segment);
    }

    /// Creates a pointer from an existing {@link Pointer}
    ///
    /// @param pointer The pointer to wrap
    public PHANDLE(@NonNull Pointer pointer) {
        super(pointer.segment);
    }
}
