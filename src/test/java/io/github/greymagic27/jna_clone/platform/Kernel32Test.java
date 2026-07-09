package io.github.greymagic27.jna_clone.platform;

import io.github.greymagic27.jna_clone.WinDef.HMODULE;
import io.github.greymagic27.jna_clone.WinNT.HANDLE;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Kernel32Test {

    private final Kernel32 kernel32 = Kernel32.INSTANCE;

    @Test
    void testGetCurrentProcessId() {
        int pid = kernel32.GetCurrentProcessId();
        assertTrue(pid > 0, "PID should be greater than 0");
    }

    @Test
    void testGetProcessId() {
        HANDLE hProcess = kernel32.GetCurrentProcess();
        assertNotNull(hProcess);
        assertFalse(hProcess.isNull());
        int pidFromHandle = kernel32.GetProcessId(hProcess);
        int pidDirect = kernel32.GetCurrentProcessId();
        assertEquals(pidDirect, pidFromHandle, "PID from handle should match the direct PID");
    }

    @Test
    void testGetCurrentProcess() {
        HANDLE hProcess = kernel32.GetCurrentProcess();
        assertNotNull(hProcess);
        assertFalse(hProcess.isNull());
        assertEquals(-1, hProcess.segment.address());
    }

    @Test
    void testGetModuleHandle() {
        HMODULE hmodule = kernel32.GetModuleHandleW(null);
        assertNotNull(hmodule);
        assertFalse(hmodule.isNull(), "handle for current process should not be null");
    }
}