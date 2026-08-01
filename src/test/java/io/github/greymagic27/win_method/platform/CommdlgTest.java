package io.github.greymagic27.win_method.platform;

import io.github.greymagic27.win_method.IntSafe.DWORD;
import io.github.greymagic27.win_method.WinDef.BOOL;
import io.github.greymagic27.win_method.WinDef.LPARAM;
import io.github.greymagic27.win_method.BaseTsd.UINT_PTR;
import io.github.greymagic27.win_method.WinDef.UINT;
import io.github.greymagic27.win_method.WinDef.WPARAM;
import io.github.greymagic27.win_method.WinNT.LPCWSTR;
import io.github.greymagic27.win_method.WinNT.LPWSTR;
import org.junit.jupiter.api.Test;

import static io.github.greymagic27.win_method.platform.Commdlg.OFN_EXPLORER;
import static io.github.greymagic27.win_method.platform.Commdlg.OFN_FILEMUSTEXIST;
import static io.github.greymagic27.win_method.platform.Commdlg.OFN_HIDEREADONLY;
import static io.github.greymagic27.win_method.platform.Commdlg.OFN_PATHMUSTEXIST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class CommdlgTest {

    @Test
    void testOfnValues() {
        assertEquals(0x00001000, OFN_FILEMUSTEXIST);
        assertEquals(0x00000800, OFN_PATHMUSTEXIST);
        assertEquals(0x00080000, OFN_EXPLORER);
        assertEquals(0x00000004, OFN_HIDEREADONLY);
    }

    @Test
    void testOpenFileName() {
        Commdlg.OPENFILENAMEW openfilenamew = new Commdlg.OPENFILENAMEW();
        assertNotNull(openfilenamew);
        openfilenamew.lStructSize = new DWORD(openfilenamew.size());
        openfilenamew.Flags = new DWORD(OFN_EXPLORER | OFN_FILEMUSTEXIST);
        openfilenamew.lpstrTitle = new LPCWSTR("Test");
        openfilenamew.nMaxFile = new DWORD(260);
        assertEquals(openfilenamew.size(), openfilenamew.lStructSize.intValue());
        assertEquals(OFN_EXPLORER | OFN_FILEMUSTEXIST, openfilenamew.Flags.intValue());
        assertEquals("Test", openfilenamew.lpstrTitle.getWideString(0));
        assertEquals(260, openfilenamew.nMaxFile.intValue());
    }

    @Test
    void testOpenFileNameDefaultValues() {
        Commdlg.OPENFILENAMEW openFileName = new Commdlg.OPENFILENAMEW();

        assertNull(openFileName.hwndOwner);
        assertNull(openFileName.hInstance);
        assertNull(openFileName.lpstrFilter);
        assertNull(openFileName.lpstrFile);
        assertNull(openFileName.lpfnHook);
        assertNull(openFileName.lpEditInfo);
    }

    @Test
    void testLpofnhookproc() {
        Commdlg.LPOFNHOOKPROC callback = (_, _, _, _) -> new UINT_PTR(0);
        assertNotNull(callback);
        UINT_PTR result = callback.Lpofnhookproc(null, new UINT(0), new WPARAM(0), new LPARAM(0));
        assertNotNull(result);
        assertEquals(0, result.longValue());
    }

    @Test
    void testGetOpenFileName() {
        Commdlg.OPENFILENAMEW openFileName = new Commdlg.OPENFILENAMEW();
        openFileName.lStructSize = new DWORD(openFileName.size());
        openFileName.lpstrFilter = new LPCWSTR("All Files\0*.*\0");
        openFileName.lpstrFile = new LPWSTR("Test");
        openFileName.nMaxFile = new DWORD(260);
        openFileName.Flags = new DWORD(OFN_EXPLORER | OFN_FILEMUSTEXIST | OFN_PATHMUSTEXIST | OFN_HIDEREADONLY);
        BOOL result = Commdlg.INSTANCE.GetOpenFileNameW(openFileName);
        assertNotNull(result);
    }
}
