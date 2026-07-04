package io.github.greymagic27.jna_clone;

import io.github.greymagic27.jna_clone.WinDef.HDC;
import io.github.greymagic27.jna_clone.WinDef.HWND;
import java.lang.foreign.MemorySegment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class HandleTest {

    @Test
    void testHandleToStringIncludesSubclassName() {
        HWND hwnd = new HWND(MemorySegment.ofAddress(0xABCD));
        HDC hdc = new HDC(MemorySegment.ofAddress(0xAF));
        HANDLE handle = new HANDLE(MemorySegment.ofAddress(0xAE));
        assertEquals("HWND@0xabcd", hwnd.toString());
        assertEquals("HDC@0xaf", hdc.toString());
        assertEquals("HANDLE@0xae", handle.toString());
    }

    @Test
    void testSegmentAccess() {
        MemorySegment segment = MemorySegment.ofAddress(0x42);
        HANDLE handle = new HANDLE(segment);
        assertEquals(segment, handle.segment());
    }

    @Test
    void testLongConstructor_MatchesMemorySegmentConstructor() {
        HANDLE fromLong = new HANDLE(0x1234);
        HANDLE fromSegment = new HANDLE(MemorySegment.ofAddress(0x1234));
        assertEquals(fromSegment.segment().address(), fromLong.segment().address());
    }

    @Test
    void testLongConstructor_Zero() {
        HANDLE handle = new HANDLE(0);
        assertEquals(0, handle.segment().address());
    }

    @Test
    void testLongConstructor_Negative() {
        HANDLE handle = new HANDLE(-999);
        HANDLE handle2 = new HANDLE(-12938);
        assertEquals(-999, handle.segment().address());
        assertEquals(-12938, handle2.segment().address());
        assertNotEquals(handle, handle2);
    }

    @Test
    void testLongConstructor_Positive() {
        HANDLE handle = new HANDLE(9999999);
        HANDLE handle2 = new HANDLE(11280482);
        assertEquals(9999999, handle.segment().address());
        assertEquals(11280482, handle2.segment().address());
        assertNotEquals(handle, handle2);
    }

    @Test
    void testLongConstructor_DecimalPlace() {
        HANDLE handle = new HANDLE((long) 9.99998);
        HANDLE handle2 = new HANDLE((long) 1.30492);
        assertEquals(9.0, handle.segment().address());
        assertEquals(1.0, handle2.segment().address());
        assertNotEquals(handle, handle2);
    }
}
