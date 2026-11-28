package io.github.greymagic27.win_method.types;

import io.github.greymagic27.win_method.BaseTsd.UINT_PTR;
import io.github.greymagic27.win_method.IntSafe.DWORD;
import io.github.greymagic27.win_method.WinDef.HINSTANCE;
import io.github.greymagic27.win_method.WinDef.HWND;
import io.github.greymagic27.win_method.WinDef.LPARAM;
import io.github.greymagic27.win_method.WinDef.LPVOID;
import io.github.greymagic27.win_method.WinDef.UINT;
import io.github.greymagic27.win_method.WinDef.WORD;
import io.github.greymagic27.win_method.WinDef.WPARAM;
import io.github.greymagic27.win_method.WinNT.LPCWSTR;
import io.github.greymagic27.win_method.WinNT.LPWSTR;
import java.lang.foreign.MemorySegment;
import org.junit.jupiter.api.Test;

import static io.github.greymagic27.win_method.types.Commdlg.OFN_FILEMUSTEXIST;
import static io.github.greymagic27.win_method.types.Commdlg.OFN_NOCHANGEDIR;
import static io.github.greymagic27.win_method.types.Commdlg.OFN_PATHMUSTEXIST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class CommdlgTest {

    @Test
    void testValues() {
        assertEquals(0x00001000, OFN_FILEMUSTEXIST);
        assertEquals(0x00000800, OFN_PATHMUSTEXIST);
        assertEquals(0x00000008, OFN_NOCHANGEDIR);
    }

    @Test
    void testOpenFileName() {
        Commdlg.OPENFILENAMEW ofn = new Commdlg.OPENFILENAMEW();
        String filter = "Test file (*.test)\0*.test\0All files (*.*)\0*.*\0\0";
        assertNotNull(ofn.lStructSize);
        assertNotNull(ofn.hwndOwner);
        assertNotNull(ofn.hInstance);
        assertNotNull(ofn.lpstrFilter);
        assertNotNull(ofn.lpstrCustomFilter);
        assertNotNull(ofn.nMaxCustFilter);
        assertNotNull(ofn.nFilterIndex);
        assertNotNull(ofn.lpstrFile);
        assertNotNull(ofn.nMaxFile);
        assertNotNull(ofn.lpstrFileTitle);
        assertNotNull(ofn.nMaxFileTitle);
        assertNotNull(ofn.lpstrInitialDir);
        assertNotNull(ofn.lpstrTitle);
        assertNotNull(ofn.Flags);
        assertNotNull(ofn.nFileOffset);
        assertNotNull(ofn.nFileExtension);
        assertNotNull(ofn.lpstrDefExt);
        assertNotNull(ofn.lCustData);
        assertNotNull(ofn.pvReserved);
        assertNotNull(ofn.dwReserved);
        assertNotNull(ofn.FlagsEx);
        assertNull(ofn.lpfnHook);
        ofn.lStructSize = new DWORD(ofn.size());
        ofn.hwndOwner = new HWND(MemorySegment.ofAddress(0x1000));
        ofn.hInstance = new HINSTANCE(MemorySegment.ofAddress(0x2000));
        ofn.lpstrFilter = new LPCWSTR("Test file (*.test)\0*.test\0All files (*.*)\0*.*\0\0");
        ofn.lpstrCustomFilter = new LPWSTR(MemorySegment.ofAddress(0x3000));
        ofn.nMaxCustFilter = new DWORD(40);
        ofn.nFilterIndex = new DWORD(2);
        ofn.lpstrFile = new LPWSTR(MemorySegment.ofAddress(0x4000));
        ofn.nMaxFile = new DWORD(260);
        ofn.lpstrFileTitle = new LPWSTR(MemorySegment.ofAddress(0x5000));
        ofn.nMaxFileTitle = new DWORD(256);
        ofn.lpstrInitialDir = new LPCWSTR("T:\\Audio");
        ofn.lpstrTitle = new LPCWSTR("Test");
        ofn.Flags = new DWORD(OFN_FILEMUSTEXIST);
        ofn.nFileOffset = new WORD(12);
        ofn.nFileExtension = new WORD(16);
        ofn.lpstrDefExt = new LPCWSTR("test");
        ofn.lCustData = new LPARAM(123);
        ofn.lpTemplateName = new LPCWSTR("Test Dialog");
        ofn.pvReserved = new LPVOID(MemorySegment.ofAddress(0x6000));
        ofn.dwReserved = new DWORD(0);
        ofn.FlagsEx = new DWORD(1);
        assertEquals(ofn.size(), ofn.lStructSize.intValue());
        assertEquals(0x1000L, ofn.hwndOwner.segment.address());
        assertEquals(0x2000L, ofn.hInstance.segment.address());
        assertEquals(filter, ofn.lpstrFilter.getWideString(0, filter.length()));
        assertEquals(0x3000L, ofn.lpstrCustomFilter.segment.address());
        assertEquals(40, ofn.nMaxCustFilter.intValue());
        assertEquals(2, ofn.nFilterIndex.intValue());
        assertEquals(0x4000L, ofn.lpstrFile.segment.address());
        assertEquals(260, ofn.nMaxFile.intValue());
        assertEquals(0x5000L, ofn.lpstrFileTitle.segment.address());
        assertEquals(256, ofn.nMaxFileTitle.intValue());
        assertEquals("T:\\Audio", ofn.lpstrInitialDir.getWideString(0));
        assertEquals("Test", ofn.lpstrTitle.getWideString(0));
        assertEquals(0x00001000, ofn.Flags.intValue());
        assertEquals(12, ofn.nFileOffset.shortValue());
        assertEquals(16, ofn.nFileExtension.shortValue());
        assertEquals("test", ofn.lpstrDefExt.getWideString(0));
        assertEquals(123, ofn.lCustData.longValue());
        assertEquals("Test Dialog", ofn.lpTemplateName.getWideString(0));
        assertEquals(0x6000L, ofn.pvReserved.segment.address());
        assertEquals(0, ofn.dwReserved.intValue());
        assertEquals(1, ofn.FlagsEx.intValue());
    }

    @Test
    void testOfnHookProc() {
        HWND hwnd = new HWND(0x12345678);
        UINT msg = new UINT(0x110);
        WPARAM wparam = new WPARAM(123);
        LPARAM lparam = new LPARAM(456);
        Commdlg.LPOFNHOOKPROC hookProc = (hWnd, uMsg, wParam, lParam) -> {
            assertEquals(hwnd.segment.address(), hWnd.segment.address());
            assertEquals(msg.intValue(), uMsg.intValue());
            assertEquals(wparam.longValue(), wParam.longValue());
            assertEquals(lparam.longValue(), lParam.longValue());
            return new UINT_PTR(1);
        };
        UINT_PTR result = hookProc.callback(hwnd, msg, wparam, lparam);
        assertNotNull(result);
        assertEquals(1, result.longValue());
    }
}