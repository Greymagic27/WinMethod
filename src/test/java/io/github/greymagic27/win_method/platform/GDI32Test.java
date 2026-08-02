package io.github.greymagic27.win_method.platform;

import io.github.greymagic27.win_method.IntSafe.DWORD;
import io.github.greymagic27.win_method.WinDef.HDC;
import io.github.greymagic27.win_method.WinDef.HGDIOBJ;
import io.github.greymagic27.win_method.WinDef.HWND;
import io.github.greymagic27.win_method.WinDef.LPVOID;
import io.github.greymagic27.win_method.WinDef.UINT;
import io.github.greymagic27.win_method.WinDef.WORD;
import io.github.greymagic27.win_method.WinNT.LONG;
import io.github.greymagic27.win_method.WinNT.LPCWSTR;
import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GDI32Test {

    private static final GDI32 gdi32 = GDI32.INSTANCE;
    private static final User32 user32 = User32.INSTANCE;
    private HWND window;
    private HDC hdc;

    @BeforeEach
    void setUp() {
        window = user32.CreateWindowExW(new DWORD(0), new LPCWSTR("STATIC"), null, new DWORD(WinUser.WS_OVERLAPPED), 100, 100, 500, 400, null, null, null, null);
        hdc = user32.GetDC(window);
    }

    @AfterEach
    void tearDown() {
        user32.ReleaseDC(window, hdc);
        user32.DestroyWindow(window);
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
        try (Arena arena = Arena.ofConfined()) {
            MemorySegment pixels = arena.allocate(width * height * 4);
            for (int i = 0; i < width * height; i++) pixels.set(ValueLayout.JAVA_INT, i * 4, 0xFFFF0000);
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
}