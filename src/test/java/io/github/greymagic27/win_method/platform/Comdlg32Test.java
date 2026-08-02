package io.github.greymagic27.win_method.platform;

import io.github.greymagic27.win_method.IntSafe.DWORD;
import io.github.greymagic27.win_method.WinDef.BOOL;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class Comdlg32Test {

    @Test
    void testGetOpenFileName() {
        Commdlg.OPENFILENAMEW openFileName = new Commdlg.OPENFILENAMEW();
        openFileName.lStructSize = new DWORD(0);
        BOOL result = Comdlg32.INSTANCE.GetOpenFileNameW(openFileName);
        assertFalse(result.booleanValue());
        openFileName.lStructSize = new DWORD(openFileName.size());
        assertEquals(openFileName.size(), openFileName.lStructSize.intValue());
    }
}