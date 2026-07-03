package io.github.greymagic27.jna_clone;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NativeLibraryTest {

    private NativeLibrary lib;

    @BeforeEach
    void setUp() {
        lib = new NativeLibrary("kernel32");
    }

    @Test
    void testHandleCaching() throws NoSuchMethodException {
        Method method = MockFunctions.class.getMethod("SetLastError", int.class);
        MethodHandle handle1 = lib.handleFor(method);
        MethodHandle handle2 = lib.handleFor(method);
        assertNotNull(handle1);
        assertNotNull(handle2);
        assertSame(handle1, handle2, "Handles should be cached and returned as the same instance");
    }

    @Test
    void testSymbolNotFound() throws NoSuchMethodException {
        Method method = MockFunctions.class.getMethod("NonExistentFunction");
        assertThrows(UnsatisfiedLinkError.class, () -> lib.handleFor(method), "Should throw UnsatisfiedLinkError when function does not exist");
    }

    @Test
    void testLibraryNotFound() {
        assertThrows(IllegalArgumentException.class, () -> new NativeLibrary("non_existent"), "Should throw exception when library cannot be loaded");
    }

    @Test
    void testMapLibraryNameLogic() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method mapper = NativeLibrary.class.getDeclaredMethod("mapLibraryName", String.class);
        mapper.setAccessible(true);
        assertEquals("opengl32.dll", mapper.invoke(lib, "opengl32"));
        assertEquals("opengl32.dll", mapper.invoke(lib, "opengl32.dll"));
        assertEquals("user32.dll", mapper.invoke(lib, "user32"));
        assertEquals("user32.dll", mapper.invoke(lib, "user32.dll"));
        assertEquals("test*.dll", mapper.invoke(lib, "test*.dll"));
        assertEquals("test*.dll", mapper.invoke(lib, "test*"));
    }

    interface MockFunctions {
        void SetLastError(int dwErrCode);

        void NonExistentFunction();
    }
}
