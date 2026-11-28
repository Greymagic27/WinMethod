package io.github.greymagic27.win_method.platform;

import io.github.greymagic27.win_method.Library;
import io.github.greymagic27.win_method.WinDef.BOOL;
import io.github.greymagic27.win_method.types.Commdlg;

/// Interface for Comdlg32.dll
public interface Comdlg32 extends Library {

    /// The instance
    Comdlg32 INSTANCE = Library.load(Comdlg32.class);

    /// Creates an Open dialog box that lets the user specify the drive, directory, and the name of a file or set of files to be opened
    BOOL GetOpenFileNameW(Commdlg.OPENFILENAMEW unnamedParam1);
}
