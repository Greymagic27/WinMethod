package io.github.greymagic27.jna_clone;

import java.lang.foreign.MemorySegment;

public class HANDLE {

    private final MemorySegment segment;

    public HANDLE(MemorySegment segment) {
        this.segment = segment;
    }

    public MemorySegment segment() {
        return segment;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "@0x" + Long.toHexString(segment.address());
    }
}
