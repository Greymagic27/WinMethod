package io.github.greymagic27.jna_clone;

import java.lang.foreign.MemorySegment;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class HANDLE extends Pointer
{
    @Contract(pure = true)
    public HANDLE(MemorySegment segment) {
        super(segment);
    }

    public HANDLE(long address) {
        this(MemorySegment.ofAddress(address));
    }

    @Override
    public @NotNull String toString() {
        return getClass().getSimpleName() + "@0x" + Long.toHexString(segment.address());
    }
}
