package io.github.greymagic27.win_method.types;

import io.github.greymagic27.win_method.IntSafe.DWORD;
import io.github.greymagic27.win_method.Structure;

/// Values defined in commctrl.h
public interface Commctrl {

    /// Load one of the intrinsic User32 control classes. The user controls include button, edit, static, listbox, combobox, and scroll bar
    int ICC_STANDARD_CLASSES = 0x00004000;

    /// Carries information used to load common control classes from the dynamic-link library (DLL). This structure is used with the {@link io.github.greymagic27.win_method.platform.Comctl32#InitCommonControlsEx(INITCOMMONCONTROLSEX)} function
    @Structure.AutoFieldOrder
    class INITCOMMONCONTROLSEX extends Structure {
        /// The size of the structure, in bytes
        public DWORD dwSize;
        /// The set of bit flags that indicate which common control classes will be loaded from the DLL. This can be a combination of [the following values](https://learn.microsoft.com/en-us/windows/win32/api/commctrl/ns-commctrl-initcommoncontrolsex)
        public DWORD dwICC;
    }
}
