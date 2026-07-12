package io.github.greymagic27.jna_clone.platform;

import io.github.greymagic27.jna_clone.Library;
import io.github.greymagic27.jna_clone.WinDef.HMODULE;
import io.github.greymagic27.jna_clone.WinNT.HANDLE;

/**
 * Interface for Kernel32.dll
 */
public interface Kernel32 extends Library {
    /**
     * The instance
     */
    Kernel32 INSTANCE = Library.load(Kernel32.class);

    /**
     * Retrieves a pseudo handle for the current process
     *
     * @return Pseudo handle to the current process
     */
    HANDLE GetCurrentProcess();

    /**
     * Retrieves a module handle for the specified module. The module must have been loaded by the calling process
     *
     * @param lpModuleName Name of the loaded module (either a .dll for .exe file)
     * @return Handle to the specified module. If the function fails, this will be NULL
     */
    HMODULE GetModuleHandleW(String lpModuleName);

    /**
     * Retrieves the process identifier of the calling process
     *
     * @return Process identifier of the calling process
     */
    int GetCurrentProcessId();

    /**
     * Retrieves the process identifier of the specified process.
     *
     * @param Process {@link HANDLE} to the process
     * @return Process identifier. If the function fails, this will be zero
     */
    int GetProcessId(HANDLE Process);
}
