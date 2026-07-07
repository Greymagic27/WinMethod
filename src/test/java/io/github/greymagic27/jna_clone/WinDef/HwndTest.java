package io.github.greymagic27.jna_clone.WinDef;

import io.github.greymagic27.jna_clone.WinNT.HANDLE;
import java.lang.foreign.MemorySegment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class HwndTest {

    @Test
    void testConstructorStoresValue() {
        MemorySegment segment = MemorySegment.ofAddress(0x1234);
        HWND hwnd = new HWND(segment);
        assertEquals(0x1234, hwnd.segment.address());
    }

    @Test
    void testIsHandleSubtype() {
        assertInstanceOf(HANDLE.class, new HWND(MemorySegment.NULL));
    }
}