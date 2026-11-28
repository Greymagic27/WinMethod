package io.github.greymagic27.win_method.platform;

import io.github.greymagic27.win_method.IntSafe.DWORD;
import io.github.greymagic27.win_method.WinDef.BOOL;
import io.github.greymagic27.win_method.types.Commdlg;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class Comdlg32Test {

    private final Comdlg32 comdlg32 = Comdlg32.INSTANCE;

    @Test
    void testGetOpenFileName() {
        Commdlg.OPENFILENAMEW ofn = new Commdlg.OPENFILENAMEW();
        ofn.lStructSize = new DWORD(0);
        BOOL result = comdlg32.GetOpenFileNameW(ofn);
        assertNotNull(result);
        assertFalse(result.booleanValue());
        ofn.lStructSize = new DWORD(ofn.size());
        assertEquals(ofn.size(), ofn.lStructSize.intValue());
    }
}