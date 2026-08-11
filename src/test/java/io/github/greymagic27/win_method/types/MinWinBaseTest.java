package io.github.greymagic27.win_method.types;

import io.github.greymagic27.win_method.BaseTsd.ULONG_PTR;
import io.github.greymagic27.win_method.IntSafe.DWORD;
import io.github.greymagic27.win_method.WinDef.BOOL;
import io.github.greymagic27.win_method.WinDef.LPVOID;
import java.lang.foreign.MemorySegment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MinWinBaseTest {

    @Test
    void testWin32FindData() {
        MinWinBase.WIN32_FIND_DATAW data = new MinWinBase.WIN32_FIND_DATAW();
        assertNotNull(data);
        assertNotNull(data.dwFileAttributes);
        assertNotNull(data.ftCreationTime);
        assertNotNull(data.ftLastAccessTime);
        assertNotNull(data.ftLastWriteTime);
        assertNotNull(data.nFileSizeHigh);
        assertNotNull(data.nFileSizeLow);
        assertNotNull(data.dwReserved0);
        assertNotNull(data.dwReserved1);
        assertNotNull(data.cFileName);
        assertNotNull(data.cAlternateFileName);
        assertEquals(260, data.cFileName.length);
        assertEquals(14, data.cAlternateFileName.length);
        data.dwFileAttributes = new DWORD(0x20);
        data.nFileSizeHigh = new DWORD(1);
        data.nFileSizeLow = new DWORD(12345);
        data.dwReserved0 = new DWORD(2);
        data.dwReserved1 = new DWORD(3);
        data.ftCreationTime.dwLowDateTime = new DWORD(10);
        data.ftCreationTime.dwHighDateTime = new DWORD(20);
        data.ftLastAccessTime.dwLowDateTime = new DWORD(30);
        data.ftLastAccessTime.dwHighDateTime = new DWORD(40);
        data.ftLastWriteTime.dwLowDateTime = new DWORD(50);
        data.ftLastWriteTime.dwHighDateTime = new DWORD(60);
        assertEquals(0x20, data.dwFileAttributes.intValue());
        assertEquals(1, data.nFileSizeHigh.intValue());
        assertEquals(12345, data.nFileSizeLow.intValue());
        assertEquals(2, data.dwReserved0.intValue());
        assertEquals(3, data.dwReserved1.intValue());
        assertEquals(10, data.ftCreationTime.dwLowDateTime.intValue());
        assertEquals(20, data.ftCreationTime.dwHighDateTime.intValue());
        assertEquals(30, data.ftLastAccessTime.dwLowDateTime.intValue());
        assertEquals(40, data.ftLastAccessTime.dwHighDateTime.intValue());
        assertEquals(50, data.ftLastWriteTime.dwLowDateTime.intValue());
        assertEquals(60, data.ftLastWriteTime.dwHighDateTime.intValue());
    }

    @Test
    void testFileTime() {
        MinWinBase.FILETIME filetime = new MinWinBase.FILETIME();
        assertNotNull(filetime);
        assertNotNull(filetime.dwLowDateTime);
        assertNotNull(filetime.dwHighDateTime);
        assertEquals(0, filetime.dwLowDateTime.intValue());
        assertEquals(0, filetime.dwHighDateTime.intValue());
        filetime.dwLowDateTime = new DWORD(0x12345678);
        filetime.dwHighDateTime = new DWORD(0x9ABCDEF0);
        assertEquals(0x12345678, filetime.dwLowDateTime.intValue());
        assertEquals(0x9ABCDEF0, filetime.dwHighDateTime.intValue());
    }

    @Test
    void testSecurityAttributes() {
        MinWinBase.SECURITY_ATTRIBUTES attributes = new MinWinBase.SECURITY_ATTRIBUTES();
        assertNotNull(attributes);
        assertNotNull(attributes.nLength);
        assertNotNull(attributes.lpSecurityDescriptor);
        assertNotNull(attributes.bInheritHandle);
        assertEquals(0, attributes.nLength.intValue());
        assertFalse(attributes.bInheritHandle.booleanValue());
        assertEquals(24, attributes.pointer().segment.byteSize());
        attributes.nLength = new DWORD(24);
        attributes.lpSecurityDescriptor = new LPVOID(MemorySegment.ofAddress(0x12345678));
        attributes.bInheritHandle = new BOOL(1);
        assertEquals(24, attributes.nLength.intValue());
        assertEquals(0x12345678L, attributes.lpSecurityDescriptor.segment.address());
        assertTrue(attributes.bInheritHandle.booleanValue());
    }

    @Test
    void testOverlapped() {
        MinWinBase.OVERLAPPED overlapped = new MinWinBase.OVERLAPPED();
        assertNotNull(overlapped);
        assertNotNull(overlapped.Internal);
        assertNotNull(overlapped.InternalHigh);
        assertNotNull(overlapped.dummyunionname);
        assertNotNull(overlapped.hEvent);
        assertEquals(0, overlapped.Internal.longValue());
        assertEquals(0, overlapped.InternalHigh.longValue());
        assertNotNull(overlapped.dummyunionname.dummystructname);
        assertNotNull(overlapped.dummyunionname.Pointer);
        assertEquals(0, overlapped.dummyunionname.dummystructname.Offset.intValue());
        assertEquals(0, overlapped.dummyunionname.dummystructname.OffsetHigh.intValue());
        assertEquals(0, overlapped.hEvent.segment.address());
        overlapped.Internal = new ULONG_PTR(10);
        overlapped.InternalHigh = new ULONG_PTR(20);
        overlapped.dummyunionname.dummystructname.Offset = new DWORD(30);
        overlapped.dummyunionname.dummystructname.OffsetHigh = new DWORD(40);
        assertEquals(10, overlapped.Internal.longValue());
        assertEquals(20, overlapped.InternalHigh.longValue());
        assertEquals(30, overlapped.dummyunionname.dummystructname.Offset.intValue());
        assertEquals(40, overlapped.dummyunionname.dummystructname.OffsetHigh.intValue());
    }

    @Test
    void testOverlappedSizes() {
        assertEquals(8, new MinWinBase.OVERLAPPED.DUMMYSTRUCTNAME().size());
        assertEquals(8, new MinWinBase.OVERLAPPED.DUMMYUNIONNAME().size());
    }
}