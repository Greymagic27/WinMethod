package io.github.greymagic27.jna_clone;

import java.lang.foreign.MemorySegment;

public class Handle {

    public static final Handle NULL = new Handle(MemorySegment.NULL);
    private final MemorySegment segment;

    public Handle(MemorySegment segment) {
        this.segment = segment;
    }

    public MemorySegment segment() {
        return segment;
    }

    public boolean isNull() {
        return segment.address() == 0;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "@0x" + Long.toHexString(segment.address());
    }
}
