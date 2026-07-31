package io.github.greymagic27.win_method.platform;

import io.github.greymagic27.win_method.WinDef.LPARAM;
import io.github.greymagic27.win_method.WinDef.UINT_PTR;
import io.github.greymagic27.win_method.WinDef.WPARAM;
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
        openfilenamew.lStructSize = openfilenamew.size();
        openfilenamew.Flags = OFN_EXPLORER | OFN_FILEMUSTEXIST;
        openfilenamew.lpstrTitle = "Test";
        openfilenamew.nMaxFile = 260;
        assertEquals(openfilenamew.size(), openfilenamew.lStructSize);
        assertEquals(OFN_EXPLORER | OFN_FILEMUSTEXIST, openfilenamew.Flags);
        assertEquals("Test", openfilenamew.lpstrTitle);
        assertEquals(260, openfilenamew.nMaxFile);
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
        UINT_PTR result = callback.Lpofnhookproc(null, 0, new WPARAM(0), new LPARAM(0));
        assertNotNull(result);
        assertEquals(0, result.longValue());
    }
}
