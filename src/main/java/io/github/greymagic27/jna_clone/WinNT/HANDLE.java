package io.github.greymagic27.jna_clone.WinNT;

import io.github.greymagic27.jna_clone.WinDef.LPVOID;
import java.lang.foreign.MemorySegment;
import org.jspecify.annotations.NonNull;

/**
 * A handle to an object
 */
public class HANDLE extends LPVOID {

    public HANDLE(MemorySegment segment) {
        super(segment);
    }

    public HANDLE(long address) {
        this(MemorySegment.ofAddress(address));
    }

    /**
     * @return Returns the class and segment address as a string
     */
    @Override
    public @NonNull String toString() {
        return getClass().getSimpleName() + "@0x" + Long.toHexString(segment.address());
    }
}
