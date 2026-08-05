package io.github.greymagic27.win_method.methods;

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
        BitmapInfoHeader.setBitmapInfoHeader(bitmapinfo, 1920, 1080, 1, 32, 0, 1920 * 1080 * 4, 3780, 3780, 0, 0);
        BITMAPINFOHEADER header = bitmapinfo.bmiHeader;
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
        BitmapInfoHeader.setBitmapInfoHeader(bitmapinfo, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        BITMAPINFOHEADER header = bitmapinfo.bmiHeader;
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