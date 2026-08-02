package io.github.greymagic27.win_method.platform;

import io.github.greymagic27.win_method.IntSafe.DWORD;
import io.github.greymagic27.win_method.WinDef.BYTE;
import io.github.greymagic27.win_method.WinDef.WORD;
import io.github.greymagic27.win_method.WinNT.LONG;
import org.junit.jupiter.api.Test;

import static io.github.greymagic27.win_method.platform.WinGdi.DIB_RGB_COLORS;
import static io.github.greymagic27.win_method.platform.WinGdi.SRCCOPY;
import static io.github.greymagic27.win_method.platform.WinGdi.WHITE_BRUSH;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class WinGdiTest {

    @Test
    void testValues() {
        assertEquals(0, WHITE_BRUSH);
        assertEquals(0, DIB_RGB_COLORS);
        assertEquals(0xcc0020, SRCCOPY);
    }

    @Test
    void testBitmapInfo() {
        WinGdi.BITMAPINFO info = new WinGdi.BITMAPINFO();
        assertNotNull(info.bmiHeader);
        assertNotNull(info.bmiColors);
    }

    @Test
    void testBitmapInfoHeader() {
        WinGdi.BITMAPINFOHEADER header = new WinGdi.BITMAPINFOHEADER();
        assertEquals(new DWORD(0).intValue(), header.biSize.intValue());
        assertEquals(0, header.biWidth.intValue());
        assertEquals(0, header.biHeight.intValue());
        assertEquals(new WORD((short) 0).shortValue(), header.biPlanes.shortValue());
        assertEquals(new WORD((short) 0).shortValue(), header.biBitCount.shortValue());
        assertEquals(new DWORD(0).intValue(), header.biCompression.intValue());
        assertEquals(new DWORD(0).intValue(), header.biSizeImage.intValue());
        assertEquals(new LONG(0).intValue(), header.biXPelsPerMeter.intValue());
        assertEquals(new LONG(0).intValue(), header.biYPelsPerMeter.intValue());
        assertEquals(new DWORD(0).intValue(), header.biClrUsed.intValue());
        assertEquals(new DWORD(0).intValue(), header.biClrImportant.intValue());
        header.biWidth = new LONG(1920);
        header.biHeight = new LONG(1080);
        header.biPlanes = new WORD((short) 1);
        header.biBitCount = new WORD((short) 32);
        header.biCompression = new DWORD(5);
        header.biSizeImage = new DWORD(20);
        header.biXPelsPerMeter = new LONG(15);
        header.biYPelsPerMeter = new LONG(25);
        header.biClrUsed = new DWORD(3);
        header.biClrImportant = new DWORD(50);
        assertEquals(1920, header.biWidth.intValue());
        assertEquals(1080, header.biHeight.intValue());
        assertEquals(1, header.biPlanes.shortValue());
        assertEquals(32, header.biBitCount.shortValue());
        assertEquals(5, header.biCompression.intValue());
        assertEquals(20, header.biSizeImage.intValue());
        assertEquals(15, header.biXPelsPerMeter.intValue());
        assertEquals(25, header.biYPelsPerMeter.intValue());
        assertEquals(3, header.biClrUsed.intValue());
        assertEquals(50, header.biClrImportant.intValue());
    }

    @Test
    void testRgbQuad() {
        WinGdi.RGBQUAD rgb = new WinGdi.RGBQUAD();
        assertEquals(new BYTE((byte) 0).byteValue(), rgb.rgbBlue.byteValue());
        assertEquals(new BYTE((byte) 0).byteValue(), rgb.rgbGreen.byteValue());
        assertEquals(new BYTE((byte) 0).byteValue(), rgb.rgbRed.byteValue());
        assertEquals(new BYTE((byte) 0).byteValue(), rgb.rgbReserved.byteValue());
        rgb.rgbBlue = new BYTE((byte) 10);
        rgb.rgbGreen = new BYTE((byte) 20);
        rgb.rgbRed = new BYTE((byte) 30);
        rgb.rgbReserved = new BYTE((byte) 40);
        assertEquals(10, rgb.rgbBlue.byteValue());
        assertEquals(20, rgb.rgbGreen.byteValue());
        assertEquals(30, rgb.rgbRed.byteValue());
        assertEquals(40, rgb.rgbReserved.byteValue());
    }
}