package io.github.greymagic27.win_method.platform;

import io.github.greymagic27.win_method.Library;
import io.github.greymagic27.win_method.WinDef.HMODULE;
import io.github.greymagic27.win_method.WinNT.HANDLE;

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
     * Loads the specified module into the address space of the calling process. The specified module may cause other modules to be loaded
     * @param lpLibFileName The name of the module. This can be a module name or an executable
     * @return If the function succeeds, the return is a {@link HANDLE} to the module. If the function fails, the return value is NULL
     */
    HMODULE LoadLibraryW(String lpLibFileName);

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

    /**
     * Retrieves the calling thread's last-error code value. The last-error code is maintained on a per-thread basis. Multiple threads do not overwrite each other's last-error code
     *
     * @return Return value is the calling thread's last error code
     */
    int GetLastError();
}
