package io.github.greymagic27.win_method.types;

import io.github.greymagic27.win_method.IntSafe.DWORD;
import io.github.greymagic27.win_method.WinDef.BYTE;
import io.github.greymagic27.win_method.WinDef.COLORREF;
import io.github.greymagic27.win_method.WinDef.UINT;
import io.github.greymagic27.win_method.WinDef.WORD;
import io.github.greymagic27.win_method.WinNT.LCID;
import io.github.greymagic27.win_method.WinNT.LONG;
import io.github.greymagic27.win_method.WinNT.SHORT;
import org.junit.jupiter.api.Test;

import static io.github.greymagic27.win_method.types.RichEdit.CFM_COLOR;
import static io.github.greymagic27.win_method.types.RichEdit.EM_SETCHARFORMAT;
import static io.github.greymagic27.win_method.types.RichEdit.SCF_SELECTION;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RichEditTest {

    @Test
    void testValues() {
        assertEquals(0x444, EM_SETCHARFORMAT);
        assertEquals(0x0001, SCF_SELECTION);
        assertEquals(0x40000000, CFM_COLOR);
    }

    @Test
    void testCharFormat() {
        RichEdit.CHARFORMATW format = new RichEdit.CHARFORMATW();
        assertNotNull(format.szFaceName);
        assertEquals(32, format.szFaceName.length);
        assertNotNull(format.cbSize);
        assertNotNull(format.dwMask);
        assertNotNull(format.dwEffects);
        assertNotNull(format.yHeight);
        assertNotNull(format.yOffset);
        assertNotNull(format.crTextColor);
        assertNotNull(format.bCharSet);
        assertNotNull(format.bPitchAndFamily);
        format.cbSize = new UINT(123);
        format.dwMask = new DWORD(456);
        format.dwEffects = new DWORD(789);
        format.yHeight = new LONG(100);
        format.yOffset = new LONG(200);
        format.crTextColor = new COLORREF(0x112233);
        format.bCharSet = new BYTE((byte) 1);
        format.bPitchAndFamily = new BYTE((byte) 2);
        assertEquals(123, format.cbSize.intValue());
        assertEquals(456, format.dwMask.intValue());
        assertEquals(789, format.dwEffects.intValue());
        assertEquals(100, format.yHeight.intValue());
        assertEquals(200, format.yOffset.intValue());
        assertEquals(0x112233, format.crTextColor.intValue());
        assertEquals(1, format.bCharSet.byteValue());
        assertEquals(2, format.bPitchAndFamily.byteValue());
    }

    @Test
    void testCharFormat2() {
        RichEdit.CHARFORMAT2W format = new RichEdit.CHARFORMAT2W();
        assertNotNull(format.wWeight);
        assertNotNull(format.sSpacing);
        assertNotNull(format.crBackColor);
        assertNotNull(format.lcid);
        assertNotNull(format.dwReserved);
        assertNotNull(format.sStyle);
        assertNotNull(format.wKerning);
        assertNotNull(format.bUnderlineType);
        assertNotNull(format.bAnimation);
        assertNotNull(format.bRevAuthor);
        assertNotNull(format.bUnderlineColor);
        format.wWeight = new WORD((short) 700);
        format.sSpacing = new SHORT((short) -10);
        format.crBackColor = new COLORREF(0x332211);
        format.lcid = new LCID(1033);
        format.dwReserved = new DWORD(1234);
        format.sStyle = new SHORT((short) 2);
        format.wKerning = new WORD((short) 10);
        format.bUnderlineType = new BYTE((byte) 1);
        format.bAnimation = new BYTE((byte) 2);
        format.bRevAuthor = new BYTE((byte) 3);
        format.bUnderlineColor = new BYTE((byte) 4);
        assertEquals(700, format.wWeight.shortValue());
        assertEquals(-10, format.sSpacing.shortValue());
        assertEquals(0x332211, format.crBackColor.intValue());
        assertEquals(1033, format.lcid.intValue());
        assertEquals(1234, format.dwReserved.intValue());
        assertEquals(2, format.sStyle.shortValue());
        assertEquals(10, format.wKerning.shortValue());
        assertEquals(1, format.bUnderlineType.byteValue());
        assertEquals(2, format.bAnimation.byteValue());
        assertEquals(3, format.bRevAuthor.byteValue());
        assertEquals(4, format.bUnderlineColor.byteValue());
    }

    @Test
    void testReservedUnion() {
        RichEdit.CHARFORMAT2W.ReservedUnion union = new RichEdit.CHARFORMAT2W.ReservedUnion();
        assertNotNull(union.dwReserved);
        assertNotNull(union.dwCookie);
        union.dwReserved = new DWORD(0x12345678);
        assertEquals(0x12345678, union.dwReserved.intValue());
    }
}