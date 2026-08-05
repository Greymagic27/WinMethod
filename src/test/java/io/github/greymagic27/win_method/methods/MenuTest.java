package io.github.greymagic27.win_method.methods;

import io.github.greymagic27.win_method.WinDef.HMENU;
import io.github.greymagic27.win_method.platform.User32;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class MenuTest {

    private static final int ID_FILE_OPEN = 1001;

    @Test
    void testAppendMenu() {
        HMENU menu = User32.INSTANCE.CreateMenu();
        assertTrue(Menu.appendMenu(menu, 0x00000000, ID_FILE_OPEN, "Open"));
    }

    @Test
    void testAppendString() {
        HMENU menu = User32.INSTANCE.CreateMenu();
        assertTrue(Menu.appendString(menu, 0, "Test"));
    }

    @Test
    void testAppendSeparator() {
        HMENU menu = User32.INSTANCE.CreateMenu();
        assertTrue(Menu.appendSeparator(menu));
    }

    @Test
    void testAppendExitMenuItem() {
        HMENU menu = User32.INSTANCE.CreateMenu();
        assertTrue(Menu.appendExitMenuItem(menu));
    }

    @Test
    void testAppendPopupMenu() {
        HMENU menu = User32.INSTANCE.CreateMenu();
        HMENU popup = User32.INSTANCE.CreatePopupMenu();
        assertTrue(Menu.appendPopupMenu(menu, popup.segment.address(), "File"));
    }
}