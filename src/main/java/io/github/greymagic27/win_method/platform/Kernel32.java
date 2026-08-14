package io.github.greymagic27.win_method.platform;

import io.github.greymagic27.win_method.IntSafe.DWORD;
import io.github.greymagic27.win_method.Library;
import io.github.greymagic27.win_method.WinDef.BOOL;
import io.github.greymagic27.win_method.WinDef.HMODULE;
import io.github.greymagic27.win_method.WinDef.LPCVOID;
import io.github.greymagic27.win_method.WinDef.LPDWORD;
import io.github.greymagic27.win_method.WinDef.LPVOID;
import io.github.greymagic27.win_method.WinNT.HANDLE;
import io.github.greymagic27.win_method.WinNT.LPCWSTR;
import io.github.greymagic27.win_method.WinNT.LPWSTR;
import io.github.greymagic27.win_method.WinNT.PHANDLE;
import io.github.greymagic27.win_method.types.MinWinBase;
import io.github.greymagic27.win_method.types.ProcessThreadsApi;

/// Interface for Kernel32.dll
public interface Kernel32 extends Library {
    /// The instance
    Kernel32 INSTANCE = Library.load(Kernel32.class);

    /// Retrieves a pseudo handle for the current process
    ///
    /// @return Pseudo handle to the current process
    HANDLE GetCurrentProcess();

    /// Searches a directory for a file or subdirectory with a name that matches a specific name (or partial name if wildcards are used)
    ///
    /// @param lpFileName     The directory or path, and the file name. this should not be NULL and can include wildcard characters
    /// @param lpFindFileData A {@link io.github.greymagic27.win_method.Pointer} to the {@link io.github.greymagic27.win_method.types.MinWinBase.WIN32_FIND_DATAW} structure that receives information about a found file or directory
    /// @return If the function succeeds, the return value is a search handle used in a subsequent call to FindNextFile or FindClose, and the <i>lpFindFileData</i> parameter contains information about the first file or directory found. If the function fails the return value is <b>INVALID_HANDLE_VALUE</b> and the contents of <i>lpFindFileData</i> are indeterminate
    HANDLE FindFirstFileW(LPCWSTR lpFileName, MinWinBase.WIN32_FIND_DATAW lpFindFileData);

    /// Creates or opens a file or I/O device
    ///
    /// @param lpFileName            The name of the file or device to be created or opened
    /// @param dwDesiredAccess       The requested access to the file or device. The most common values are {@link io.github.greymagic27.win_method.types.WinNT#GENERIC_READ} and/or {@link io.github.greymagic27.win_method.types.WinNT#GENERIC_WRITE}
    /// @param dwShareMode           The requested sharing mode of the file or device. The list of these can be found [here](https://learn.microsoft.com/en-us/windows/win32/api/fileapi/nf-fileapi-createfilew)
    /// @param lpSecurityAttributes  A {@link io.github.greymagic27.win_method.Pointer} to a {@link io.github.greymagic27.win_method.types.MinWinBase.SECURITY_ATTRIBUTES} structure that contains two separate but related data members. These are an optional security descriptor and a {@link Boolean} value that determines whether the returned handle can be inherited by child processes. This parameter can be NULL
    /// @param dwCreationDisposition An action to take on a file or device that exists or does not exist. This can be one of [the following values](https://learn.microsoft.com/en-us/windows/win32/api/fileapi/nf-fileapi-createfilew)
    /// @param dwFlagsAndAttributes  The file or device attributes, and flags. This can be one of [the following values](https://learn.microsoft.com/en-us/windows/win32/api/fileapi/nf-fileapi-createfilew)
    /// @param hTemplateFile         A valid {@link HANDLE} to a template file with {@link io.github.greymagic27.win_method.types.WinNT#GENERIC_READ} access right. This can be NULL
    /// @return If the function succeeds, the return value is an open handle to the specified file, device, named pipe, or mail slot. If the function fails, the return value is INVALID_HANDLE_VALUE
    HANDLE CreateFileW(LPCWSTR lpFileName, DWORD dwDesiredAccess, DWORD dwShareMode, MinWinBase.SECURITY_ATTRIBUTES lpSecurityAttributes, DWORD dwCreationDisposition, DWORD dwFlagsAndAttributes, HANDLE hTemplateFile);

    /// Retrieves a module handle for the specified module. The module must have been loaded by the calling process
    ///
    /// @param lpModuleName Name of the loaded module (either a .dll for .exe file)
    /// @return Handle to the specified module. If the function fails, this will be NULL
    HMODULE GetModuleHandleW(LPCWSTR lpModuleName);

    /// Loads the specified module into the address space of the calling process. The specified module may cause other modules to be loaded
    ///
    /// @param lpLibFileName The name of the module. This can be a module name or an executable
    /// @return If the function succeeds, the return is a {@link HANDLE} to the module. If the function fails, the return value is NULL
    HMODULE LoadLibraryW(LPCWSTR lpLibFileName);

    /// Continues a file search from a previous call to the {@link FindFirstFileW}, FindFirstFileEx, or FindFirstFileTransacted functions
    ///
    /// @param hFindFile      The search handle returned by a previous call to the {@link FindFirstFileW} or FindFirstFileEx function
    /// @param lpFindFileData A {@link io.github.greymagic27.win_method.Pointer} to the {@link io.github.greymagic27.win_method.types.MinWinBase.WIN32_FIND_DATAW} structure that receives information about the found file or subdirectory
    /// @return If the function succeeds, the return value is nonzero and the <i>lpFindFileData</i> parameter contains information about the next file or directory found. If the function fails, the return value is zero and the contents of <i>lpFindFileData</i> are indeterminate
    BOOL FindNextFileW(HANDLE hFindFile, MinWinBase.WIN32_FIND_DATAW lpFindFileData);

    /// Closes a file search handle opened by the {@link FindFirstFileW}, FindFirstFileEx, FindFirstFileNameW, FindFirstFileNameTransactedW, FindFirstFileTransacted, FindFirstStreamTransactedW, or FindFirstStreamW functions
    ///
    /// @param hFindFile The file search handle
    /// @return If the function succeeds, the return value is nonzero. If the function fails, the return value is zero
    BOOL FindClose(HANDLE hFindFile);

    /// Reads data from the specified file or input/output (I/O) device. Reads occur at the position specified by the file pointer if supported by the device
    ///
    /// @param hFile                A {@link HANDLE} to the device (for example, a file, file stream, physical disk, volume, console buffer, tape drive, socket, communications resource, mailslot, or pipe)
    /// @param lpBuffer             A {@link io.github.greymagic27.win_method.Pointer} to the buffer that receives the data read from a file or device
    /// @param nNumberOfBytesToRead The maximum number of bytes to be read
    /// @param lpNumberOfBytesRead  A {@link io.github.greymagic27.win_method.Pointer} to the variable that receives the number of bytes read when using a synchronous hFile parameter. {@link #ReadFile(HANDLE, LPVOID, DWORD, LPDWORD, MinWinBase.OVERLAPPED)} sets this value to zero before doing any work or error checking. Use NULL for this parameter if this is an asynchronous operation to avoid potentially erroneous results. This can only be NULL when the <i>lpOverlapped</i> parameter is not NULL
    /// @param lpOverlapped         A {@link io.github.greymagic27.win_method.Pointer} to an {@link io.github.greymagic27.win_method.types.MinWinBase.OVERLAPPED} structure is required if the hFile parameter was opened with FILE_FLAG_OVERLAPPED, otherwise it can be NULL
    /// @return If the function succeeds, the return value is nonzero <b>(TRUE)</b>. If the function fails, the return value is zero <b>(FALSE)</b>
    BOOL ReadFile(HANDLE hFile, LPVOID lpBuffer, DWORD nNumberOfBytesToRead, LPDWORD lpNumberOfBytesRead, MinWinBase.OVERLAPPED lpOverlapped);

    /// Writes data to the specified file or input/output (I/O) device
    ///
    /// @param hFile                  A {@link HANDLE} to the file or I/O device (for example, a file, file stream, physical disk, volume, console buffer, tape drive, socket, communications resource, mailslot, or pipe
    /// @param lpBuffer               A {@link io.github.greymagic27.win_method.Pointer} to the buffer containing the data to be written to the file or device
    /// @param nNumberOfBytesToWrite  The number of bytes to be written to the file or device
    /// @param lpNumberOfBytesWritten A {@link io.github.greymagic27.win_method.Pointer} to the variable that receives the number of bytes written when using a synchronous hFile parameter. {@link #WriteFile(HANDLE, LPCVOID, DWORD, LPDWORD, MinWinBase.OVERLAPPED)} sets this value to zero before doing any work or error checking. Use NULL for this parameter if this is an asynchronous operation to avoid potentially erroneous results. This parameter can be NULL only when the lpOverlapped parameter is not NULL
    /// @param lpOverlapped           A {@link io.github.greymagic27.win_method.Pointer} to an {@link io.github.greymagic27.win_method.types.MinWinBase.OVERLAPPED} structure is required if the hFile parameter was opened with FILE_FLAG_OVERLAPPED, otherwise it can be NULL
    /// @return If the function succeeds, the return value is nonzero <b>(TRUE)</b>. If the function fails, the return value is zero <b>(FALSE)</b>
    BOOL WriteFile(HANDLE hFile, LPCVOID lpBuffer, DWORD nNumberOfBytesToWrite, LPDWORD lpNumberOfBytesWritten, MinWinBase.OVERLAPPED lpOverlapped);

    /// Closes an open object {@link HANDLE}
    ///
    /// @param hObject A valid {@link HANDLE} to an open object
    /// @return If the function succeeds, the return value is nonzero. If the function fails, the return value is zero
    BOOL CloseHandle(HANDLE hObject);

    /// Creates a new directory. If the underlying file system supports security on files and directories, the function applies a specified security descriptor to the new directory
    ///
    /// @param lpPathName           The path of the directory to be created
    /// @param lpSecurityAttributes A pointer to a {@link io.github.greymagic27.win_method.types.MinWinBase.SECURITY_ATTRIBUTES} structure. The lpSecurityDescriptor member of the structure specifies a security descriptor for the new directory. If lpSecurityAttributes is NULL, the directory gets a default security descriptor
    /// @return If the function succeeds, the return value is nonzero. If the function fails, the return value is zero
    BOOL CreateDirectoryW(LPCWSTR lpPathName, MinWinBase.SECURITY_ATTRIBUTES lpSecurityAttributes);

    /// Creates an anonymous pipe, and returns handles to the read and write ends of the pipe
    ///
    /// @param hReadPipe        A {@link io.github.greymagic27.win_method.Pointer} to a variable that receives the read handle for the pipe
    /// @param hWritePipe       A {@link io.github.greymagic27.win_method.Pointer} to a variable that receives the write handle for the pipe
    /// @param lpPipeAttributes A {@link io.github.greymagic27.win_method.Pointer} to a {@link io.github.greymagic27.win_method.types.MinWinBase.SECURITY_ATTRIBUTES} structure that determines whether the returned handle can be inherited by child processes. If lpPipeAttributes is NULL, the handle cannot be inherited
    /// @param nSize            The size of the buffer for the pipe, in bytes
    /// @return If the function succeeds, the return value is nonzero.  If the function fails, the return value is zero
    BOOL CreatePipe(PHANDLE hReadPipe, PHANDLE hWritePipe, MinWinBase.SECURITY_ATTRIBUTES lpPipeAttributes, DWORD nSize);

    /// Sets certain properties of an object handle
    ///
    /// @param hObject A {@link HANDLE} to an object whose information is to be set
    /// @param dwMask  A mask that specifies the bit flags to be changed. This can be one of [the following values](https://learn.microsoft.com/en-us/windows/win32/api/handleapi/nf-handleapi-sethandleinformation)
    /// @param dwFlags Set of bit flags that specifies properties of the object handle. This parameter can be 0 or one or more of [the following values](https://learn.microsoft.com/en-us/windows/win32/api/handleapi/nf-handleapi-sethandleinformation)
    /// @return If the function succeeds, the return value is nonzero. If the function fails, the return value is zero
    BOOL SetHandleInformation(HANDLE hObject, DWORD dwMask, DWORD dwFlags);

    /// Creates a new process and its primary thread. The new process runs in the security context of the calling process
    ///
    /// @param lpApplicationName    The name of the module to be executed
    /// @param lpCommandLine        The command line to be executed. The maximum length of this string is 32,767 characters including the terminating null character
    /// @param lpProcessAttributes  A {@link io.github.greymagic27.win_method.Pointer} to a {@link io.github.greymagic27.win_method.types.MinWinBase.SECURITY_ATTRIBUTES} structure that determines whether the returned handle to the new process object can be inherited by child processes. If lpProcessAttributes is NULL, the handle cannot be inherited
    /// @param lpThreadAttributes   A {@link io.github.greymagic27.win_method.Pointer} to a {@link io.github.greymagic27.win_method.types.MinWinBase.SECURITY_ATTRIBUTES} structure that specifies a security descriptor for the new thread and determines whether child processes can inherit the returned handle. If lpThreadAttributes is NULL, the thread gets a default security descriptor and the handle cannot be inherited
    /// @param bInheritHandles      If this parameter is TRUE, each inheritable handle in the calling process is inherited by the new process. If the parameter is FALSE, the handles are not inherited
    /// @param dwCreationFlags      The flags that control the priority class and the creation of the process. For a list of values, see [Process Creation Flags](https://learn.microsoft.com/en-us/windows/desktop/ProcThread/process-creation-flags)
    /// @param lpEnvironment        A {@link  io.github.greymagic27.win_method.Pointer} to the environment block for the new process. If this parameter is NULL, the new process uses the environment of the calling process
    /// @param lpCurrentDirectory   The full path to the current directory for the process. The string can also specify a UNC path
    /// @param lpStartupInfo        A {@link io.github.greymagic27.win_method.Pointer} to a {@link io.github.greymagic27.win_method.types.ProcessThreadsApi.STARTUPINFOW} or STARTUPINFOEX structure
    /// @param lpProcessInformation A {@link io.github.greymagic27.win_method.Pointer} to a {@link io.github.greymagic27.win_method.types.ProcessThreadsApi.PROCESS_INFORMATION} structure that receives identification information about the new process
    /// @return If the function succeeds, the return value is nonzero. If the function fails, the return value is zero
    BOOL CreateProcessW(LPCWSTR lpApplicationName, LPWSTR lpCommandLine, MinWinBase.SECURITY_ATTRIBUTES lpProcessAttributes, MinWinBase.SECURITY_ATTRIBUTES lpThreadAttributes, BOOL bInheritHandles, DWORD dwCreationFlags, LPVOID lpEnvironment, LPCWSTR lpCurrentDirectory, ProcessThreadsApi.STARTUPINFOW lpStartupInfo, ProcessThreadsApi.PROCESS_INFORMATION lpProcessInformation);

    /// Retrieves the size of the specified file, in bytes
    ///
    /// @param hFile          A {@link HANDLE} to the file
    /// @param lpFileSizeHigh A {@link io.github.greymagic27.win_method.Pointer} to the variable where the high-order doubleword of the file size is returned. This parameter can be NULL if the application does not require the high-order doubleword
    /// @return If the function succeeds, the return value is the low-order doubleword of the file size, and, if lpFileSizeHigh is non-NULL, the function puts the high-order doubleword of the file size into the variable pointed to by that parameter. If the function fails and lpFileSizeHigh is NULL, the return value is INVALID_FILE_SIZE
    DWORD GetFileSize(HANDLE hFile, LPDWORD lpFileSizeHigh);

    /// Waits until the specified object is in the signalled state or the time-out interval elapses
    ///
    /// @param hHandle A {@link HANDLE} to the object
    /// @param dwMilliseconds The time-out interval, in millisecond
    /// @return If the function succeeds, the return value indicates the event that caused the function to return. It can be one of [the following values](https://learn.microsoft.com/en-us/windows/win32/api/synchapi/nf-synchapi-waitforsingleobject)
    DWORD WaitForSingleObject(HANDLE hHandle, DWORD dwMilliseconds);

    /// Retrieves the process identifier of the calling process
    ///
    /// @return Process identifier of the calling process
    int GetCurrentProcessId();

    /// Retrieves the process identifier of the specified process.
    ///
    /// @param Process {@link HANDLE} to the process
    /// @return Process identifier. If the function fails, this will be zero
    int GetProcessId(HANDLE Process);

    /// Retrieves the calling thread's last-error code value. The last-error code is maintained on a per-thread basis. Multiple threads do not overwrite each other's last-error code
    ///
    /// @return Return value is the calling thread's last error code
    int GetLastError();
}
