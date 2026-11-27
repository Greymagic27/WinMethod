package io.github.greymagic27.win_method.types;

import io.github.greymagic27.win_method.IntSafe.DWORD;
import io.github.greymagic27.win_method.Structure;

/// Values defined in commctrl.h
public interface Commctrl {

    /// The status bar control will include a sizing grip at the right end of the status bar. A sizing grip is similar to a sizing border; it is a rectangular area that the user can click and drag to resize the parent window.
    int SBARS_SIZEGRIP = 0x0100;

    /// Load tab and tooltip control classes
    int ICC_TAB_CLASSES = 0x00000008;

    /// Carries information used to load common control classes from the dynamic-link library (DLL). This structure is used with the {@link io.github.greymagic27.win_method.platform.Comctl32#InitCommonControlsEx(INITCOMMONCONTROLSEX)} function
    @Structure.AutoFieldOrder
    class INITCOMMONCONTROLSEX extends Structure {
        /// The size of the structure, in bytes
        public DWORD dwSize;
        /// The set of bit flags that indicate which common control classes will be loaded from the DLL. This can be a combination of [the following values](https://learn.microsoft.com/en-us/windows/win32/api/commctrl/ns-commctrl-initcommoncontrolsex)
        public DWORD dwICC;
    }
}
