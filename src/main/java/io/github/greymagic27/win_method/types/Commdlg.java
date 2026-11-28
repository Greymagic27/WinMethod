package io.github.greymagic27.win_method.types;

import io.github.greymagic27.win_method.BaseTsd.UINT_PTR;
import io.github.greymagic27.win_method.Callback;
import io.github.greymagic27.win_method.IntSafe.DWORD;
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

/// Values defined in commdlg.h
public interface Commdlg {

    /// The user can type only names of existing files in the File Name entry field
    int OFN_FILEMUSTEXIST = 0x00001000;
    /// The user can type only valid paths and file names
    int OFN_PATHMUSTEXIST = 0x00000800;
    /// Restores the current directory to its original value if the user changed the directory while searching for files
    int OFN_NOCHANGEDIR = 0x00000008;

    /// Receives notification messages sent from the dialog box. The function also receives messages for any additional controls that you defined by specifying a child dialogue template
    interface LPOFNHOOKPROC extends Callback {
        /// Processes the specified notification message
        /// @param unnamedParam1 A handle to the child dialog box of the Open or Save As dialog box. Use the {@link io.github.greymagic27.win_method.platform.User32#GetParent(HWND)} function to get the handle to the Open or Save As dialog box
        /// @param unnamedParam2 The identifier of the message being received
        /// @param unnamedParam3 Additional information about the message. The exact meaning depends on the value of the unnamedParam2 parameter
        /// @param unnamedParam4 Additional information about the message. The exact meaning depends on the value of the unnamedParam2 parameter
        /// @return If the hook procedure returns zero, the default dialog box procedure processes the message. If the hook procedure returns a nonzero value, the default dialog box procedure ignores the message
        UINT_PTR callback(HWND unnamedParam1, UINT unnamedParam2, WPARAM unnamedParam3, LPARAM unnamedParam4);
    }

    /// Contains information that the GetOpenFileName and GetSaveFileName functions use to initialise an Open or Save As dialog box. After the user closes the dialog box, the system returns information about the user's selection in this structure
    @Structure.AutoFieldOrder
    class OPENFILENAMEW extends Structure {
        /// The length, in bytes, of the structure. Use {@code sizeof (OPENFILENAMEW)} for this parameter
        public DWORD lStructSize;
        /// A handle to the window that owns the dialog box. This member can be any valid window handle, or it can be NULL if the dialog box has no owner
        public HWND hwndOwner;
        /// If the OFN_ENABLETEMPLATEHANDLE flag is set in the Flags member, hInstance is a handle to a memory object containing a dialog box template. If the OFN_ENABLETEMPLATE flag is set, hInstance is a handle to a module that contains a dialog box template named by the lpTemplateName member. If neither flag is set, this member is ignored. If the OFN_EXPLORER flag is set, the system uses the specified template to create a dialog box that is a child of the default Explorer-style dialog box. If the OFN_EXPLORER flag is not set, the system uses the template to create an old-style dialog box that replaces the default dialog box
        public HINSTANCE hInstance;
        /// A buffer containing pairs of null-terminated filter strings. The last string in the buffer must be terminated by two NULL characters. The first string in each pair is a display string that describes the filter (for example, "Text Files"), and the second string specifies the filter pattern (for example, ".TXT"). To specify multiple filter patterns for a single display string, use a semicolon to separate the patterns (for example, ".TXT;.DOC;.BAK"). A pattern string can be a combination of valid file name characters and the asterisk (*) wildcard character. Do not include spaces in the pattern string. If lpstrFilter is NULL, the dialog box does not display any filters
        public LPCWSTR lpstrFilter;
        /// A static buffer that contains a pair of null-terminated filter strings for preserving the filter pattern chosen by the user. The first string is your display string that describes the custom filter, and the second string is the filter pattern selected by the user. The first time your application creates the dialog box, you specify the first string, which can be any nonempty string. When the user selects a file, the dialog box copies the current filter pattern to the second string. The preserved filter pattern can be one of the patterns specified in the lpstrFilter buffer, or it can be a filter pattern typed by the user. The system uses the strings to initialise the user-defined file filter the next time the dialog box is created. If the nFilterIndex member is zero, the dialog box uses the custom filter. If this member is NULL, the dialog box does not preserve user-defined filter patterns. If this member is not NULL, the value of the nMaxCustFilter member must specify the size, in characters, of the lpstrCustomFilter buffer
        public LPWSTR lpstrCustomFilter;
        /// The size, in characters, of the buffer identified by lpstrCustomFilter. This buffer should be at least 40 characters long. This member is ignored if lpstrCustomFilter is NULL or points to a NULL string.
        public DWORD nMaxCustFilter;
        /// The index of the currently selected filter in the File Types control. The buffer pointed to by lpstrFilter contains pairs of strings that define the filters. The first pair of strings has an index value of 1, the second pair 2, and so on. An index of zero indicates the custom filter specified by lpstrCustomFilter. You can specify an index on input to indicate the initial filter description and filter pattern for the dialog box. When the user selects a file, nFilterIndex returns the index of the currently displayed filter. If nFilterIndex is zero and lpstrCustomFilter is NULL, the system uses the first filter in the lpstrFilter buffer. If all three members are zero or NULL, the system does not use any filters and does not show any files in the file list control of the dialog box
        public DWORD nFilterIndex;
        /// The file name used to initialise the File Name edit control. The first character of this buffer must be NULL if initialisation is not necessary. When the GetOpenFileName or GetSaveFileName function returns successfully, this buffer contains the drive designator, path, file name, and extension of the selected file
        public LPWSTR lpstrFile;
        /// The size, in characters, of the buffer pointed to by lpstrFile. The buffer must be large enough to store the path and file name string or strings, including the terminating NULL character. The GetOpenFileName and GetSaveFileName functions return FALSE if the buffer is too small to contain the file information. The buffer should be at least 256 characters long
        public DWORD nMaxFile;
        /// The file name and extension (without path information) of the selected file. This member can be NULL
        public LPWSTR lpstrFileTitle;
        /// The size, in characters, of the buffer pointed to by lpstrFileTitle. This member is ignored if lpstrFileTitle is NULL
        public DWORD nMaxFileTitle;
        /// The initial directory. The algorithm for selecting the initial directory varies on different platforms
        public LPCWSTR lpstrInitialDir;
        /// A string to be placed in the title bar of the dialog box. If this member is NULL, the system uses the default title (that is, Save As or Open)
        public LPCWSTR lpstrTitle;
        /// A set of bit flags you can use to initialise the dialog box. When the dialog box returns, it sets these flags to indicate the user's input. This member can be a combination of [the following flags](https://learn.microsoft.com/en-us/windows/win32/api/commdlg/ns-commdlg-openfilenamew)
        public DWORD Flags;
        /// The zero-based offset, in characters, from the beginning of the path to the file name in the string pointed to by lpstrFile. For the ANSI version, this is the number of bytes; for the Unicode version, this is the number of characters
        public WORD nFileOffset;
        /// The zero-based offset, in characters, from the beginning of the path to the file name extension in the string pointed to by lpstrFile. For the ANSI version, this is the number of bytes; for the Unicode version, this is the number of characters
        public WORD nFileExtension;
        /// The default extension. GetOpenFileName and GetSaveFileName append this extension to the file name if the user fails to type an extension. This string can be any length, but only the first three characters are appended. The string should not contain a period (.). If this member is NULL and the user fails to type an extension, no extension is appended
        public LPCWSTR lpstrDefExt;
        /// Application-defined data that the system passes to the hook procedure identified by the lpfnHook member
        public LPARAM lCustData;
        /// A pointer to a hook procedure. This member is ignored unless the Flags member includes the OFN_ENABLEHOOK flag
        public LPOFNHOOKPROC lpfnHook;
        /// The name of the dialogue template resource in the module identified by the hInstance member. For numbered dialog box resources, this can be a value returned by the {@link WinUser#MAKEINTRESOURCEW(int)} macro
        public LPCWSTR lpTemplateName;
        /// This member is reserved
        public LPVOID pvReserved;
        /// This member is reserved
        public DWORD dwReserved;
        /// A set of bit flags you can use to initialise the dialog box. Currently, this member can be zero or {@code OFN_EX_NOPLACESBAR}
        public DWORD FlagsEx;
    }
}
