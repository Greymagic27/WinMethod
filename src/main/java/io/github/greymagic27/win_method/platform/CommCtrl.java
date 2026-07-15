package io.github.greymagic27.win_method.platform;

/**
 * Values from CommCtrl.h
 */
public interface CommCtrl {

    /**
     * Creates edit controls. These controls contain editable text
     */
    String WC_EDIT = "EDIT";
    /**
     * Creates tree-view controls. These controls display a hierarchical list of items. Each item consists of a label and an optional bitmap
     */
    String WC_TREEVIEW = "SysTreeView32";

    /**
     * Uses lines to show the hierarchy of items
     */
    int TVS_HASLINES = 2;
    /**
     * Displays plus and minus buttons next to parent items
     */
    int TVS_HASBUTTONS = 1;
    /**
     * Uses lines to link items at the root of the tree-view control
     */
    int TVS_LINESATROOT = 4;
}
