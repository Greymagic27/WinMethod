package io.github.greymagic27.jna_clone.platform;

import io.github.greymagic27.jna_clone.Structure;
import io.github.greymagic27.jna_clone.WinDef.LONG;

/**
 * Values defined in WinDef.h
 */
public interface WinDef {

    /**
     * Defines the x and y coordinates of a point
     */
    @Structure.FieldOrder({"x", "y"})
    class POINT extends Structure {
        /**
         * Specifies the x-coordinate of the point
         */
        public LONG x;
        /**
         * Specifies the y-coordinate of the point
         */
        public LONG y;
    }

    /**
     * Defines a rectangle by the coordinates of its upper-left and lower-right corners
     */
    @Structure.FieldOrder({"left", "top", "right", "bottom"})
    class RECT extends Structure {
        /**
         * Specifies the x-coordinate of the upper-left corner of the rectangle
         */
        public LONG left;
        /**
         * Specifies the y-coordinate of the upper-left corner of the rectangle
         */
        public LONG top;
        /**
         * Specifies the x-coordinate of the lower-right corner of the rectangle
         */
        public LONG right;
        /**
         * Specifies the y-coordinate of the lower-right corner of the rectangle
         */
        public LONG bottom;
    }
}
