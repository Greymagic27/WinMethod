package io.github.greymagic27.win_method;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UnionTest {

    private IntLongUnion u;

    @BeforeEach
    void setUp() {
        u = new IntLongUnion();
    }

    @Test
    void testSizeIsLargestMember() {
        assertEquals(8, new IntLongUnion().size());
    }

    @Test
    void testAllFieldsShareOffsetZero() {
        u.setType("i");
        u.i = 42;
        u.write();
        assertEquals(42, u.pointer().segment.get(ValueLayout.JAVA_INT, 0));
        u.i = 0;
        assertEquals(0, u.pointer().segment.get(ValueLayout.JAVA_INT, 0));
        u.setType("l");
        MemorySegment seg = u.pointer().segment;
        assertEquals(0L, seg.get(ValueLayout.JAVA_LONG, 0));
    }

    @Test
    void testWriteWithoutSettingTypeThrows() {
        assertThrows(IllegalArgumentException.class, u::write);
    }

    @Test
    void testSetTypeByClass() {
        u.setType(long.class);
        u.l = 5;
        u.write();
        assertEquals(5L, u.pointer().segment.get(ValueLayout.JAVA_LONG, 0));
    }

    @Test
    void testSetTypeByClass_NoMatchThrows() {
        assertThrows(IllegalArgumentException.class, () -> u.setType(String.class));
    }

    @Test
    void testSetTypeByName_NoMatchThrows() {
        assertThrows(IllegalArgumentException.class, () -> u.setType("nonexistent"));
    }

    @Test
    void testConstructionDoesNotCrashWhenSizeExceedsAlignment() {
        LargeSizeSmallAlignUnion u = assertDoesNotThrow(LargeSizeSmallAlignUnion::new);
        assertEquals(8, u.size());
    }

    @Test
    void testOverlappedStyleUnion() {
        DummyUnion u = new DummyUnion();
        assertEquals(8, u.size());
        u.setType("pointerField");
        u.pointerField = new Pointer(MemorySegment.ofAddress(0x1234));
        MemorySegment seg = u.pointer().segment;
        assertEquals(0x1234L, seg.get(ValueLayout.ADDRESS, 0).address());
    }

    @Test
    void testToStringWithActiveFieldWorks() {
        u.setType("i");
        u.i = 42;
        String str = assertDoesNotThrow(u::toString);
        assertTrue(str.contains("i=42"));
    }

    @Test
    void testHasActiveField() {
        assertFalse(u.hasActiveField());
        u.setType("i");
        assertTrue(u.hasActiveField());
    }

    @SuppressWarnings("unused")
    @Structure.AutoFieldOrder
    static class IntLongUnion extends Union {
        public int i;
        public long l;
    }

    @SuppressWarnings("unused")
    @Structure.AutoFieldOrder
    static class LargeSizeSmallAlignUnion extends Union {
        @ArrayLength(2)
        public int[] arr;
        public short s;
    }

    @SuppressWarnings("unused")
    @Structure.AutoFieldOrder
    static class OffsetStruct extends Structure {
        public int offset;
        public int offsetHigh;
    }

    @SuppressWarnings("unused")
    @Structure.AutoFieldOrder
    static class DummyUnion extends Union {
        public OffsetStruct dummyStructName;
        public Pointer pointerField;
    }
}