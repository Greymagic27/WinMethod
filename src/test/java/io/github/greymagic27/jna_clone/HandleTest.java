package io.github.greymagic27.jna_clone;

import io.github.greymagic27.jna_clone.WinDef.HDC;
import io.github.greymagic27.jna_clone.WinDef.HWND;
import java.lang.foreign.MemorySegment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HandleTest {

    @Test
    void testHandleIsNull() {
        assertTrue(Handle.NULL.isNull());
        assertTrue(HWND.NULL.isNull());
        assertTrue(HDC.NULL.isNull());
        assertFalse(new HWND(MemorySegment.ofAddress(0x1)).isNull());
    }

    @Test
    void testHandleToStringIncludesSubclassName() {
        HWND hwnd = new HWND(MemorySegment.ofAddress(0xABCD));
        HDC hdc = new HDC(MemorySegment.ofAddress(0xAF));
        Handle handle = new Handle(MemorySegment.ofAddress(0xAE));
        assertEquals("HWND@0xabcd", hwnd.toString());
        assertEquals("HDC@0xaf", hdc.toString());
        assertEquals("Handle@0xae", handle.toString());
    }

    @Test
    void testSegmentAccess() {
        MemorySegment segment = MemorySegment.ofAddress(0x42);
        Handle handle = new Handle(segment);
        assertEquals(segment, handle.segment());
    }

    @Test
    void testNullHandleToString() {
        assertEquals("Handle@0x0", Handle.NULL.toString());
        assertEquals("HWND@0x0", HWND.NULL.toString());
        assertEquals("HDC@0x0", HDC.NULL.toString());
    }
}
