package io.github.greymagic27.win_method.platform;

import io.github.greymagic27.win_method.IntSafe.DWORD;
import io.github.greymagic27.win_method.WinDef.BOOL;
import io.github.greymagic27.win_method.types.Commctrl;
import org.junit.jupiter.api.Test;

import static io.github.greymagic27.win_method.types.Commctrl.ICC_STANDARD_CLASSES;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Comctl32Test {

    private final Comctl32 comctl32 = Comctl32.INSTANCE;

    @Test
    void testInitCommonControlsEx() {
        Commctrl.INITCOMMONCONTROLSEX picce = new Commctrl.INITCOMMONCONTROLSEX();
        picce.dwSize = new DWORD(8);
        picce.dwICC = new DWORD(ICC_STANDARD_CLASSES);
        BOOL result = comctl32.InitCommonControlsEx(picce);
        assertNotNull(result);
        assertTrue(result.booleanValue());
    }
}