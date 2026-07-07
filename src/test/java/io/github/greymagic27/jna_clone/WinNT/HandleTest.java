package io.github.greymagic27.jna_clone.WinNT;

import io.github.greymagic27.jna_clone.WinDef.HDC;
import io.github.greymagic27.jna_clone.WinDef.HINSTANCE;
import io.github.greymagic27.jna_clone.WinDef.HMENU;
import io.github.greymagic27.jna_clone.WinDef.HWND;
import java.lang.foreign.MemorySegment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HandleTest {

    @Test
    void testHandleToStringIncludesSubclassName() {
        HWND hwnd = new HWND(MemorySegment.ofAddress(0xABCD));
        HDC hdc = new HDC(MemorySegment.ofAddress(0xAF));
        HANDLE handle = new HANDLE(MemorySegment.ofAddress(0xAE));
        HMENU hmenu = new HMENU(MemorySegment.ofAddress(0xAD));
        HINSTANCE hinstance = new HINSTANCE(MemorySegment.ofAddress(0xBEA));
        assertEquals("HWND@0xabcd", hwnd.toString());
        assertEquals("HDC@0xaf", hdc.toString());
        assertEquals("HANDLE@0xae", handle.toString());
        assertEquals("HMENU@0xad", hmenu.toString());
        assertEquals("HINSTANCE@0xbea", hinstance.toString());
    }

    @Test
    void testSegmentAccess() {
        MemorySegment segment = MemorySegment.ofAddress(0x42);
        HANDLE handle = new HANDLE(segment);
        assertEquals(segment, handle.segment);
    }

    @Test
    void testLongConstructor_MatchesMemorySegmentConstructor() {
        HANDLE fromLong = new HANDLE(0x1234);
        HANDLE fromSegment = new HANDLE(MemorySegment.ofAddress(0x1234));
        assertEquals(fromSegment.segment.address(), fromLong.segment.address());
    }
}
