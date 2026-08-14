package io.github.greymagic27.win_method.types;

import io.github.greymagic27.win_method.IntSafe.DWORD;
import io.github.greymagic27.win_method.WinDef.BOOL;
import io.github.greymagic27.win_method.WinDef.LPVOID;
import io.github.greymagic27.win_method.WinNT.LPCWSTR;
import io.github.greymagic27.win_method.WinNT.LPWSTR;

/// Values defined in winbase.h
public interface WinBase {

    /// If this flag is set, a child process created with the bInheritHandles parameter of {@link io.github.greymagic27.win_method.platform.Kernel32#CreateProcessW(LPCWSTR, LPWSTR, MinWinBase.SECURITY_ATTRIBUTES, MinWinBase.SECURITY_ATTRIBUTES, BOOL, DWORD, LPVOID, LPCWSTR, ProcessThreadsApi.STARTUPINFOW, ProcessThreadsApi.PROCESS_INFORMATION)} set to TRUE will inherit the object handle
    int HANDLE_FLAG_INHERIT = 0x00000001;
    /// The state of the specified object is signalled
    int WAIT_OBJECT_0 = 0x00000000;
}
