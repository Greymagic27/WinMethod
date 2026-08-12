package io.github.greymagic27.win_method.WinDef;

import io.github.greymagic27.win_method.Pointer;
import io.github.greymagic27.win_method.WinNT.HANDLE;
import java.lang.foreign.MemorySegment;
import org.jspecify.annotations.NonNull;

/// A {@link HANDLE} to a [font](https://learn.microsoft.com/en-us/windows/desktop/gdi/about-fonts)
public class HFONT extends HANDLE {

    /// Creates an {@code HFONT} from a memory segment
    ///
    /// @param segment The memory segment containing the device context handle
    public HFONT(MemorySegment segment) {
        super(segment);
    }

    /// Creates a pointer from an existing {@link Pointer}
    ///
    /// @param pointer The pointer to wrap
    public HFONT(@NonNull Pointer pointer) {
        super(pointer.segment);
    }
}
