package io.github.greymagic27.win_method.types;

import io.github.greymagic27.win_method.BaseTsd.ULONG_PTR;
import io.github.greymagic27.win_method.IntSafe.DWORD;
import io.github.greymagic27.win_method.Structure;
import io.github.greymagic27.win_method.Union;
import io.github.greymagic27.win_method.WinDef.BOOL;
import io.github.greymagic27.win_method.WinDef.LPCVOID;
import io.github.greymagic27.win_method.WinDef.LPDWORD;
import io.github.greymagic27.win_method.WinDef.LPVOID;
import io.github.greymagic27.win_method.WinNT.HANDLE;
import io.github.greymagic27.win_method.WinNT.LPCWSTR;
import io.github.greymagic27.win_method.WinNT.PVOID;
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
        /// A {@link FILETIME} structure. For a file, the structure specifies when the file was last written to, truncated, or overwritten, for example, when {@link io.github.greymagic27.win_method.platform.Kernel32#WriteFile(HANDLE, LPCVOID, DWORD, LPDWORD, OVERLAPPED)} or SetEndOfFile are used. For a directory, the structure specifies when the directory is created
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

    /// The SECURITY_ATTRIBUTES structure contains the security descriptor for an object and specifies whether the handle retrieved by specifying this structure is inheritable
    @Structure.AutoFieldOrder
    class SECURITY_ATTRIBUTES extends Structure {
        /// The size, in bytes, of this structure. Set this value to the size of the {@link SECURITY_ATTRIBUTES} structure
        public DWORD nLength;
        /// A {@link io.github.greymagic27.win_method.Pointer} to a <b>SECURITY_DESCRIPTOR</b> structure that controls access to the object. This can be NULL
        public LPVOID lpSecurityDescriptor;
        /// A {@link Boolean} value that specifies whether the returned handle is inherited when a new process is created. If this is <b>TRUE</b>, the new process inherits the handle
        public BOOL bInheritHandle;
    }

    /// Contains information used in asynchronous (or overlapped) input and output (I/O)
    @Structure.AutoFieldOrder
    class OVERLAPPED extends Structure {
        /// The status code for the I/O request
        public ULONG_PTR Internal;
        /// The number of bytes transferred for the I/O request
        public ULONG_PTR InternalHigh;
        /// Contains the file offset or reserved pointer for the I/O request
        public DUMMYUNIONNAME dummyunionname;
        /// A {@link HANDLE} to the event that will be set to a signalled state by the system when the operation has completed. The user must initialise this member either to zero or a valid event handle using the CreateEvent function before passing this structure to any overlapped functions
        public HANDLE hEvent;

        /// Contains the low-order and high-order portions of the file position for an overlapped I/O request
        @AutoFieldOrder
        static class DUMMYSTRUCTNAME extends Structure {
            /// The low-order portion of the file position at which to start the I/O request, as specified by the user
            DWORD Offset;
            /// The high-order portion of the file position at which to start the I/O request, as specified by the user
            DWORD OffsetHigh;
        }

        /// Contains the file offset or reserved pointer associated with an overlapped I/O operation
        @AutoFieldOrder
        public static class DUMMYUNIONNAME extends Union {
            DUMMYSTRUCTNAME dummystructname;
            /// Reserved for system use; do not use after initialisation to zero
            PVOID Pointer;
        }
    }
}
