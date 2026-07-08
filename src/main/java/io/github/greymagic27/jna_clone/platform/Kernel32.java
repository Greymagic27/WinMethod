package io.github.greymagic27.jna_clone.platform;

import io.github.greymagic27.jna_clone.Library;
import io.github.greymagic27.jna_clone.WinDef.HMODULE;
import io.github.greymagic27.jna_clone.WinNT.HANDLE;

public interface Kernel32 extends Library {
    Kernel32 INSTANCE = Library.load(Kernel32.class);

    int GetCurrentProcessId();

    int GetProcessId(HANDLE Process);

    HANDLE GetCurrentProcess();

    HMODULE GetModuleHandleW(String lpModuleName);
}
