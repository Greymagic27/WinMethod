package io.github.greymagic27.jna_clone;

import java.lang.foreign.MemorySegment;

public class HANDLE {

    public static final HANDLE NULL = new HANDLE(MemorySegment.NULL);
    private final MemorySegment segment;

    public HANDLE(MemorySegment segment) {
        this.segment = segment;
    }

    public HANDLE(long address) {
        this(MemorySegment.ofAddress(address));
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
