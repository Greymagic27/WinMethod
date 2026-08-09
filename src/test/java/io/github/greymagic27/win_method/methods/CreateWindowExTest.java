package io.github.greymagic27.win_method.methods;

import io.github.greymagic27.win_method.WinDef.HWND;
import io.github.greymagic27.win_method.platform.User32;
import io.github.greymagic27.win_method.types.WinUser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CreateWindowExTest {

    @BeforeEach
    void setUp() {
        Window.createWindow(WinUser.Wndproc.defaultWndProc(), "CreateWindowMethodTest", 800, 600);
    }

    @AfterEach
    void tearDown() {
        Window.reset();
    }

    @Test
    void testCreateListBoxWindow() {
        HWND listBox = assertDoesNotThrow(() -> CreateWindowEx.createListBoxWindow("Test List", Window.getCurrentWindow(), 0, 0, 0, 800, 600));
        assertNotNull(listBox);
        assertNotEquals(0, listBox.segment.address());
        assertEquals(Window.getCurrentWindow().segment.address(), User32.INSTANCE.GetParent(listBox).segment.address());
        User32.INSTANCE.DestroyWindow(listBox);
    }

    @Test
    void testCreateEditWindow() {
        HWND edit = assertDoesNotThrow(() -> CreateWindowEx.createEditWindow("Test Edit", Window.getCurrentWindow(), 0, 0, 0, 800, 600));
        assertNotNull(edit);
        assertNotEquals(0, edit.segment.address());
        assertEquals(Window.getCurrentWindow().segment.address(), User32.INSTANCE.GetParent(edit).segment.address());
        User32.INSTANCE.DestroyWindow(edit);
    }
}