package io.github.greymagic27.win_method.methods;

import io.github.greymagic27.win_method.WinDef.HFONT;
import io.github.greymagic27.win_method.WinDef.HGDIOBJ;
import io.github.greymagic27.win_method.platform.GDI32;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FontTest {

    @Test
    void testCreateFont() {
        HFONT font = assertDoesNotThrow(() -> Font.create("Consolas", 12));
        assertNotNull(font);
        assertNotEquals(0, font.segment.address());
        assertTrue(GDI32.INSTANCE.DeleteObject(new HGDIOBJ(font)).booleanValue());
    }
}