package io.github.greymagic27.win_method.WinDef;

import io.github.greymagic27.win_method.Pointer;
import io.github.greymagic27.win_method.WinNT.HANDLE;
import java.lang.foreign.MemorySegment;
import org.jspecify.annotations.NonNull;

/// A {@link HANDLE} to a brush
public class HBRUSH extends HANDLE {

    /// Creates an {@code HBRUSH} from a memory segment
    ///
    /// @param segment The memory segment containing the brush handle
    public HBRUSH(MemorySegment segment) {
        super(segment);
    }

    /// Creates an {@code HBRUSH} from an existing {@link Pointer}
    ///
    /// @param pointer The pointer to wrap
    public HBRUSH(@NonNull Pointer pointer) {
        super(pointer.segment);
    }
}
