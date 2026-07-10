package io.github.greymagic27.jna_clone.platform;

import io.github.greymagic27.jna_clone.Structure;
import io.github.greymagic27.jna_clone.WinDef.LONG;

public interface WinDef {

    @Structure.FieldOrder({"x", "y"})
    class POINT extends Structure {
        public LONG x;
        public LONG y;
    }

    @Structure.FieldOrder({"left", "top", "right", "bottom"})
    class RECT extends Structure {
        public LONG left;
        public LONG top;
        public LONG right;
        public LONG bottom;
    }
}
