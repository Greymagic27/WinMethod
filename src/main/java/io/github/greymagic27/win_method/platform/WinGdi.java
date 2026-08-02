package io.github.greymagic27.win_method.platform;

import io.github.greymagic27.win_method.IntSafe.DWORD;
import io.github.greymagic27.win_method.Structure;
import io.github.greymagic27.win_method.WinDef.BYTE;
import io.github.greymagic27.win_method.WinDef.WORD;
import io.github.greymagic27.win_method.WinNT.LONG;

/// Values defined in wingdi.h
public interface WinGdi {

    /// White brush stock object
    int WHITE_BRUSH = 0;
    /// A colour table is provided and contains literal RGB values
    int DIB_RGB_COLORS = 0;
    /// Copies the source rectangle directly to the destination rectangle
    int SRCCOPY = 0xcc0020;

    /// Defines the dimensions and colour information for a DIB
    @Structure.AutoFieldOrder
    class BITMAPINFO extends Structure {
        /// A {@link BITMAPINFOHEADER} structure that contains information about the dimensions of colour format
        public BITMAPINFOHEADER bmiHeader;
        /// An array of {@link RGBQUAD} that make up the colour table, or an array of 16-bit unsigned integers that specifies indexes into the current realised logical palette
        @ArrayLength(1)
        public RGBQUAD[] bmiColors;
    }

    /// Contains information about the dimension and colour format of the device-independent bitmap (DIB)
    @Structure.AutoFieldOrder
    class BITMAPINFOHEADER extends Structure {
        /// Specifies the number of bytes required by the structure. This does not include the size of the colour table or the size of the colour masks
        public DWORD biSize;
        /// Specifies the width of the bitmap, in pixels
        public LONG biWidth;
        /// Specifies the height of the bitmap, in pixels
        public LONG biHeight;
        /// Specifies the number of planes for the target device. This value must be 1
        public WORD biPlanes;
        ///  Specifies the number of bits per pixel (bpp). For uncompressed formats, this value is the average number of bits per pixel. For compressed formats, this value is the implied bit depth of the uncompressed image, after the image has been decoded
        public WORD biBitCount;
        /// The compression value. See [here](https://learn.microsoft.com/en-us/windows/win32/api/wingdi/ns-wingdi-bitmapinfoheader) for the allowed values
        public DWORD biCompression;
        /// Specifies the size, in bytes, of the image. This can be 0 for uncompressed RGB bitmaps
        public DWORD biSizeImage;
        /// Specifies the horizontal resolution, in pixels per meter, of the target device for the bitmap
        public LONG biXPelsPerMeter;
        /// Specifies the vertical resolution, in pixels per meter, of the target device for the bitmap
        public LONG biYPelsPerMeter;
        /// Specifies the number of colour indices in the colour table that are actually used by the bitmap
        public DWORD biClrUsed;
        /// Specifies the number of colour indices that are considered important for displaying the bitmap. If this value is zero, all colours are important
        public DWORD biClrImportant;
    }

    /// Describes a colour consisting of relative intensities of red, green and blue
    @Structure.AutoFieldOrder
    class RGBQUAD extends Structure {
        /// The intensity of blue in the colour
        public BYTE rgbBlue;
        /// The intensity of green in the colour
        public BYTE rgbGreen;
        /// The intensity of red in the colour
        public BYTE rgbRed;
        /// This member is reserved and must be 0
        public BYTE rgbReserved;
    }
}
