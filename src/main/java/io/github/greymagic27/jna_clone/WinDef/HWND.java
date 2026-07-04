package io.github.greymagic27.jna_clone.WinDef;

import io.github.greymagic27.jna_clone.Handle;
import java.lang.foreign.MemorySegment;

public class HWND extends Handle {

    public static final HWND NULL = new HWND(MemorySegment.NULL);

    public HWND(MemorySegment segment) {
        super(segment);
    }
}
