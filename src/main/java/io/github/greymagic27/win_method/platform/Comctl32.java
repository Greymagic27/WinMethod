package io.github.greymagic27.win_method.platform;

import io.github.greymagic27.win_method.Library;
import io.github.greymagic27.win_method.WinDef.BOOL;
import io.github.greymagic27.win_method.types.Commctrl;

/// Interface for Comctl32.dll
public interface Comctl32 extends Library {

    Comctl32 INSTANCE = Library.load(Comctl32.class);

    /// Ensures that the common control DLL (Comctl32.dll) is loaded, and registers specific common control classes from the DLL. An application must call this function before creating a common control
    /// @param picce A pointer to an {@link io.github.greymagic27.win_method.types.Commctrl.INITCOMMONCONTROLSEX} structure that contains information specifying which control classes will be registered
    /// @return Returns TRUE if successful, or FALSE otherwise
    BOOL InitCommonControlsEx(Commctrl.INITCOMMONCONTROLSEX picce);
}
