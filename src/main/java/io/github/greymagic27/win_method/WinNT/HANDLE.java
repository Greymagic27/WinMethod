package io.github.greymagic27.win_method.WinNT;

import io.github.greymagic27.win_method.Pointer;
import io.github.greymagic27.win_method.WinDef.LPVOID;
import java.lang.foreign.MemorySegment;
import org.jspecify.annotations.NonNull;

/// A handle to an object
public class HANDLE extends LPVOID {

    /// Creates a handle from a memory segment
    ///
    /// @param segment The memory segment containing the handle value
    public HANDLE(MemorySegment segment) {
        super(segment);
    }

    /// Creates a handle from a pointer
    ///
    /// @param pointer The pointer containing the handle value
    public HANDLE(@NonNull Pointer pointer) {
        super(pointer.segment);
    }

    /// Creates a handle from a memory address
    ///
    /// @param address The native memory address of the handle
    public HANDLE(long address) {
        super(MemorySegment.ofAddress(address));
    }

    /// Returns the class and segment address as a string
    @Override
    public @NonNull String toString() {
        return getClass().getSimpleName() + "@0x" + Long.toHexString(segment.address());
    }
}
