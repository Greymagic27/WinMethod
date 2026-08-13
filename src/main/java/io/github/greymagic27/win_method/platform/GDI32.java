package io.github.greymagic27.win_method.platform;

import io.github.greymagic27.win_method.IntSafe.DWORD;
import io.github.greymagic27.win_method.Library;
import io.github.greymagic27.win_method.WinDef.BOOL;
import io.github.greymagic27.win_method.WinDef.HDC;
import io.github.greymagic27.win_method.WinDef.HFONT;
import io.github.greymagic27.win_method.WinDef.HGDIOBJ;
import io.github.greymagic27.win_method.WinDef.LPVOID;
import io.github.greymagic27.win_method.WinDef.UINT;
import io.github.greymagic27.win_method.WinNT.LPCWSTR;
import io.github.greymagic27.win_method.types.WinGdi;

/// Interface for Gdi32.dll
public interface GDI32 extends Library {
    /// The instance
    GDI32 INSTANCE = Library.load(GDI32.class);

    /// Copies the colour data for a rectangle of pixels in a DIB, JPEG or PNG image to the specified destination rectangle
    ///
    /// @param hdc        A {@link io.github.greymagic27.win_method.WinNT.HANDLE} to the destination device context
    /// @param xDest      The x-coordinate, in logical units, of the upper-left corner of the destination rectangle
    /// @param yDest      The y-coordinate, in logical units, of the upper-left corner of the destination rectangle
    /// @param DestWidth  The width, in logical units, of the destination rectangle
    /// @param DestHeight The height, in logical units, of the destination rectangle
    /// @param xSrc       The x-coordinate, in pixels, of the source rectangle in the image
    /// @param ySrc       The y-coordinate, in pixels, of the source rectangle in the image
    /// @param SrcWidth   The width, in pixels, of the source rectangle in the image
    /// @param SrcHeight  The height, in pixels, of the source rectangle in the image
    /// @param lpBits     A {@link io.github.greymagic27.win_method.Pointer} to the image bits, which are stored as an array of bytes
    /// @param lpbmi      A {@link io.github.greymagic27.win_method.Pointer} to a {@link WinGdi.BITMAPINFO} structure that contains information about the DIB
    /// @param iUsage     Specifies whether the bmiColors member of the BITMAPINFO structure was provided and, if so, whether bmiColors contains explicit red, green, blue (RGB) values or indexes. This must be [one of the following values](https://learn.microsoft.com/en-us/windows/win32/api/wingdi/nf-wingdi-stretchdibits)
    /// @param rop        A raster-operation code that specifies how the source pixels, the destination device context's current brush, and the destination pixels are to be combined to form the new image
    /// @return If the function succeeds, the return value is the number of scan lines copied. If the function fails, or no scan lines are copied, the return value is 0
    int StretchDIBits(HDC hdc, int xDest, int yDest, int DestWidth, int DestHeight, int xSrc, int ySrc, int SrcWidth, int SrcHeight, LPVOID lpBits, WinGdi.BITMAPINFO lpbmi, UINT iUsage, DWORD rop);

    /// Retrieves a {@link io.github.greymagic27.win_method.WinNT.HANDLE} to one of the stock pens, brushes, fonts or palettes.
    ///
    /// @param i The type of stock object. This can be [one of the following values](https://learn.microsoft.com/en-us/windows/win32/api/wingdi/nf-wingdi-getstockobject)
    /// @return If the function succeeds, the return value is a {@link io.github.greymagic27.win_method.WinNT.HANDLE} to the requested logical object. If the function fails, the return type is NULL
    HGDIOBJ GetStockObject(int i);

    /// Creates a logical font with the specified characteristics
    ///
    /// @param cHeight         The height, in logical units, of the font's character cell or character. If zero, a default height value is used
    /// @param cWidth          The average width, in logical units, of characters in the requested font. If zero, the closest match value is chosen
    /// @param cEscapement     The angle, in tenths of degrees, between the escapement vector and the x-axis of the device
    /// @param cOrientation    The angle, in tenths of degrees, between each character's baseline and the x-axis of the device
    /// @param cWeight         The weight of the font in the range 0 through 1000. 400 weight is normal, 700 weight is bold. See [this table](https://learn.microsoft.com/en-us/windows/win32/api/wingdi/nf-wingdi-createfontw) for more values
    /// @param bItalic         Specifies an italic font if set to TRUE
    /// @param bUnderline      Specifies an underlined font if set to TRUE
    /// @param bStrikeOut      A strikeout font if set to TRUE
    /// @param iCharSet        The character set
    /// @param iOutPrecision   The output precision, defining how closely the output must match the requested font's height, weight, character orientation, escapement, pitch and font type. It can be one of [the following values](https://learn.microsoft.com/en-us/windows/win32/api/wingdi/nf-wingdi-createfontw)
    /// @param iClipPrecision  The clipping precision, defining how to clip characters that are partially outside the clipping region. It can be one of [the following values](https://learn.microsoft.com/en-us/windows/win32/api/wingdi/nf-wingdi-createfontw)
    /// @param iQuality        The output quality, defining how carefully GDI must attempt to match the logical-font attributes to those of an actual physical font. It can be one of [the following values](https://learn.microsoft.com/en-us/windows/win32/api/wingdi/nf-wingdi-createfontw)
    /// @param iPitchAndFamily The pitch and family of the font. This can be one of [the following values](https://learn.microsoft.com/en-us/windows/win32/api/wingdi/nf-wingdi-createfontw)
    /// @param pszFaceName     A {@link io.github.greymagic27.win_method.Pointer} to a null-terminated string that specifies the typeface name of the font. This must not exceed 32 characters, including the terminating null character
    /// @return If the function succeeds, the return value is a handle to a logical font. If the function fails, it returns NULL
    HFONT CreateFontW(int cHeight, int cWidth, int cEscapement, int cOrientation, int cWeight, DWORD bItalic, DWORD bUnderline, DWORD bStrikeOut, DWORD iCharSet, DWORD iOutPrecision, DWORD iClipPrecision, DWORD iQuality, DWORD iPitchAndFamily, LPCWSTR pszFaceName);

    /// Deletes a logical pen, brush, font, bitmap, region, or palette, freeing all system resources associated with the object
    ///
    /// @param ho A {@link io.github.greymagic27.win_method.WinNT.HANDLE} to a logical pen, brush, font, bitmap, region or palette
    /// @return If the function succeeds, the return value is nonzero. If the specified handle is not valid or is currently selected into a DC, the return value is zero
    BOOL DeleteObject(HGDIOBJ ho);
}
