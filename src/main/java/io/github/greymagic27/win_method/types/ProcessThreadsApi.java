package io.github.greymagic27.win_method.types;

import io.github.greymagic27.win_method.IntSafe.DWORD;
import io.github.greymagic27.win_method.Structure;
import io.github.greymagic27.win_method.WinDef.LPBYTE;
import io.github.greymagic27.win_method.WinDef.WORD;
import io.github.greymagic27.win_method.WinNT.HANDLE;
import io.github.greymagic27.win_method.WinNT.LPWSTR;

/// Values defined in processthreadsapi.h
public interface ProcessThreadsApi {

    /// Specifies the window station, desktop, standard handles, and appearance of the main window for a process at creation time
    @Structure.AutoFieldOrder
    class STARTUPINFOW extends Structure {
        /// The size of the structure, in bytes
        public DWORD cb;
        /// Reserved, must be NULL
        public LPWSTR lpReserved;
        /// The name of the desktop, or the name of both the desktop and window station for this process. This is indicated by a backslash in the string
        public LPWSTR lpDesktop;
        /// For console processes, this is the title displayed in the title bar if a new console window is created. If NULL, the name of the executable file is used as the window title instead. This parameter must be NULL for GUI or console processes that do not create a new console window
        public LPWSTR lpTitle;
        /// If dwFlags specifies STARTF_USEPOSITION, this member is the x offset of the upper left corner of a window if a new window is created, in pixels. Otherwise, this member is ignored
        public DWORD dwX;
        /// If dwFlags specifies STARTF_USEPOSITION, this member is the y offset of the upper left corner of a window if a new window is created, in pixels. Otherwise, this member is ignored
        public DWORD dwY;
        /// If dwFlags specifies STARTF_USESIZE, this member is the width of the window if a new window is created, in pixels. Otherwise, this member is ignored
        public DWORD dwXSize;
        /// If dwFlags specifies STARTF_USESIZE, this member is the height of the window if a new window is created, in pixels. Otherwise, this member is ignored
        public DWORD dwYSize;
        /// If dwFlags specifies STARTF_USECOUNTCHARS, if a new console window is created in a console process, this member specifies the screen buffer width, in character columns. Otherwise, this member is ignored
        public DWORD dwXCountChars;
        /// If dwFlags specifies STARTF_USECOUNTCHARS, if a new console window is created in a console process, this member specifies the screen buffer height, in character rows. Otherwise, this member is ignored
        public DWORD dwYCountChars;
        /// If dwFlags specifies STARTF_USEFILLATTRIBUTE, this member is the initial text and background colours if a new console window is created in a console application. Otherwise, this member is ignored
        public DWORD dwFillAttribute;
        /// A bitfield that determines whether certain {@link STARTUPINFOW} members are used when the process creates a window. This member can be one or more of [the following values](https://learn.microsoft.com/en-us/windows/win32/api/processthreadsapi/ns-processthreadsapi-startupinfow)
        public DWORD dwFlags;
        /// If dwFlags specifies STARTF_USESHOWWINDOW, this member can be any of the values that can be specified in the nCmdShow parameter for the ShowWindow function, except for SW_SHOWDEFAULT. Otherwise, this member is ignored
        public WORD wShowWindow;
        /// Reserved for use by the C Run-time; must be zero
        public WORD cbReserved2;
        /// Reserved for use by the C Run-time; must be NULL
        public LPBYTE lpReserved2;
        /// If dwFlags specifies STARTF_USESTDHANDLES, this member is the standard input handle for the process. If STARTF_USESTDHANDLES is not specified, the default for standard input is the keyboard buffer
        public HANDLE hStdInput;
        /// If dwFlags specifies STARTF_USESTDHANDLES, this member is the standard output handle for the process. Otherwise, this member is ignored and the default for standard output is the console window's buffer
        public HANDLE hStdOutput;
        /// If dwFlags specifies STARTF_USESTDHANDLES, this member is the standard error handle for the process. Otherwise, this member is ignored and the default for standard error is the console window's buffer
        public HANDLE hStdError;
    }

    /// Contains information about a newly created process and its primary thread. It is used with the CreateProcess, CreateProcessAsUser, CreateProcessWithLogonW, or CreateProcessWithTokenW function
    @Structure.AutoFieldOrder
    class PROCESS_INFORMATION extends Structure {
        /// A {@link HANDLE} to the newly created process. The handle is used to specify the process in all functions that perform operations on the process object
        HANDLE hProcess;
        /// A {@link HANDLE} to the primary thread of the newly created process. The handle is used to specify the thread in all functions that perform operations on the thread object
        HANDLE hThread;
        /// A value that can be used to identify a process. The value is valid from the time the process is created until all handles to the process are closed and the process object is freed; at this point, the identifier may be reused
        DWORD dwProcessId;
        /// A value that can be used to identify a thread. The value is valid from the time the thread is created until all handles to the thread are closed and the thread object is freed; at this point, the identifier may be reused
        DWORD dwThreadId;
    }
}
