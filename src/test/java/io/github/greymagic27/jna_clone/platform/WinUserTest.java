package io.github.greymagic27.jna_clone.platform;

import org.junit.jupiter.api.Test;

import static io.github.greymagic27.jna_clone.platform.WinUser.WS_BORDER;
import static io.github.greymagic27.jna_clone.platform.WinUser.WS_CAPTION;
import static io.github.greymagic27.jna_clone.platform.WinUser.WS_CHILD;
import static io.github.greymagic27.jna_clone.platform.WinUser.WS_DISABLED;
import static io.github.greymagic27.jna_clone.platform.WinUser.WS_HSCROLL;
import static io.github.greymagic27.jna_clone.platform.WinUser.WS_MINIMIZE;
import static io.github.greymagic27.jna_clone.platform.WinUser.WS_OVERLAPPED;
import static io.github.greymagic27.jna_clone.platform.WinUser.WS_POPUP;
import static io.github.greymagic27.jna_clone.platform.WinUser.WS_SYSMENU;
import static io.github.greymagic27.jna_clone.platform.WinUser.WS_VISIBLE;
import static io.github.greymagic27.jna_clone.platform.WinUser.WS_VSCROLL;
import static org.junit.jupiter.api.Assertions.assertEquals;

class WinUserTest {

    @Test
    void testConstants() {
        assertEquals(0x00000000, WS_OVERLAPPED);
        assertEquals(0x80000000, WS_POPUP);
        assertEquals(0x40000000, WS_CHILD);
        assertEquals(0x20000000, WS_MINIMIZE);
        assertEquals(0x10000000, WS_VISIBLE);
        assertEquals(0x08000000, WS_DISABLED);
        assertEquals(0x00C00000, WS_CAPTION);
        assertEquals(0x00800000, WS_BORDER);
        assertEquals(0x00200000, WS_VSCROLL);
        assertEquals(0x00100000, WS_HSCROLL);
        assertEquals(0x00080000, WS_SYSMENU);
    }
}