package io.github.greymagic27.jna_clone.WinDef;

import io.github.greymagic27.jna_clone.WinNT.HANDLE;
import java.lang.foreign.MemorySegment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class HModuleTest {

    @Test
    void testConstructorStoresValue() {
        MemorySegment segment = MemorySegment.ofAddress(0xBEEF);
        HMODULE hmodule = new HMODULE(segment);
        assertEquals(0xBEEF, hmodule.segment.address());
    }

    @Test
    void testIsHandleSubtype() {
        assertInstanceOf(HANDLE.class, new HMODULE(MemorySegment.NULL));
    }
}
