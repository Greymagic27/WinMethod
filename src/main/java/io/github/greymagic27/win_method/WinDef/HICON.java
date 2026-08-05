package io.github.greymagic27.win_method.WinDef;

import io.github.greymagic27.win_method.WinNT.HANDLE;
import java.lang.foreign.MemorySegment;

/// A {@link HANDLE} to an icon
public class HICON extends HANDLE {

    /// Creates an {@code HICON} from a memory segment
    ///
    /// @param segment The memory segment containing the icon handle
    public HICON(MemorySegment segment) {
        super(segment);
    }
}
