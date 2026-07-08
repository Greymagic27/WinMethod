package io.github.greymagic27.jna_clone;

import io.github.greymagic27.jna_clone.WinNT.HANDLE;
import io.github.greymagic27.jna_clone.platform.Kernel32;
import java.lang.reflect.Proxy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LibraryTest {

    Kernel32 kernel32WithLibrary;
    Kernel32 kernel32WithoutLibrary;

    @BeforeEach
    void setUp() {
        kernel32WithLibrary = Library.load("kernel32", Kernel32.class);
        kernel32WithoutLibrary = Library.load(Kernel32.class);
    }

    @Test
    void testLoadAndInvoke() {
        assertNotNull(kernel32WithLibrary, "Proxy should not be null");
        assertTrue(Proxy.isProxyClass(kernel32WithLibrary.getClass()), "Returned object should be a proxy");
        int pid = kernel32WithLibrary.GetCurrentProcessId();
        assertTrue(pid > 0, "Process ID should be valid and non-zero");
        HANDLE processHandle = kernel32WithLibrary.GetCurrentProcess();
        assertNotNull(processHandle, "Process handle should not be null");
        int pidFromHandle = kernel32WithLibrary.GetProcessId(processHandle);
        assertEquals(pid, pidFromHandle, "PID from handle should match GetCurrentProcessId()");
    }

    @Test
    void testLoadAndInvokeNoLibraryName() {
        assertNotNull(kernel32WithoutLibrary, "Proxy should not be null");
        assertTrue(Proxy.isProxyClass(kernel32WithoutLibrary.getClass()), "Returned object should be a proxy");
        int pid = kernel32WithoutLibrary.GetCurrentProcessId();
        assertTrue(pid > 0, "Process ID should be valid and non-zero");
        HANDLE processHandle = kernel32WithoutLibrary.GetCurrentProcess();
        assertNotNull(processHandle, "Process handle should not be null");
        int pidFromHandle = kernel32WithoutLibrary.GetProcessId(processHandle);
        assertEquals(pid, pidFromHandle, "PID from handle should match GetCurrentProcessId()");
    }

    @Test
    void testObjectMethods() {
        String toString = kernel32WithLibrary.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("NativeLibrary"), "toString should be handled by the NativeLibrary");
    }

    @Test
    void testObjectMethodsWithoutLibrary() {
        String toString = kernel32WithoutLibrary.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("NativeLibrary"), "toString should be handled by the NativeLibrary");
    }

    @Test
    void testLoadFailsForMissingLibrary() {
        assertThrows(IllegalArgumentException.class, () -> Library.load("non_existent", Kernel32.class), "Should throw exception if native library cannot be found");
    }

    @Test
    void testLoadFailsForMissingLibraryNoLibrarySpecified() {
        interface non_existent extends Library {
        }
        assertThrows(IllegalArgumentException.class, () -> Library.load(non_existent.class), "Should throw exception if native library cannot be found");
    }

    @Test
    void testNullArgumentHandling() {
        Pointer result = kernel32WithLibrary.GetModuleHandleW(null);
        assertNotNull(result, "Returned pointer should not be null");
        assertFalse(result.isNull(), "Process handle should be a valid memory address");
    }

    @Test
    void testNullArgumentHandlingWithoutLibrary() {
        Pointer result = kernel32WithoutLibrary.GetModuleHandleW(null);
        assertNotNull(result, "Returned pointer should not be null");
        assertFalse(result.isNull(), "Process handle should be a valid memory address");
    }

    @Test
    void testUnsupportedMethodType() {
        interface GDI32 extends Library {
            void ChoosePixelFormat(String[] list);
        }
        GDI32 lib = Library.load("gdi32", GDI32.class);
        assertThrows(IllegalArgumentException.class, () -> lib.ChoosePixelFormat(null));
    }

    @Test
    void testUnsupportedClassAsLibrary() {
        interface Invalid extends Library {
        }
        assertThrows(IllegalArgumentException.class, () -> Library.load(Invalid.class));
    }

    @Test
    void testMultipleInstances() {
        Kernel32 first = Library.load(Kernel32.class);
        Kernel32 second = Library.load(Kernel32.class);
        assertNotSame(first, second);
        assertEquals(first.GetCurrentProcessId(), second.GetCurrentProcessId());
    }
}