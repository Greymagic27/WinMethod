package io.github.greymagic27.win_method.WinDef;

import io.github.greymagic27.win_method.Pointer;
import java.lang.foreign.MemorySegment;
import org.jspecify.annotations.NonNull;

/// A {@link Pointer} to a constant of any type
public class LPCVOID extends Pointer {
    /// Creates a pointer from a memory segment
    ///
    /// @param segment The memory segment to wrap
    public LPCVOID(MemorySegment segment) {
        super(segment);
    }

    /// Creates a pointer from an existing {@link Pointer}
    ///
    /// @param pointer The pointer to wrap
    public LPCVOID(@NonNull Pointer pointer) {
        super(pointer.segment);
    }
}
