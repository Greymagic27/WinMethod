package io.github.greymagic27.win_method.types;

import io.github.greymagic27.win_method.BaseTsd.UINT_PTR;
import io.github.greymagic27.win_method.Callback;
import io.github.greymagic27.win_method.IntSafe.DWORD;
import io.github.greymagic27.win_method.Pointer;
import io.github.greymagic27.win_method.Structure;
import io.github.greymagic27.win_method.WinDef.HINSTANCE;
import io.github.greymagic27.win_method.WinDef.HWND;
import io.github.greymagic27.win_method.WinDef.LPARAM;
import io.github.greymagic27.win_method.WinDef.LPVOID;
import io.github.greymagic27.win_method.WinDef.UINT;
import io.github.greymagic27.win_method.WinDef.WORD;
import io.github.greymagic27.win_method.WinDef.WPARAM;
import io.github.greymagic27.win_method.WinNT.LPCWSTR;
import io.github.greymagic27.win_method.WinNT.LPWSTR;
import io.github.greymagic27.win_method.platform.Comdlg32;

/// Values defined in commdlg.h
public interface Commdlg {

    /// The user can type only names of existing files in the File Name entry field
    int OFN_FILEMUSTEXIST = 0x00001000;
    /// The user can type only valid paths and file names
    int OFN_PATHMUSTEXIST = 0x00000800;
    /// Indicates that any customisations made to the Open or Save As dialogue box use the Explorer-style customisation methods
    int OFN_EXPLORER = 0x00080000;
    /// Hides the Read Only check box
    int OFN_HIDEREADONLY = 0x00000004;

    /// Receives notification messages sent from the dialogue box
    interface LPOFNHOOKPROC extends Callback {
        /// Defines a {@link Pointer} to this callback function
        ///
        /// @param unnamedParam1 A {@link io.github.greymagic27.win_method.WinNT.HANDLE} to the child dialogue box of the Open or Save As dialogue box
        /// @param unnamedParam2 The identifier of the message being received
        /// @param unnamedParam3 Additional information about the message. The exact meaning depends on the value of the unnamedParam2 parameter
        /// @param unnamedParam4 Additional information about the message. The exact meaning depends on the value of the unnamedParam2 parameter
        /// @return If the hook procedure returns zero, the default dialogue box procedure processes the message. If the hook procedure returns a nonzero value, the default dialogue box procedure ignores the message
        UINT_PTR Lpofnhookproc(HWND unnamedParam1, UINT unnamedParam2, WPARAM unnamedParam3, LPARAM unnamedParam4);
    }

    /// Contains information that the {@link Comdlg32#GetOpenFileNameW(OPENFILENAMEW)} and [GetSaveFileName](https://learn.microsoft.com/en-us/windows/desktop/api/commdlg/nf-commdlg-getsavefilenamew) functions use to initialise an Open or Save As dialogue box.Once the dialogue box is closed, the system returns information about the user's selection in this structure
    @SuppressWarnings("unused")
    @Structure.AutoFieldOrder
    class OPENFILENAMEW extends Structure {
        /// The length, in bytes, of the structure. Use {@code sizeof (OPENFILENAME)} for this parameter
        public DWORD lStructSize;
        /// A {@link io.github.greymagic27.win_method.WinNT.HANDLE} to the window that owns the dialogue box. This can be any valid window handle, or it can be NULl if the dialogue box has no owner
        public HWND hwndOwner;
        /// If the OFN_ENABLETEMPLATEHANDLE flag is set in the Flags member, hInstance is a {@link io.github.greymagic27.win_method.WinNT.HANDLE} to a memory object containing a dialogue box template. If the OFN_ENABLETEMPLATE flag is set, hInstance is a {@link io.github.greymagic27.win_method.WinNT.HANDLE} to a module that contains a dialogue box template named by the lpTemplateName member. If neither flag is set, this member is ignored
        public HINSTANCE hInstance;
        /// A buffer containing pairs of null-terminated filter strings. The last string in te buffer must be terminated by two NULL characters
        public LPCWSTR lpstrFilter;
        /// A static buffer that contains a pair of null-terminated filter strings for preserving the filter pattern chosen by the user. The first string is your display string that describes the custom filter, and the second string is the filter pattern selected by the user
        public LPWSTR lpstrCustomFilter;
        /// The size, in characters, of the buffer identified by lpstrCustomFilter. This buffer should be at least 40 characters long. This member is ignored if lpstrCustomFilter is NULL or points to a NULL string
        public DWORD nMaxCustFilter;
        /// The index of the currently selected filter
        public DWORD nFilterIndex;
        /// The file name used to initialise the File Name edit control. The first character must be NULL if initialisation is not necessary
        public LPWSTR lpstrFile;
        /// The size, in characters, of the buffer pointed to by lpstrFile. The buffer must be large enough to store the path and file name, including a NULL terminating character
        public DWORD nMaxFile;
        /// The file name and extensions of the selected file. This can be NULL
        public LPWSTR lpstrFileTitle;
        /// The size, in characters, of the buffer pointed to by lpstrFileTitle. This is ignored if lpstrFileTitle is NULL
        public DWORD nMaxFileTitle;
        /// The initial directory
        public LPCWSTR lpstrInitialDir;
        /// A string to be placed in the title bar of the dialogue box. If NULL, the system uses the default title
        public LPCWSTR lpstrTitle;
        /// A set of bit flags you can use to initialise the dialogue box. This can be a combination of [the following flags](https://learn.microsoft.com/en-us/windows/win32/api/commdlg/ns-commdlg-openfilenamew)
        public DWORD Flags;
        /// The zero-based offset, in characters, from the beginning of the path to the file name in the string pointed to by lpstrFile
        public WORD nFileOffset;
        /// The zero-based offset, in characters, from the beginning of the path to the file extension in the string pointed to by lpstrFile
        public WORD nFileExtension;
        /// The default extension
        public LPCWSTR lpstrDefExt;
        /// Application-defined data that the system passes to the hook procedure identified by lpfnHook
        public LPARAM lCustData;
        /// A {@link Pointer} to a hook procedure. This member is ignored unless the Flags member includes the {@code OFN_ENABLEHOOK} flag
        public LPOFNHOOKPROC lpfnHook;
        /// The name of the dialogue template resource in the module identified by the hInstance member
        public LPCWSTR lpTemplateName;
        /// This member is reserved
        public LPVOID pvReserved;
        /// This member is reserved
        public DWORD dwReserved;
        /// A set of bit flags you can use to initialise the dialogue box. This can be zero or OFN_EX_NOPLACESBAR
        public DWORD FlagsEx;
    }
}
