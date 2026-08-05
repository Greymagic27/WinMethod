package io.github.greymagic27.win_method.WinDef;

import io.github.greymagic27.win_method.WinNT.HANDLE;
import java.lang.foreign.MemorySegment;

/// A {@link HANDLE} to a GDI object
public class HGDIOBJ extends HANDLE {

    /// Creates an {@code HGDIOBJ} from a memory segment
    ///
    /// @param segment The memory segment containing the GDI object handle
    public HGDIOBJ(MemorySegment segment) {
        super(segment);
    }
}
