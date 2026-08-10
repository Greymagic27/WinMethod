package io.github.greymagic27.win_method.types;

import io.github.greymagic27.win_method.IntSafe.DWORD;
import io.github.greymagic27.win_method.Structure;
import io.github.greymagic27.win_method.WinNT.LPCWSTR;
import io.github.greymagic27.win_method.WinNT.WCHAR;

/// Values defined in minwinbase.h
public interface MinWinBase {

    /// Contains information about the file that is found by the {@link io.github.greymagic27.win_method.platform.Kernel32#FindFirstFileW(LPCWSTR, WIN32_FIND_DATAW)}, FindFirstFileEx, or FindNextFile function
    @Structure.AutoFieldOrder
    class WIN32_FIND_DATAW extends Structure {
        /// The file attributes of a file
        public DWORD dwFileAttributes;
        /// A {@link FILETIME} structure that specifies when a file or directory was created
        public FILETIME ftCreationTime;
        /// A {@link FILETIME} structure. For a file, the structure specifies when the file was last read from or written to. For a directory, the structure specifies when the directory is created
        public FILETIME ftLastAccessTime;
        /// A {@link FILETIME} structure. For a file, the structure specifies when the file was last written to, truncated, or overwritten, for example, when WriteFile or SetEndOfFile are used. For a directory, the structure specifies when the directory is created
        public FILETIME ftLastWriteTime;
        /// The high-order {@link DWORD} value of the file size, in bytes
        public DWORD nFileSizeHigh;
        /// The low-order {@link DWORD} value of the file size, in bytes
        public DWORD nFileSizeLow;
        /// If the dwFileAttributes member includes the FILE_ATTRIBUTE_REPARSE_POINT attribute, this member specifies the reparse point tag
        public DWORD dwReserved0;
        /// Reserved for future use
        public DWORD dwReserved1;
        /// The name of the file
        @ArrayLength(260)
        public WCHAR[] cFileName;
        /// An alternative name for the file
        @ArrayLength(14)
        public WCHAR[] cAlternateFileName;
    }

    /// Contains a 64-bit value representing the number of 100-nanosecond intervals since January 1, 1601 (UTC)
    @Structure.AutoFieldOrder
    class FILETIME extends Structure {
        /// The low-order part of the file time
        public DWORD dwLowDateTime;
        /// The high-order part of the file time
        public DWORD dwHighDateTime;
    }
}
