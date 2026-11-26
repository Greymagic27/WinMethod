package io.github.greymagic27.win_method.platform;

import io.github.greymagic27.win_method.IntSafe.DWORD;
import io.github.greymagic27.win_method.Memory;
import io.github.greymagic27.win_method.WinDef.COLORREF;
import io.github.greymagic27.win_method.WinDef.HDC;
import io.github.greymagic27.win_method.WinDef.HFONT;
import io.github.greymagic27.win_method.WinDef.HGDIOBJ;
import io.github.greymagic27.win_method.WinDef.HWND;
import io.github.greymagic27.win_method.WinDef.LPVOID;
import io.github.greymagic27.win_method.WinDef.UINT;
import io.github.greymagic27.win_method.WinDef.WORD;
import io.github.greymagic27.win_method.WinNT.LONG;
import io.github.greymagic27.win_method.WinNT.LPCWSTR;
import io.github.greymagic27.win_method.methods.CreateWindow;
import io.github.greymagic27.win_method.methods.Font;
import io.github.greymagic27.win_method.types.WinGdi;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GDI32Test {

    private static final GDI32 gdi32 = GDI32.INSTANCE;
    private static final User32 user32 = User32.INSTANCE;
    private HWND window;
    private HFONT hfont;
    private HDC hdc;

    @BeforeEach
    void setUp() {
        window = CreateWindow.createStaticWindow(null, null, 0, 0, 0, 800, 600);
        hfont = Font.create("Arial", 16);
        hdc = user32.GetDC(window);
    }

    @AfterEach
    void tearDown() {
        user32.ReleaseDC(window, hdc);
        user32.DestroyWindow(window);
        gdi32.DeleteObject(new HGDIOBJ(hfont));
    }

    @Test
    void testGetStockObject() {
        HGDIOBJ hgdiobj = gdi32.GetStockObject(WinGdi.WHITE_BRUSH);
        assertNotNull(hgdiobj);
        assertNotEquals(0, hgdiobj.segment.address());
    }

    @Test
    void testStretchDIBits() {
        int width = 2;
        int height = 2;
        try (Memory pixels = new Memory(width * height * 4)) {
            for (int i = 0; i < width * height; i++) pixels.segment.set(ValueLayout.JAVA_INT, i * 4, 0xFFFF0000);
            WinGdi.BITMAPINFO bmi = new WinGdi.BITMAPINFO();
            bmi.bmiHeader.biSize = new DWORD(bmi.size());
            bmi.bmiHeader.biWidth = new LONG(width);
            bmi.bmiHeader.biHeight = new LONG(height);
            bmi.bmiHeader.biPlanes = new WORD((short) 1);
            bmi.bmiHeader.biBitCount = new WORD((short) 32);
            bmi.bmiHeader.biCompression = new DWORD(0);
            bmi.bmiHeader.biSizeImage = new DWORD(0);
            int result = gdi32.StretchDIBits(hdc, 0, 0, width, height, 0, 0, width, height, new LPVOID(pixels), bmi, new UINT(WinGdi.DIB_RGB_COLORS), new DWORD(WinGdi.SRCCOPY));
            assertTrue(result != 0);
        }
    }

    @Test
    void testCreateFont() {
        HFONT font = gdi32.CreateFontW(-16, 0, 0, 0, WinGdi.FW_NORMAL, new DWORD(0), new DWORD(0), new DWORD(0), new DWORD(WinGdi.ANSI_CHARSET), new DWORD(WinGdi.OUT_DEFAULT_PRECIS), new DWORD(WinGdi.CLIP_DEFAULT_PRECIS), new DWORD(WinGdi.CLEARTYPE_QUALITY), new DWORD(0), new LPCWSTR("Arial"));
        assertNotNull(font);
        assertNotEquals(0, font.segment.address());
        assertFalse(font.isNull());
        assertTrue(gdi32.DeleteObject(new HGDIOBJ(font)).booleanValue());
    }

    @Test
    void testDeleteObject() {
        assertNotNull(hfont);
        assertNotEquals(0, hfont.segment.address());
        assertFalse(hfont.isNull());
        HGDIOBJ hgdiobj = new HGDIOBJ(hfont);
        assertTrue(gdi32.DeleteObject(hgdiobj).booleanValue());
        HGDIOBJ invalidObject = new HGDIOBJ(MemorySegment.ofAddress(0));
        assertFalse(gdi32.DeleteObject(invalidObject).booleanValue());
    }

    @Test
    void testSelectObject() {
        HGDIOBJ previousFont = gdi32.SelectObject(hdc, new HGDIOBJ(hfont));
        assertNotNull(previousFont);
        assertFalse(previousFont.isNull());
        HGDIOBJ restoredFont = gdi32.SelectObject(hdc, previousFont);
        assertNotNull(restoredFont);
        assertFalse(restoredFont.isNull());
        assertEquals(hfont.segment.address(), restoredFont.segment.address());
    }

    @Test
    void testTextOut() {
        gdi32.SelectObject(hdc, new HGDIOBJ(hfont));
        assertTrue(gdi32.SetTextColor(hdc, new COLORREF(0x000000FF)).intValue() != 0xFFFFFFFF);
        String txt = "test";
        assertTrue(gdi32.TextOutW(hdc, 10, 10, new LPCWSTR(txt), txt.length()).booleanValue());
    }

    @Test
    void testSetTextColor() {
        COLORREF originalColor = gdi32.SetTextColor(hdc, new COLORREF(0x000000FF));
        assertNotNull(originalColor);
        assertNotEquals(0xFFFFFFFF, originalColor.intValue());
        COLORREF previousColor = gdi32.SetTextColor(hdc, new COLORREF(0x0000FF00));
        assertEquals(0x000000FF, previousColor.intValue());
        gdi32.SetTextColor(hdc, originalColor);
    }

    @Test
    void testSetBkMode() {
        int originalMode = gdi32.SetBkMode(hdc, WinGdi.TRANSPARENT);
        assertEquals(WinGdi.OPAQUE, originalMode);
        int previousMode = gdi32.SetBkMode(hdc, WinGdi.OPAQUE);
        assertEquals(WinGdi.TRANSPARENT, previousMode);
        gdi32.SetBkMode(hdc, originalMode);
    }
}