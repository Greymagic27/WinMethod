package io.github.greymagic27.win_method.platform;

import io.github.greymagic27.win_method.Callback;
import io.github.greymagic27.win_method.Library;
import io.github.greymagic27.win_method.Pointer;
import io.github.greymagic27.win_method.Structure;
import io.github.greymagic27.win_method.WinDef.BOOL;
import io.github.greymagic27.win_method.WinDef.HINSTANCE;
import io.github.greymagic27.win_method.WinDef.HWND;
import io.github.greymagic27.win_method.WinDef.LPARAM;
import io.github.greymagic27.win_method.WinDef.LPVOID;
import io.github.greymagic27.win_method.WinDef.UINT_PTR;
import io.github.greymagic27.win_method.WinDef.WORD;
import io.github.greymagic27.win_method.WinDef.WPARAM;

/// Values defined in commdlg.h
public interface Commdlg extends Library {

    /// The instance
    Commdlg INSTANCE = Library.load("comdlg32", Commdlg.class);

    /// The user can type only names of existing files in the File Name entry field
    int OFN_FILEMUSTEXIST = 0x00001000;
    /// The user can type only valid paths and file names
    int OFN_PATHMUSTEXIST = 0x00000800;
    /// Indicates that any customisations made to the Open or Save As dialogue box use the Explorer-style customisation methods
    int OFN_EXPLORER = 0x00080000;
    /// Hides the Read Only check box
    int OFN_HIDEREADONLY = 0x00000004;
    /// Creates an Open dialogue box that lets the user specify the drive, directory, and name of a file or set of files to be opened
    ///
    /// @param unnamedParam1 A {@link Pointer} to an {@link OPENFILENAMEW} structure that contains information used to initialise the dialogue box
    /// @return If a file name is specified, the return value is nonzero. If the dialogue box is cancelled, the return value is zero
    BOOL GetOpenFileNameW(OPENFILENAMEW unnamedParam1);

    /// Receives notification messages sent from the dialogue box
    interface LPOFNHOOKPROC extends Callback {
        /// Defines a {@link Pointer} to this callback function
        ///
        /// @param unnamedParam1 A {@link io.github.greymagic27.win_method.WinNT.HANDLE} to the child dialogue box of the Open or Save As dialogue box
        /// @param unnamedParam2 The identifier of the message being received
        /// @param unnamedParam3 Additional information about the message. The exact meaning depends on the value of the unnamedParam2 parameter
        /// @param unnamedParam4 Additional information about the message. The exact meaning depends on the value of the unnamedParam2 parameter
        /// @return If the hook procedure returns zero, the default dialogue box procedure processes the message. If the hook procedure returns a nonzero value, the default dialogue box procedure ignores the message
        UINT_PTR Lpofnhookproc(HWND unnamedParam1, int unnamedParam2, WPARAM unnamedParam3, LPARAM unnamedParam4);
    }

    /// Contains information that the {@link #GetOpenFileNameW(OPENFILENAMEW)} and [GetSaveFileName](https://learn.microsoft.com/en-us/windows/desktop/api/commdlg/nf-commdlg-getsavefilenamew) functions use to initialise an Open or Save As dialogue box.Once the dialogue box is closed, the system returns information about the user's selection in this structure
    @SuppressWarnings("unused")
    @Structure.AutoFieldOrder
    class OPENFILENAMEW extends Structure {
        /// The length, in bytes, of the structure. Use {@code sizeof (OPENFILENAME)} for this parameter
        public int lStructSize;
        /// A {@link io.github.greymagic27.win_method.WinNT.HANDLE} to the window that owns the dialogue box. This can be any valid window handle, or it can be NULl if the dialogue box has no owner
        public HWND hwndOwner;
        /// If the OFN_ENABLETEMPLATEHANDLE flag is set in the Flags member, hInstance is a {@link io.github.greymagic27.win_method.WinNT.HANDLE} to a memory object containing a dialogue box template. If the OFN_ENABLETEMPLATE flag is set, hInstance is a {@link io.github.greymagic27.win_method.WinNT.HANDLE} to a module that contains a dialogue box template named by the lpTemplateName member. If neither flag is set, this member is ignored
        public HINSTANCE hInstance;
        /// A buffer containing pairs of null-terminated filter strings. The last string in te buffer must be terminated by two NULL characters
        public String lpstrFilter;
        /// A static buffer that contains a pair of null-terminated filter strings for preserving the filter pattern chosen by the user. The first string is your display string that describes the custom filter, and the second string is the filter pattern selected by the user
        public String lpstrCustomFilter;
        /// The size, in characters, of the buffer identified by lpstrCustomFilter. This buffer should be at least 40 characters long. This member is ignored if lpstrCustomFilter is NULL or points to a NULL string
        public int nMaxCustFilter;
        /// The index of the currently selected filter
        public int nFilterIndex;
        /// The file name used to initialise the File Name edit control. The first character must be NULL if initialisation is not necessary
        public String lpstrFile;
        /// The size, in characters, of the buffer pointed to by lpstrFile. The buffer must be large enough to store the path and file name, including a NULL terminating character
        public int nMaxFile;
        /// The file name and extensions of the selected file. This can be NULL
        public String lpstrFileTitle;
        /// The size, in characters, of the buffer pointed to by lpstrFileTitle. This is ignored if lpstrFileTitle is NULL
        public int nMaxFileTitle;
        /// The initial directory
        public String lpstrInitialDir;
        /// A string to be placed in the title bar of the dialogue box. If NULL, the system uses the default title
        public String lpstrTitle;
        /// A set of bit flags you can use to initialise the dialogue box. This can be a combination of [the following flags](https://learn.microsoft.com/en-us/windows/win32/api/commdlg/ns-commdlg-openfilenamew)
        public int Flags;
        /// The zero-based offset, in characters, from the beginning of the path to the file name in the string pointed to by lpstrFile
        public WORD nFileOffset;
        /// The zero-based offset, in characters, from the beginning of the path to the file extension in the string pointed to by lpstrFile
        public WORD nFileExtension;
        /// The default extension
        public String lpstrDefExt;
        /// Application-defined data that the system passes to the hook procedure identified by lpfnHook
        public LPARAM lCustData;
        /// A {@link Pointer} to a hook procedure. This member is ignored unless the Flags member includes the {@code OFN_ENABLEHOOK} flag
        public LPOFNHOOKPROC lpfnHook;
        /// The name of the dialogue template resource in the module identified by the hInstance member
        public String lpTemplateName;
        /// This member is conditionally compiled so that it is only applicable to macOS
        public Pointer lpEditInfo;
        /// This member is conditionally compiled so that it is only applicable to macOS
        public String lpstrPrompt;
        /// This member is reserved
        public LPVOID pvReserved;
        /// This member is reserved
        public int dwReserved;
        /// A set of bit flags you can use to initialise the dialogue box. This can be zero or OFN_EX_NOPLACESBAR
        public int FlagsEx;
    }
}
