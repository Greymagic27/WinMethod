package io.github.greymagic27.jna_clone;

import java.lang.reflect.Proxy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LibraryTest {

    Kernel32 kernel32;

    @BeforeEach
    void setUp() {
        kernel32 = Library.load("kernel32", Kernel32.class);
    }

    @Test
    void testLoadAndInvoke() {
        assertNotNull(kernel32, "Proxy should not be null");
        assertTrue(Proxy.isProxyClass(kernel32.getClass()), "Returned object should be a proxy");
        int pid = kernel32.GetCurrentProcessId();
        assertTrue(pid > 0, "Process ID should be valid and non-zero");
        Pointer processHandle = kernel32.GetCurrentProcess();
        assertNotNull(processHandle, "Process handle should not be null");
        int pidFromHandle = kernel32.GetProcessId(processHandle);
        assertEquals(pid, pidFromHandle, "PID from handle should match GetCurrentProcessId()");
    }

    @Test
    void testObjectMethods() {
        String toString = kernel32.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("NativeLibrary"), "toString should be handled by the NativeLibrary");
    }

    @Test
    void testLoadFailsForMissingLibrary() {
        assertThrows(IllegalArgumentException.class, () -> Library.load("non_existent", Kernel32.class), "Should throw exception if native library cannot be found");
    }

    @Test
    void testNullArgumentHandling() {
        kernel32 = Library.load("kernel32", Kernel32.class);
        Pointer result = kernel32.GetModuleHandleW(null);
        assertNotNull(result, "Returned pointer should not be null");
        assertFalse(result.isNull(), "Process handle should be a valid memory address");
    }

    interface Kernel32 extends Library {
        int GetCurrentProcessId();

        int GetProcessId(Pointer process);

        Pointer GetCurrentProcess();

        Pointer GetModuleHandleW(String lpModuleName);
    }
}