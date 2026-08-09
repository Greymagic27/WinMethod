package io.github.greymagic27.win_method.types;

import org.junit.jupiter.api.Test;

import static io.github.greymagic27.win_method.types.WinNT.FILE_ATTRIBUTE_DIRECTORY;
import static io.github.greymagic27.win_method.types.WinNT.FILE_SHARE_READ;
import static io.github.greymagic27.win_method.types.WinNT.GENERIC_READ;
import static io.github.greymagic27.win_method.types.WinNT.GENERIC_WRITE;
import static org.junit.jupiter.api.Assertions.*;

class WinNTTest {

    @Test
    void testValues() {
        assertEquals(0x00000010, FILE_ATTRIBUTE_DIRECTORY);
        assertEquals(0x80000000, GENERIC_READ.intValue());
        assertEquals(0x40000000, GENERIC_WRITE.intValue());
        assertEquals(0x00000001, FILE_SHARE_READ);
    }
}