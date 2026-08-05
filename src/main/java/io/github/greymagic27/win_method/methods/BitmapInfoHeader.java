package io.github.greymagic27.win_method.methods;

import io.github.greymagic27.win_method.IntSafe.DWORD;
import io.github.greymagic27.win_method.WinDef.WORD;
import io.github.greymagic27.win_method.WinNT.LONG;
import io.github.greymagic27.win_method.types.WinGdi.BITMAPINFO;
import io.github.greymagic27.win_method.types.WinGdi.BITMAPINFOHEADER;
import org.jspecify.annotations.NonNull;

/// Helper to create a bitmapinfoheader more easily
public class BitmapInfoHeader {

    /// Creates a {@link BITMAPINFOHEADER} that contains information about the dimension and colour format of a device-independent bitmap (DIB)
    ///
    /// @param bitmapInfo    The bitmap information structure to configure
    /// @param width         Specifies the width of the bitmap, in pixels
    /// @param height        Specifies the height of the bitmap, in pixels
    /// @param planes        Specifies the number of planes for the target device. This value must be 1
    /// @param bitCount      Specifies the number of bits per pixel (bpp). For uncompressed formats, this value is the average number of bits per pixel. For compressed formats, this value is the implied bit depth of the uncompressed image, after the image has been decoded
    /// @param compression   The compression value. See [here](https://learn.microsoft.com/en-us/windows/win32/api/wingdi/ns-wingdi-bitmapinfoheader) for the allowed values
    /// @param sizeImage     Specifies the size, in bytes, of the image. This can be 0 for uncompressed RGB bitmaps
    /// @param xPelsPerMeter Specifies the horizontal resolution, in pixels per meter, of the target device for the bitmap
    /// @param yPelsPerMeter Specifies the vertical resolution, in pixels per meter, of the target device for the bitmap
    /// @param clrUsed       Specifies the number of colour indices in the colour table that are actually used by the bitmap
    /// @param clrImportant  Specifies the number of colour indices that are considered important for displaying the bitmap. If this value is zero, all colours are important
    public static void setBitmapInfoHeader(@NonNull BITMAPINFO bitmapInfo, int width, int height, int planes, int bitCount, int compression, int sizeImage, int xPelsPerMeter, int yPelsPerMeter, int clrUsed, int clrImportant) {
        BITMAPINFOHEADER header = bitmapInfo.bmiHeader;
        header.biSize = new DWORD(header.size());
        header.biWidth = new LONG(width);
        header.biHeight = new LONG(height);
        header.biPlanes = new WORD((short) planes);
        header.biBitCount = new WORD((short) bitCount);
        header.biCompression = new DWORD(compression);
        header.biSizeImage = new DWORD(sizeImage);
        header.biXPelsPerMeter = new LONG(xPelsPerMeter);
        header.biYPelsPerMeter = new LONG(yPelsPerMeter);
        header.biClrUsed = new DWORD(clrUsed);
        header.biClrImportant = new DWORD(clrImportant);
    }
}