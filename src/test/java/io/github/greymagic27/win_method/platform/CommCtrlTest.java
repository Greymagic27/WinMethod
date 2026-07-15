package io.github.greymagic27.win_method.platform;

import org.junit.jupiter.api.Test;

import static io.github.greymagic27.win_method.platform.CommCtrl.TVS_HASBUTTONS;
import static io.github.greymagic27.win_method.platform.CommCtrl.TVS_HASLINES;
import static io.github.greymagic27.win_method.platform.CommCtrl.TVS_LINESATROOT;
import static io.github.greymagic27.win_method.platform.CommCtrl.WC_EDIT;
import static io.github.greymagic27.win_method.platform.CommCtrl.WC_TREEVIEW;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CommCtrlTest {

    @Test
    void testWcValues() {
        assertEquals("EDIT", WC_EDIT);
        assertEquals("SysTreeView32", WC_TREEVIEW);
    }

    @Test
    void testTvsValues() {
        assertEquals(2, TVS_HASLINES);
        assertEquals(1, TVS_HASBUTTONS);
        assertEquals(4, TVS_LINESATROOT);
    }
}