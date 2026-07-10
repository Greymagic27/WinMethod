package io.github.greymagic27.jna_clone.WinDef;

import io.github.greymagic27.jna_clone.Pointer;
import java.lang.foreign.MemorySegment;
import org.jspecify.annotations.NonNull;

public class LPVOID extends Pointer {

    public LPVOID(MemorySegment segment) {
        super(segment);
    }

    public LPVOID(@NonNull Pointer pointer) {
        super(pointer.segment);
    }
}
