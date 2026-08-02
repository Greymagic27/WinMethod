package io.github.greymagic27.win_method.platform;

import io.github.greymagic27.win_method.Library;
import io.github.greymagic27.win_method.Pointer;
import io.github.greymagic27.win_method.WinDef.BOOL;
import io.github.greymagic27.win_method.types.Commdlg;

/// Interface for Comdlg32.dll
public interface Comdlg32 extends Library {
    /// The instance
    Comdlg32 INSTANCE = Library.load(Comdlg32.class);

    /// Creates an Open dialogue box that lets the user specify the drive, directory, and name of a file or set of files to be opened
    ///
    /// @param unnamedParam1 A {@link Pointer} to an {@link Commdlg.OPENFILENAMEW} structure that contains information used to initialise the dialogue box
    /// @return If a file name is specified, the return value is nonzero. If the dialogue box is cancelled, the return value is zero
    BOOL GetOpenFileNameW(Commdlg.OPENFILENAMEW unnamedParam1);
}
