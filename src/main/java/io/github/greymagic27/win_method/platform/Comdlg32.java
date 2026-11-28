package io.github.greymagic27.win_method.platform;

import io.github.greymagic27.win_method.Library;
import io.github.greymagic27.win_method.WinDef.BOOL;
import io.github.greymagic27.win_method.types.Commdlg;

/// Interface for Comdlg32.dll
public interface Comdlg32 extends Library {

    /// The instance
    Comdlg32 INSTANCE = Library.load(Comdlg32.class);


    /// Creates an Open dialog box that lets the user specify the drive, directory, and the name of a file or set of files to be opened
    ///
    /// @param unnamedParam1 A pointer to an {@link io.github.greymagic27.win_method.types.Commdlg.OPENFILENAMEW} structure that contains information used to initialise the dialog box. When GetOpenFileName returns, this structure contains information about the user's file selection
    /// @return If the user specifies a file name and clicks the OK button, the return value is nonzero. f the user cancels or closes the Open dialog box or an error occurs, the return value is zero
    BOOL GetOpenFileNameW(Commdlg.OPENFILENAMEW unnamedParam1);
}
