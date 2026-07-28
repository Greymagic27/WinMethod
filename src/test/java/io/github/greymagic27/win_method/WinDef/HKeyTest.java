package io.github.greymagic27.win_method.WinDef;

import io.github.greymagic27.win_method.WinNT.HANDLE;
import java.lang.foreign.MemorySegment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class HKeyTest {

    @Test
    void testConstructorStoresValue() {
        MemorySegment segment = MemorySegment.ofAddress(0xBEEF);
        HKEY hkey = new HKEY(segment);
        assertEquals(0xBEEF, hkey.segment.address());
    }

    @Test
    void testIsHandleSubtype() {
        assertInstanceOf(HANDLE.class, new HKEY(MemorySegment.NULL));
    }
}
