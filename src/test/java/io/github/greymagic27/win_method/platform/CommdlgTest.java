package io.github.greymagic27.win_method.platform;

import org.junit.jupiter.api.Test;

import static io.github.greymagic27.win_method.platform.Commdlg.OFN_EXPLORER;
import static io.github.greymagic27.win_method.platform.Commdlg.OFN_FILEMUSTEXIST;
import static io.github.greymagic27.win_method.platform.Commdlg.OFN_HIDEREADONLY;
import static io.github.greymagic27.win_method.platform.Commdlg.OFN_PATHMUSTEXIST;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CommdlgTest {

    @Test
    void testOfnValues() {
        assertEquals(0x00001000, OFN_FILEMUSTEXIST);
        assertEquals(0x00000800, OFN_PATHMUSTEXIST);
        assertEquals(0x00080000, OFN_EXPLORER);
        assertEquals(0x00000004, OFN_HIDEREADONLY);
    }
}
