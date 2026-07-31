package io.github.greymagic27.win_method.platform;

/// Values defined in commdlg.h
public interface Commdlg {

    /// The user can type only names of existing files in the File Name entry field
    int OFN_FILEMUSTEXIST = 0x00001000;
    /// The user can type only valid paths and file names
    int OFN_PATHMUSTEXIST = 0x00000800;
    /// Indicates that any customisations made to the Open or Save As dialogue box use the Explorer-style customization methods
    int OFN_EXPLORER = 0x00080000;
    /// Hides the Read Only check box
    int OFN_HIDEREADONLY = 0x00000004;
}
