package io.github.greymagic27.win_method;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StructureArrayTest {

    @Test
    void testByteArrayWriteRead() {
        ByteArray st = new ByteArray();
        st.data = new byte[]{1, 2, 3, 4, 5, 6, 7, 8};
        MemorySegment segment = st.pointer().segment;
        for (int i = 0; i < 8; i++) assertEquals((byte) (i + 1), segment.get(ValueLayout.JAVA_BYTE, i));
        st.data = new byte[8];
        st.read();
        assertEquals(1, st.data[0]);
        assertEquals(2, st.data[1]);
        assertEquals(3, st.data[2]);
        assertEquals(4, st.data[3]);
        assertEquals(5, st.data[4]);
        assertEquals(6, st.data[5]);
        assertEquals(7, st.data[6]);
        assertEquals(8, st.data[7]);
    }

    @Test
    void testShortArrayWriteRead() {
        ShortArray st = new ShortArray();
        st.data = new short[]{100, 200, 300, 400};
        MemorySegment segment = st.pointer().segment;
        assertEquals((short) 100, segment.get(ValueLayout.JAVA_SHORT, 0));
        assertEquals((short) 200, segment.get(ValueLayout.JAVA_SHORT, 2));
        assertEquals((short) 300, segment.get(ValueLayout.JAVA_SHORT, 4));
        assertEquals((short) 400, segment.get(ValueLayout.JAVA_SHORT, 6));
        st.data = new short[4];
        st.read();
        assertEquals(100, st.data[0]);
        assertEquals(200, st.data[1]);
        assertEquals(300, st.data[2]);
        assertEquals(400, st.data[3]);
    }

    @Test
    void testIntArrayWriteRead() {
        IntArray st = new IntArray();
        st.data = new int[]{1000, 2000, 3000};
        MemorySegment segment = st.pointer().segment;
        assertEquals(1000, segment.get(ValueLayout.JAVA_INT, 0));
        assertEquals(2000, segment.get(ValueLayout.JAVA_INT, 4));
        assertEquals(3000, segment.get(ValueLayout.JAVA_INT, 8));
        st.data = new int[3];
        st.read();
        assertEquals(1000, st.data[0]);
        assertEquals(2000, st.data[1]);
        assertEquals(3000, st.data[2]);
    }

    @Test
    void testLongArrayWriteRead() {
        LongArray st = new LongArray();
        st.data = new long[]{100000L, 200000L};
        MemorySegment segment = st.pointer().segment;
        assertEquals(100000L, segment.get(ValueLayout.JAVA_LONG, 0));
        assertEquals(200000L, segment.get(ValueLayout.JAVA_LONG, 8));
        st.data = new long[2];
        st.read();
        assertEquals(100000L, st.data[0]);
        assertEquals(200000L, st.data[1]);
    }

    @Test
    void testFloatArrayWriteRead() {
        FloatArray st = new FloatArray();
        st.data = new float[]{1.5f, 2.5f, 3.5f};
        MemorySegment segment = st.pointer().segment;
        assertEquals(1.5f, segment.get(ValueLayout.JAVA_FLOAT, 0));
        assertEquals(2.5f, segment.get(ValueLayout.JAVA_FLOAT, 4));
        assertEquals(3.5f, segment.get(ValueLayout.JAVA_FLOAT, 8));
        st.data = new float[3];
        st.read();
        assertEquals(1.5f, st.data[0]);
        assertEquals(2.5f, st.data[1]);
        assertEquals(3.5f, st.data[2]);
    }

    @Test
    void testDoubleArrayWriteRead() {
        DoubleArray st = new DoubleArray();
        st.data = new double[]{1.25, 2.5};
        MemorySegment segment = st.pointer().segment;
        assertEquals(1.25, segment.get(ValueLayout.JAVA_DOUBLE, 0));
        assertEquals(2.5, segment.get(ValueLayout.JAVA_DOUBLE, 8));
        st.data = new double[2];
        st.read();
        assertEquals(1.25, st.data[0]);
        assertEquals(2.5, st.data[1]);
    }

    @Test
    void testBooleanArrayWriteRead() {
        BooleanArray st = new BooleanArray();
        st.data = new boolean[]{true, false, true};
        MemorySegment segment = st.pointer().segment;
        assertEquals(1, segment.get(ValueLayout.JAVA_INT, 0));
        assertEquals(0, segment.get(ValueLayout.JAVA_INT, 4));
        assertEquals(1, segment.get(ValueLayout.JAVA_INT, 8));
        st.data = new boolean[3];
        st.read();
        assertTrue(st.data[0]);
        assertFalse(st.data[1]);
        assertTrue(st.data[2]);
    }

    @Test
    void testByteArraySize() {
        ByteArray st = new ByteArray();
        assertEquals(8, st.size());
    }

    @Test
    void testShortArraySize() {
        ShortArray st = new ShortArray();
        assertEquals(8, st.size());
    }

    @Test
    void testIntArraySize() {
        IntArray st = new IntArray();
        assertEquals(12, st.size());
    }

    @Test
    void testLongArraySize() {
        LongArray st = new LongArray();
        assertEquals(16, st.size());
    }

    @Test
    void testFloatArraySize() {
        FloatArray st = new FloatArray();
        assertEquals(12, st.size());
    }

    @Test
    void testDoubleArraySize() {
        DoubleArray st = new DoubleArray();
        assertEquals(16, st.size());
    }

    @Test
    void testBooleanArraySize() {
        BooleanArray st = new BooleanArray();
        assertEquals(12, st.size());
    }

    @Test
    void testArrayFieldRequiresArrayLength() {
        @SuppressWarnings("unused")
        @Structure.AutoFieldOrder
        class InvalidArray extends Structure {
            private byte[] data;
        }
        IllegalStateException e = assertThrows(IllegalStateException.class, InvalidArray::new);
        assertTrue(e.getMessage().contains("must have an @ArrayLength"));
    }

    @Test
    void testArrayFieldWrongLength() {
        ByteArray st = new ByteArray();
        st.data = new byte[]{1, 2, 3};
        IllegalStateException e = assertThrows(IllegalStateException.class, st::pointer);
        assertTrue(e.getMessage().contains("must have length 8"));
    }

    @Test
    void testPrimitiveBeforeArray() {
        @Structure.AutoFieldOrder
        class PrimitiveArray extends Structure {
            private int value;
            @ArrayLength(4)
            private int[] data;
        }
        PrimitiveArray st = new PrimitiveArray();
        st.value = 100;
        st.data = new int[]{10, 20, 30, 40};
        MemorySegment segment = st.pointer().segment;
        assertEquals(100, segment.get(ValueLayout.JAVA_INT, 0));
        assertEquals(10, segment.get(ValueLayout.JAVA_INT, 4));
        assertEquals(20, segment.get(ValueLayout.JAVA_INT, 8));
        assertEquals(30, segment.get(ValueLayout.JAVA_INT, 12));
        assertEquals(40, segment.get(ValueLayout.JAVA_INT, 16));
        st.value = 0;
        st.data = new int[4];
        st.read();
        assertEquals(100, st.value);
        assertEquals(10, st.data[0]);
        assertEquals(20, st.data[1]);
        assertEquals(30, st.data[2]);
        assertEquals(40, st.data[3]);
    }

    @Test
    void testPrimitiveAfterArray() {
        @Structure.AutoFieldOrder
        class PrimitiveArray extends Structure {
            @ArrayLength(4)
            private int[] data;
            private int value;
        }
        PrimitiveArray st = new PrimitiveArray();
        st.value = 100;
        st.data = new int[]{10, 20, 30, 40};
        MemorySegment segment = st.pointer().segment;
        assertEquals(10, segment.get(ValueLayout.JAVA_INT, 0));
        assertEquals(20, segment.get(ValueLayout.JAVA_INT, 4));
        assertEquals(30, segment.get(ValueLayout.JAVA_INT, 8));
        assertEquals(40, segment.get(ValueLayout.JAVA_INT, 12));
        assertEquals(100, segment.get(ValueLayout.JAVA_INT, 16));
        st.value = 0;
        st.data = new int[4];
        st.read();
        assertEquals(100, st.value);
        assertEquals(10, st.data[0]);
        assertEquals(20, st.data[1]);
        assertEquals(30, st.data[2]);
        assertEquals(40, st.data[3]);
    }

    @Structure.AutoFieldOrder
    private static class ByteArray extends Structure {
        @ArrayLength(8)
        private byte[] data;
    }

    @Structure.AutoFieldOrder
    private static class ShortArray extends Structure {
        @ArrayLength(4)
        private short[] data;
    }

    @Structure.AutoFieldOrder
    private static class IntArray extends Structure {
        @ArrayLength(3)
        private int[] data;
    }

    @Structure.AutoFieldOrder
    private static class LongArray extends Structure {
        @ArrayLength(2)
        private long[] data;
    }

    @Structure.AutoFieldOrder
    private static class FloatArray extends Structure {
        @ArrayLength(3)
        private float[] data;
    }

    @Structure.AutoFieldOrder
    private static class DoubleArray extends Structure {
        @ArrayLength(2)
        private double[] data;
    }

    @Structure.AutoFieldOrder
    private static class BooleanArray extends Structure {
        @ArrayLength(3)
        private boolean[] data;
    }
}
