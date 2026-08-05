package io.github.greymagic27.win_method.WinDef;

import io.github.greymagic27.win_method.WinNT.HANDLE;
import java.lang.foreign.MemorySegment;

/// A {@link HANDLE} to a device context
public class HDC extends HANDLE {

    /// Creates an {@code HDC} from a memory segment
    ///
    /// @param segment The memory segment containing the device context handle
    public HDC(MemorySegment segment) {
        super(segment);
    }
}
