package io.github.greymagic27.jna_clone;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StructureTest {

    @Test
    void testWrite() {
        Point p = new Point();
        p.x = 10;
        p.y = 20;
        p.write();
        Pointer ptr = p.pointer();
        assertEquals(10, ptr.segment.get(ValueLayout.JAVA_INT, 0));
        assertEquals(20, ptr.segment.get(ValueLayout.JAVA_INT, 4));
    }

    @Test
    void testRead() {
        Point p = new Point();
        Pointer ptr = p.pointer();
        ptr.segment.set(ValueLayout.JAVA_INT, 0, 500);
        ptr.segment.set(ValueLayout.JAVA_INT, 4, 1000);
        p.read();
        assertEquals(500, p.x);
        assertEquals(1000, p.y);
    }

    @Test
    void testMixedTypes() {
        @Structure.FieldOrder({"id", "active", "value"})
        class DataBundle extends Structure {
            private long id;
            private boolean active;
            private double value;
        }
        DataBundle db = new DataBundle();
        db.id = 0xDEADBEEFL;
        db.active = true;
        db.value = 9999.99;
        Pointer ptr = db.pointer();
        assertEquals(0xDEADBEEFL, ptr.segment.get(ValueLayout.JAVA_LONG, 0));
        assertEquals(1, ptr.segment.get(ValueLayout.JAVA_INT, 8));
        assertEquals(9999.99, ptr.segment.get(ValueLayout.JAVA_DOUBLE, 16));
        db.id = 0;
        db.read();
        assertEquals(0xDEADBEEFL, db.id);
        assertEquals(9999.99, db.value);
        assertTrue(db.active);
    }

    @Test
    void testBooleanType() {
        @SuppressWarnings("unused")
        @Structure.FieldOrder("b")
        class Bool extends Structure {
            private boolean b;
        }
        Bool st = new Bool();
        Pointer ptr = st.pointer();
        st.b = false;
        st.pointer();
        assertEquals(0, ptr.segment.get(ValueLayout.JAVA_INT, 0));
        st.b = true;
        st.pointer();
        assertEquals(1, ptr.segment.get(ValueLayout.JAVA_INT, 0));
        st.b = false;
        st.read();
        assertTrue(st.b);
    }

    @Test
    void testPointerType() {
        @SuppressWarnings("unused")
        @Structure.FieldOrder("addr")
        class WithPointer extends Structure {
            private Pointer addr;
        }
        WithPointer wp = new WithPointer();
        wp.addr = new Pointer(MemorySegment.ofAddress(0x9999));
        wp.pointer();
        assertEquals(0x9999, wp.pointer().segment.get(ValueLayout.ADDRESS, 0).address());
    }

    @Test
    void testStringType() {
        @SuppressWarnings("unused")
        @Structure.FieldOrder("str")
        class WithString extends Structure {
            private String str;
        }
        WithString ws = new WithString();
        ws.str = "test string";
        MemorySegment strAddress = ws.pointer().segment.get(ValueLayout.ADDRESS, 0);
        Pointer strPtr = new Pointer(strAddress.reinterpret(("test string".length() + 1) * 2));
        assertEquals("test string", strPtr.getWideString(0));
    }

    @Test
    void testMissingAnnotation() {
        class Invalid extends Structure {
        }
        assertThrows(IllegalStateException.class, Invalid::new, "Throws IllegalStateException if no @FieldOrder annotation is provided");
    }

    @Test
    void testMismatchFields() {
        @SuppressWarnings("unused")
        @Structure.FieldOrder("x")
        class Mismatch extends Structure {
            private int x;
            private int y;
        }
        IllegalStateException e = assertThrows(IllegalStateException.class, Mismatch::new);
        assertTrue(e.getMessage().contains("Missing from @FieldOrder"));
    }

    @Test
    void testGhostFields() {
        @Structure.FieldOrder({"x", "ghost"})
        class GhostField extends Structure {
        }
        RuntimeException e = assertThrows(RuntimeException.class, GhostField::new);
        assertTrue(e.getMessage().contains("no such field(s) exist"));
    }

    @Test
    void testMultipleGhostFields() {
        @Structure.FieldOrder({"x", "ghost1", "ghost2"})
        class MultipleGhostFields extends Structure {
        }
        RuntimeException e = assertThrows(RuntimeException.class, MultipleGhostFields::new);
        assertTrue(e.getMessage().contains("ghost1"));
        assertTrue(e.getMessage().contains("ghost2"));
    }

    @Test
    void testToString() {
        Point p = new Point();
        p.x = 42;
        p.y = 7;
        String str = p.toString();
        assertEquals(42, p.pointer().segment.get(ValueLayout.JAVA_INT, 0));
        assertTrue(str.contains("Point"));
        assertTrue(str.contains("x=42"));
        assertTrue(str.contains("y=7"));
    }

    @Test
    void testAlignmentPadding() {
        @SuppressWarnings("unused")
        @Structure.FieldOrder({"b", "l"})
        class Aligned extends Structure {
            private byte b;
            private long l;
        }
        Aligned s = new Aligned();
        s.b = (byte) 0xAA;
        s.l = 0xBBBBBBBBBBBBBBBBL;
        s.write();
        MemorySegment seg = s.pointer().segment;
        assertEquals((byte) 0XAA, seg.get(ValueLayout.JAVA_BYTE, 0));
        assertEquals(0xBBBBBBBBBBBBBBBBL, seg.get(ValueLayout.JAVA_LONG, 8));
    }

    @Test
    void testStructureSize() {
        Point p = new Point();
        assertEquals(8, p.size());
    }

    @Test
    void testCaseSensitivityEnforcement() {
        @SuppressWarnings("unused")
        @Structure.FieldOrder({"x", "y"})
        class TestClass extends Structure {
            private int x;
            private int Y;
        }
        @SuppressWarnings("unused")
        @Structure.FieldOrder({"X", "Y"})
        class TestClass2 extends Structure {
            private int x;
            private int y;
        }
        RuntimeException e = assertThrows(RuntimeException.class, TestClass::new);
        RuntimeException e2 = assertThrows(RuntimeException.class, TestClass2::new);
        assertTrue(e.getMessage().contains("no such field(s) exist"));
        assertTrue(e.getMessage().contains("y"));
        assertTrue(e2.getMessage().contains("no such field(s) exist"));
        assertTrue(e2.getMessage().contains("X"));
        assertTrue(e2.getMessage().contains("Y"));
    }


    @Structure.FieldOrder({"x", "y"})
    private static class Point extends Structure {
        private int x;
        private int y;
    }
}