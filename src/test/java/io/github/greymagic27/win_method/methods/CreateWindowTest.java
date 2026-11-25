package io.github.greymagic27.win_method.methods;

import io.github.greymagic27.win_method.WinDef.HWND;
import io.github.greymagic27.win_method.platform.User32;
import io.github.greymagic27.win_method.types.Commctrl;
import io.github.greymagic27.win_method.types.WinUser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CreateWindowTest {

    @BeforeEach
    void setUp() {
        Window.createWindow(WinUser.Wndproc.defaultWndProc(), "CreateWindowMethodTest", 800, 600, false);
    }

    @AfterEach
    void tearDown() {
        Window.reset();
    }

    @Test
    void testCreateListBoxWindow() {
        HWND listBox = assertDoesNotThrow(() -> CreateWindow.createListBoxWindow("Test List", Window.getCurrentWindow(), 0, 0, 0, 800, 600));
        assertNotNull(listBox);
        assertNotEquals(0, listBox.segment.address());
        assertEquals(Window.getCurrentWindow().segment.address(), User32.INSTANCE.GetParent(listBox).segment.address());
        User32.INSTANCE.DestroyWindow(listBox);
    }

    @Test
    void testCreateEditWindowWithScrollBars() {
        HWND edit = CreateWindow.createEditWindow("Test Edit", Window.getCurrentWindow(), 0, 0, 0, 800, 600, true);
        assertNotNull(edit);
        assertNotEquals(0, edit.segment.address());
        long style = User32.INSTANCE.GetWindowLongPtrW(edit, WinUser.GWL_STYLE).longValue();
        assertNotEquals(0, style & WinUser.WS_VSCROLL);
        assertNotEquals(0, style & WinUser.WS_HSCROLL);
        User32.INSTANCE.DestroyWindow(edit);
    }

    @Test
    void testCreateEditWindowWithoutScrollBars() {
        HWND edit = CreateWindow.createEditWindow("Test Edit", Window.getCurrentWindow(), 0, 0, 0, 800, 600, false);
        assertNotNull(edit);
        assertNotEquals(0, edit.segment.address());
        long style = User32.INSTANCE.GetWindowLongPtrW(edit, WinUser.GWL_STYLE).longValue();
        assertEquals(0, style & WinUser.WS_VSCROLL);
        assertEquals(0, style & WinUser.WS_HSCROLL);
        User32.INSTANCE.DestroyWindow(edit);
    }

    @Test
    void testCreateButtonWindow() {
        HWND button = assertDoesNotThrow(() -> CreateWindow.createButtonWindow("Test Button", Window.getCurrentWindow(), 0, 0, 0, 800, 600));
        assertNotNull(button);
        assertNotEquals(0, button.segment.address());
        assertEquals(Window.getCurrentWindow().segment.address(), User32.INSTANCE.GetParent(button).segment.address());
        User32.INSTANCE.DestroyWindow(button);
    }

    @Test
    void testCreateButtonWindowWithCustomStyle() {
        HWND button = assertDoesNotThrow(() -> CreateWindow.createButtonWindow("Test Button", WinUser.BS_BITMAP, Window.getCurrentWindow(), 0, 0, 0, 800, 600));
        assertNotNull(button);
        assertNotEquals(0, button.segment.address());
        assertEquals(Window.getCurrentWindow().segment.address(), User32.INSTANCE.GetParent(button).segment.address());
        assertTrue((User32.INSTANCE.GetWindowLongPtrW(button, WinUser.GWL_STYLE).longValue() & WinUser.BS_BITMAP) != 0);
        User32.INSTANCE.DestroyWindow(button);
    }

    @Test
    void testCreateOkButtonWindow() {
        HWND button = assertDoesNotThrow(() -> CreateWindow.createOkButtonWindow(Window.getCurrentWindow(), 0, 0, 800, 600));
        assertNotNull(button);
        assertNotEquals(0, button.segment.address());
        assertEquals(Window.getCurrentWindow().segment.address(), User32.INSTANCE.GetParent(button).segment.address());
        User32.INSTANCE.DestroyWindow(button);
    }

    @Test
    void testCreateCancelButtonWindow() {
        HWND button = assertDoesNotThrow(() -> CreateWindow.createCancelButtonWindow(Window.getCurrentWindow(), 0, 0, 800, 600));
        assertNotNull(button);
        assertNotEquals(0, button.segment.address());
        assertEquals(Window.getCurrentWindow().segment.address(), User32.INSTANCE.GetParent(button).segment.address());
        User32.INSTANCE.DestroyWindow(button);
    }

    @Test
    void testCreateRichEditWindow() {
        Window.reset();
        Window.createWindow(WinUser.Wndproc.defaultWndProc(), "CreateRichEditWindow", 800, 600, true);
        HWND richEdit = assertDoesNotThrow(() -> CreateWindow.createRichEditWindow("Test Rich Edit", Window.getCurrentWindow(), 0, 0, 0, 800, 600, false));
        assertNotNull(richEdit);
        assertNotEquals(0, richEdit.segment.address());
        assertEquals(Window.getCurrentWindow().segment.address(), User32.INSTANCE.GetParent(richEdit).segment.address());
        User32.INSTANCE.DestroyWindow(richEdit);
    }

    @Test
    void testCreatePopupWindow() {
        HWND popup = assertDoesNotThrow(() -> CreateWindow.createPopupWindow(Window.getClassName(), "Test Popup", Window.getCurrentWindow(), 0, 0, 0, 800, null));
        assertNotNull(popup);
        assertNotEquals(0, popup.segment.address());
        assertEquals(Window.getCurrentWindow().segment.address(), User32.INSTANCE.GetParent(popup).segment.address());
        long style = User32.INSTANCE.GetWindowLongPtrW(popup, WinUser.GWL_STYLE).longValue();
        assertTrue((style & Integer.toUnsignedLong(WinUser.WS_POPUP)) != 0);
        assertTrue((style & WinUser.WS_CAPTION) != 0);
        assertTrue((style & WinUser.WS_SYSMENU) != 0);
        assertTrue((style & WinUser.WS_VISIBLE) != 0);
    }

    @Test
    void testCreateStatusBarWindow() {
        HWND statusBar = assertDoesNotThrow(() -> CreateWindow.createStatusBarWindow("Test Status Bar", Window.getCurrentWindow(), 0, 0, 0, 800, 20));
        assertNotNull(statusBar);
        assertNotEquals(0, statusBar.segment.address());
        assertEquals(Window.getCurrentWindow().segment.address(), User32.INSTANCE.GetParent(statusBar).segment.address());
        long style = User32.INSTANCE.GetWindowLongPtrW(statusBar, WinUser.GWL_STYLE).longValue();
        assertTrue((style & WinUser.WS_CHILD) != 0);
        assertTrue((style & WinUser.WS_VISIBLE) != 0);
        assertTrue((style & Commctrl.SBARS_SIZEGRIP) != 0);
        User32.INSTANCE.DestroyWindow(statusBar);
    }
}