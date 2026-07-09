package io.github.greymagic27.jna_clone.platform;

import io.github.greymagic27.jna_clone.Structure;
import io.github.greymagic27.jna_clone.WinDef.LONG;

public interface WinDef {

    @Structure.FieldOrder({"x", "y"})
    class POINT extends Structure {
        LONG x;
        LONG y;
    }
}
