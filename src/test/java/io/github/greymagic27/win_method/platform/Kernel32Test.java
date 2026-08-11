package io.github.greymagic27.win_method.platform;

import io.github.greymagic27.win_method.IntSafe.DWORD;
import io.github.greymagic27.win_method.Memory;
import io.github.greymagic27.win_method.WinDef.BOOL;
import io.github.greymagic27.win_method.WinDef.HMODULE;
import io.github.greymagic27.win_method.WinDef.LPCVOID;
import io.github.greymagic27.win_method.WinDef.LPDWORD;
import io.github.greymagic27.win_method.WinDef.LPVOID;
import io.github.greymagic27.win_method.WinNT.HANDLE;
import io.github.greymagic27.win_method.WinNT.LPCWSTR;
import io.github.greymagic27.win_method.types.MinWinBase;
import java.io.IOException;
import java.lang.foreign.ValueLayout;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import static io.github.greymagic27.win_method.types.Fileapi.CREATE_ALWAYS;
import static io.github.greymagic27.win_method.types.Fileapi.OPEN_EXISTING;
import static io.github.greymagic27.win_method.types.WinNT.FILE_SHARE_READ;
import static io.github.greymagic27.win_method.types.WinNT.GENERIC_READ;
import static io.github.greymagic27.win_method.types.WinNT.GENERIC_WRITE;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Kernel32Test {
    private final Kernel32 kernel32 = Kernel32.INSTANCE;
    @TempDir
    private Path tempDir;
    private Path testFile;

    @BeforeEach
    void setUp() throws IOException {
        testFile = tempDir.resolve("test.txt");
        Files.writeString(testFile, "Test", StandardCharsets.UTF_8);
    }

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

    @Test
    void testGetLastError() {
        HMODULE hmodule = kernel32.GetModuleHandleW(new LPCWSTR("NonExistent.dll"));
        assertTrue(hmodule.isNull());
        assertEquals(126, kernel32.GetLastError());
    }

    @Test
    void testLoadLibrary() {
        HMODULE hmodule = kernel32.LoadLibraryW(new LPCWSTR("kernel32.dll"));
        HMODULE hmodule1 = kernel32.LoadLibraryW(new LPCWSTR("NonExistent"));
        assertNotNull(hmodule);
        assertNotNull(hmodule1);
        assertFalse(hmodule.isNull());
        assertTrue(hmodule1.isNull());
        assertEquals(126, kernel32.GetLastError());
    }

    @Test
    void testFindNextFile() throws IOException {
        Files.createFile(tempDir.resolve("file1.txt"));
        Files.createFile(tempDir.resolve("file2.txt"));
        MinWinBase.WIN32_FIND_DATAW findData = new MinWinBase.WIN32_FIND_DATAW();
        HANDLE hFind = kernel32.FindFirstFileW(new LPCWSTR(tempDir + "\\*"), findData);
        assertNotNull(hFind);
        assertFalse(hFind.isNull());
        try {
            assertTrue(kernel32.FindNextFileW(hFind, findData).booleanValue());
        } finally {
            assertTrue(kernel32.FindClose(hFind).booleanValue());
        }
    }

    @Test
    void testFindClose() {
        HANDLE hFind = findTestDirectory();
        assertNotNull(hFind);
        assertFalse(hFind.isNull());
        assertTrue(kernel32.FindClose(hFind).booleanValue());
    }

    @Test
    void testReadFile() {
        HANDLE hFile = openTestFile();
        assertNotNull(hFile);
        assertFalse(hFile.isNull());
        try {
            byte[] expected = "Test".getBytes(StandardCharsets.UTF_8);
            try (Memory buffer = new Memory(expected.length); Memory bytesRead = new Memory(4)) {
                BOOL result = kernel32.ReadFile(hFile, new LPVOID(buffer.segment), new DWORD(expected.length), new LPDWORD(bytesRead.segment), null);
                assertTrue(result.booleanValue(), "ReadFile should succeed");
                int numberOfBytesRead = bytesRead.segment.get(ValueLayout.JAVA_INT, 0);
                assertEquals(expected.length, numberOfBytesRead);
                byte[] actual = buffer.segment.reinterpret(expected.length).toArray(ValueLayout.JAVA_BYTE);
                assertArrayEquals(expected, actual);
            }
        } finally {
            assertTrue(kernel32.CloseHandle(hFile).booleanValue());
        }
    }

    @Test
    void testWriteFile() throws IOException {
        Path outputFile = tempDir.resolve("test-output.txt");
        HANDLE hFile = kernel32.CreateFileW(new LPCWSTR(outputFile.toString()), new DWORD(GENERIC_WRITE.intValue()), new DWORD(0), null, new DWORD(CREATE_ALWAYS), new DWORD(0), null);
        assertNotNull(hFile);
        assertFalse(hFile.isNull());
        try {
            byte[] expected = "Hello from WriteFile!".getBytes(StandardCharsets.UTF_8);
            try (Memory buffer = new Memory(expected.length); Memory bytesWritten = new Memory(4)) {
                buffer.segment.copyFrom(java.lang.foreign.MemorySegment.ofArray(expected));
                BOOL result = kernel32.WriteFile(hFile, new LPCVOID(buffer.segment), new DWORD(expected.length), new LPDWORD(bytesWritten.segment), null);
                assertTrue(result.booleanValue(), "WriteFile should succeed");
                int numberOfBytesWritten = bytesWritten.segment.get(ValueLayout.JAVA_INT, 0);
                assertEquals(expected.length, numberOfBytesWritten);
            }
        } finally {
            assertTrue(kernel32.CloseHandle(hFile).booleanValue());
        }
        assertEquals("Hello from WriteFile!", Files.readString(outputFile));
    }

    @Test
    void testCloseHandle() {
        HANDLE hFile = openTestFile();
        assertNotNull(hFile);
        assertFalse(hFile.isNull());
        assertTrue(kernel32.CloseHandle(hFile).booleanValue());
    }

    @Test
    void testGetFileSize() throws IOException {
        HANDLE hFile = openTestFile();
        assertNotNull(hFile);
        assertFalse(hFile.isNull());
        try {
            try (Memory fileSizeHigh = new Memory(4)) {
                DWORD fileSizeLow = kernel32.GetFileSize(hFile, new LPDWORD(fileSizeHigh.segment));
                assertEquals(Files.size(testFile), Integer.toUnsignedLong(fileSizeLow.intValue()));
                int high = fileSizeHigh.segment.get(ValueLayout.JAVA_INT, 0);
                assertEquals(0, high, "High DWORD should be zero for a small test file");
            }
        } finally {
            assertTrue(kernel32.CloseHandle(hFile).booleanValue());
        }
    }

    @Test
    void testCreateFile() {
        Path file = tempDir.resolve("DoesNotExist.txt");
        HANDLE hFile = openTestFile();
        HANDLE hFile2 = kernel32.CreateFileW(new LPCWSTR(file.toString()), new DWORD(GENERIC_READ.intValue()), new DWORD(FILE_SHARE_READ), null, new DWORD(OPEN_EXISTING), new DWORD(0), null);
        assertNotNull(hFile);
        assertFalse(hFile.isNull());
        assertTrue(Files.exists(testFile));
        assertTrue(kernel32.CloseHandle(hFile).booleanValue());
        assertNotNull(hFile2);
        assertEquals(-1, hFile2.segment.address());
    }

    private HANDLE openTestFile() {
        return kernel32.CreateFileW(new LPCWSTR(testFile.toString()), new DWORD(GENERIC_READ.intValue()), new DWORD(FILE_SHARE_READ), null, new DWORD(OPEN_EXISTING), new DWORD(0), null);
    }

    private HANDLE findTestDirectory() {
        return kernel32.FindFirstFileW(new LPCWSTR(tempDir + "\\*"), new MinWinBase.WIN32_FIND_DATAW());
    }
}