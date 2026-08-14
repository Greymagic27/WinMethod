package io.github.greymagic27.win_method.types;

import org.junit.jupiter.api.Test;

import static io.github.greymagic27.win_method.types.WinBase.HANDLE_FLAG_INHERIT;
import static io.github.greymagic27.win_method.types.WinBase.WAIT_OBJECT_0;
import static org.junit.jupiter.api.Assertions.assertEquals;

class WinBaseTest {

    @Test
    void testValues() {
        assertEquals(0x00000001, HANDLE_FLAG_INHERIT);
        assertEquals(0x00000000, WAIT_OBJECT_0);
    }
}