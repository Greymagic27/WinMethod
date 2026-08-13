package io.github.greymagic27.win_method.WinDef;

import io.github.greymagic27.win_method.WinNT.HANDLE;
import java.lang.foreign.MemorySegment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class HgdiObjTest {

    @Test
    void testHGDIOBJ() {
        MemorySegment segment = MemorySegment.ofAddress(0x1234);
        HGDIOBJ hgdobj = new HGDIOBJ(segment);
        assertNotNull(hgdobj);
        assertEquals(0x1234, hgdobj.segment.address());
    }

    @Test
    void testHGDIOBJFromHandle() {
        MemorySegment segment = MemorySegment.ofAddress(0x1234);
        HANDLE handle = new HANDLE(segment);
        HGDIOBJ hgdobj = new HGDIOBJ(handle);
        assertNotNull(hgdobj);
        assertEquals(handle.segment.address(), hgdobj.segment.address());
    }
}