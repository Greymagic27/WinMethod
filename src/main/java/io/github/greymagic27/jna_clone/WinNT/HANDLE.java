package io.github.greymagic27.jna_clone.WinNT;

import io.github.greymagic27.jna_clone.Pointer;
import java.lang.foreign.MemorySegment;
import org.jspecify.annotations.NonNull;

public class HANDLE extends Pointer {

    public HANDLE(MemorySegment segment) {
        super(segment);
    }

    public HANDLE(long address) {
        this(MemorySegment.ofAddress(address));
    }

    @Override
    public @NonNull String toString() {
        return getClass().getSimpleName() + "@0x" + Long.toHexString(segment.address());
    }
}
