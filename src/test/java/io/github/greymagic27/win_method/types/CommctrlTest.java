package io.github.greymagic27.win_method.types;

import io.github.greymagic27.win_method.IntSafe.DWORD;
import org.junit.jupiter.api.Test;

import static io.github.greymagic27.win_method.types.Commctrl.ICC_STANDARD_CLASSES;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CommctrlTest {

    @Test
    void testValues() {
        assertEquals(0x00004000, ICC_STANDARD_CLASSES);
    }

    @Test
    void testInitCommonControlsEx() {
        Commctrl.INITCOMMONCONTROLSEX iccex = new Commctrl.INITCOMMONCONTROLSEX();
        assertEquals(new DWORD(0).intValue(), iccex.dwSize.intValue());
        assertEquals(new DWORD(0).intValue(), iccex.dwICC.intValue());
        iccex.dwSize = new DWORD(8);
        iccex.dwICC = new DWORD(ICC_STANDARD_CLASSES);
        assertEquals(8, iccex.dwSize.intValue());
        assertEquals(ICC_STANDARD_CLASSES, iccex.dwICC.intValue());
    }
}