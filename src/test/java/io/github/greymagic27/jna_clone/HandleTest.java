package io.github.greymagic27.jna_clone;

import io.github.greymagic27.jna_clone.WinDef.HDC;
import io.github.greymagic27.jna_clone.WinDef.HWND;
import java.lang.foreign.MemorySegment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HandleTest {

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
}
