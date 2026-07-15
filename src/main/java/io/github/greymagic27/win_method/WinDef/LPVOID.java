package io.github.greymagic27.win_method.WinDef;

import io.github.greymagic27.win_method.Pointer;
import java.lang.foreign.MemorySegment;
import org.jspecify.annotations.NonNull;

/**
 * A {@link Pointer} to any type
 */
public class LPVOID extends Pointer {

    public LPVOID(MemorySegment segment) {
        super(segment);
    }

    public LPVOID(@NonNull Pointer pointer) {
        super(pointer.segment);
    }
}
