package io.github.greymagic27.win_method.WinDef;

import io.github.greymagic27.win_method.WinNT.HANDLE;
import java.lang.foreign.MemorySegment;

/// A {@link HANDLE} to a window
public class HWND extends HANDLE {

    /// Creates an {@code HWND} from a memory segment
    ///
    /// @param segment The memory segment containing the window handle
    public HWND(MemorySegment segment) {
        super(segment);
    }

    /// Creates an {@code HWND} from a memory address
    ///
    /// @param address The native memory address of the window handle
    public HWND(long address) {
        super(address);
    }
}
