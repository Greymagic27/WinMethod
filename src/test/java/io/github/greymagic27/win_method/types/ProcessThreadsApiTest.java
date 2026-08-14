package io.github.greymagic27.win_method.types;

import io.github.greymagic27.win_method.IntSafe.DWORD;
import io.github.greymagic27.win_method.WinDef.LPBYTE;
import io.github.greymagic27.win_method.WinDef.WORD;
import io.github.greymagic27.win_method.WinNT.HANDLE;
import io.github.greymagic27.win_method.WinNT.LPWSTR;
import java.lang.foreign.MemorySegment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProcessThreadsApiTest {

    @Test
    void testStartupInfo() {
        ProcessThreadsApi.STARTUPINFOW startupInfo = new ProcessThreadsApi.STARTUPINFOW();

        startupInfo.cb = new DWORD(123);
        startupInfo.lpReserved = new LPWSTR(MemorySegment.NULL);
        startupInfo.lpDesktop = new LPWSTR(MemorySegment.ofAddress(0x2000));
        startupInfo.lpTitle = new LPWSTR(MemorySegment.ofAddress(0x3000));
        startupInfo.dwX = new DWORD(10);
        startupInfo.dwY = new DWORD(20);
        startupInfo.dwXSize = new DWORD(800);
        startupInfo.dwYSize = new DWORD(600);
        startupInfo.dwXCountChars = new DWORD(80);
        startupInfo.dwYCountChars = new DWORD(25);
        startupInfo.dwFillAttribute = new DWORD(7);
        startupInfo.dwFlags = new DWORD(1);
        startupInfo.wShowWindow = new WORD((short) 5);
        startupInfo.cbReserved2 = new WORD((short) 0);
        startupInfo.lpReserved2 = new LPBYTE(MemorySegment.NULL);
        startupInfo.hStdInput = new HANDLE(MemorySegment.ofAddress(0x4000));
        startupInfo.hStdOutput = new HANDLE(MemorySegment.ofAddress(0x5000));
        startupInfo.hStdError = new HANDLE(MemorySegment.ofAddress(0x6000));
        startupInfo.write();
        startupInfo.cb = new DWORD(0);
        startupInfo.lpReserved = new LPWSTR(MemorySegment.NULL);
        startupInfo.lpDesktop = new LPWSTR(MemorySegment.NULL);
        startupInfo.lpTitle = new LPWSTR(MemorySegment.NULL);
        startupInfo.dwX = new DWORD(0);
        startupInfo.dwY = new DWORD(0);
        startupInfo.dwXSize = new DWORD(0);
        startupInfo.dwYSize = new DWORD(0);
        startupInfo.dwXCountChars = new DWORD(0);
        startupInfo.dwYCountChars = new DWORD(0);
        startupInfo.dwFillAttribute = new DWORD(0);
        startupInfo.dwFlags = new DWORD(0);
        startupInfo.wShowWindow = new WORD((short) 0);
        startupInfo.cbReserved2 = new WORD((short) 0);
        startupInfo.lpReserved2 = new LPBYTE(MemorySegment.NULL);
        startupInfo.hStdInput = new HANDLE(MemorySegment.NULL);
        startupInfo.hStdOutput = new HANDLE(MemorySegment.NULL);
        startupInfo.hStdError = new HANDLE(MemorySegment.NULL);
        startupInfo.read();
        assertEquals(123, startupInfo.cb.intValue());
        assertEquals(0, startupInfo.lpReserved.segment.address());
        assertEquals(0x2000L, startupInfo.lpDesktop.segment.address());
        assertEquals(0x3000L, startupInfo.lpTitle.segment.address());
        assertEquals(10, startupInfo.dwX.intValue());
        assertEquals(20, startupInfo.dwY.intValue());
        assertEquals(800, startupInfo.dwXSize.intValue());
        assertEquals(600, startupInfo.dwYSize.intValue());
        assertEquals(80, startupInfo.dwXCountChars.intValue());
        assertEquals(25, startupInfo.dwYCountChars.intValue());
        assertEquals(7, startupInfo.dwFillAttribute.intValue());
        assertEquals(1, startupInfo.dwFlags.intValue());
        assertEquals(5, startupInfo.wShowWindow.shortValue());
        assertEquals(0, startupInfo.cbReserved2.shortValue());
        assertEquals(0, startupInfo.lpReserved2.segment.address());
        assertEquals(0x4000L, startupInfo.hStdInput.segment.address());
        assertEquals(0x5000L, startupInfo.hStdOutput.segment.address());
        assertEquals(0x6000L, startupInfo.hStdError.segment.address());
    }

    @Test
    void testProcessInformation() {
        ProcessThreadsApi.PROCESS_INFORMATION processInfo = new ProcessThreadsApi.PROCESS_INFORMATION();
        processInfo.hProcess = new HANDLE(MemorySegment.ofAddress(0x1000));
        processInfo.hThread = new HANDLE(MemorySegment.ofAddress(0x2000));
        processInfo.dwProcessId = new DWORD(1234);
        processInfo.dwThreadId = new DWORD(5678);
        processInfo.write();
        processInfo.hProcess = new HANDLE(MemorySegment.NULL);
        processInfo.hThread = new HANDLE(MemorySegment.NULL);
        processInfo.dwProcessId = new DWORD(0);
        processInfo.dwThreadId = new DWORD(0);
        processInfo.read();
        assertEquals(0x1000L, processInfo.hProcess.segment.address());
        assertEquals(0x2000L, processInfo.hThread.segment.address());
        assertEquals(1234, processInfo.dwProcessId.intValue());
        assertEquals(5678, processInfo.dwThreadId.intValue());
    }
}