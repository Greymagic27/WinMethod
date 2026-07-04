package io.github.greymagic27.jna_clone.WinDef;

import io.github.greymagic27.jna_clone.Handle;
import java.lang.foreign.MemorySegment;

public class HDC extends Handle {

    public static final HDC NULL = new HDC(MemorySegment.NULL);

    public HDC(MemorySegment segment) {
        super(segment);
    }
}
