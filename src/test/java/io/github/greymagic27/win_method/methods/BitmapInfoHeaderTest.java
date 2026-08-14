package io.github.greymagic27.win_method.methods;

import io.github.greymagic27.win_method.IntSafe.DWORD;
import io.github.greymagic27.win_method.WinDef.WORD;
import io.github.greymagic27.win_method.WinNT.LONG;
import io.github.greymagic27.win_method.types.WinGdi;
import io.github.greymagic27.win_method.types.WinGdi.BITMAPINFOHEADER;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class BitmapInfoHeaderTest {

    private WinGdi.BITMAPINFO bitmapinfo;

    @BeforeEach
    void setUp() {
        bitmapinfo = new WinGdi.BITMAPINFO();
    }

    @Test
    void testSetBitmapInfoHeader() {
        BITMAPINFOHEADER header = bitmapinfo.bmiHeader;
        header.biSize = new DWORD(header.size());
        header.biWidth = new LONG(1920);
        header.biHeight = new LONG(1080);
        header.biPlanes = new WORD((short) 1);
        header.biBitCount = new WORD((short) 32);
        header.biCompression = new DWORD(0);
        header.biSizeImage = new DWORD(1920 * 1080 * 4);
        header.biXPelsPerMeter = new LONG(3780);
        header.biYPelsPerMeter = new LONG(3780);
        header.biClrUsed = new DWORD(0);
        header.biClrImportant = new DWORD(0);
        assertNotNull(header);
        assertEquals(40, header.biSize.intValue());
        assertEquals(1920, header.biWidth.intValue());
        assertEquals(1080, header.biHeight.intValue());
        assertEquals(1, header.biPlanes.shortValue());
        assertEquals(32, header.biBitCount.shortValue());
        assertEquals(0, header.biCompression.intValue());
        assertEquals(1920 * 1080 * 4, header.biSizeImage.intValue());
        assertEquals(3780, header.biXPelsPerMeter.intValue());
        assertEquals(3780, header.biYPelsPerMeter.intValue());
        assertEquals(0, header.biClrUsed.intValue());
        assertEquals(0, header.biClrImportant.intValue());
    }

    @Test
    void testSetBitmapInfoHeaderWithZeroValues() {
        BITMAPINFOHEADER header = bitmapinfo.bmiHeader;
        header.biSize = new DWORD(header.size());
        header.biWidth = new LONG(0);
        header.biHeight = new LONG(0);
        header.biPlanes = new WORD((short) 0);
        header.biBitCount = new WORD((short) 0);
        header.biCompression = new DWORD(0);
        header.biSizeImage = new DWORD(0);
        header.biXPelsPerMeter = new LONG(0);
        header.biYPelsPerMeter = new LONG(0);
        header.biClrUsed = new DWORD(0);
        header.biClrImportant = new DWORD(0);
        assertNotNull(header);
        assertEquals(40, header.biSize.intValue());
        assertEquals(0, header.biWidth.intValue());
        assertEquals(0, header.biHeight.intValue());
        assertEquals(0, header.biPlanes.shortValue());
        assertEquals(0, header.biBitCount.shortValue());
        assertEquals(0, header.biCompression.intValue());
        assertEquals(0, header.biSizeImage.intValue());
        assertEquals(0, header.biXPelsPerMeter.intValue());
        assertEquals(0, header.biYPelsPerMeter.intValue());
        assertEquals(0, header.biClrUsed.intValue());
        assertEquals(0, header.biClrImportant.intValue());
    }
}