package io.github.greymagic27.jna_clone.WinDef;

import io.github.greymagic27.jna_clone.WinNT.HANDLE;
import java.lang.foreign.MemorySegment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class HInstanceTest {

    @Test
    void testConstructorStoresValue() {
        MemorySegment segment = MemorySegment.ofAddress(0xBEEF);
        HINSTANCE hinstance = new HMODULE(segment);
        assertEquals(0xBEEF, hinstance.segment.address());
    }

    @Test
    void testIsHandleSubtype() {
        assertInstanceOf(HANDLE.class, new HINSTANCE(MemorySegment.NULL));
    }
}
