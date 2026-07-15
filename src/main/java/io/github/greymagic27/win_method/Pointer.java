package io.github.greymagic27.win_method;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.nio.charset.StandardCharsets;
import org.jspecify.annotations.NonNull;

public class Pointer {

    public final MemorySegment segment;

    public Pointer(MemorySegment segment) {
        this.segment = segment;
    }

    public static @NonNull Pointer MAKEINTRESOURCEW(int i) {
        return new Pointer(MemorySegment.ofAddress(i));
    }

    public boolean isNull() {
        return segment.equals(MemorySegment.NULL) || segment.address() == 0;
    }

    public int getInt(long offset) {
        return segment.get(ValueLayout.JAVA_INT, offset);
    }

    public long getLong(long offset) {
        return segment.get(ValueLayout.JAVA_LONG, offset);
    }

    public MemorySegment getPointer() {
        return segment;
    }

    public void setInt(long offset, int value) {
        segment.set(ValueLayout.JAVA_INT, offset, value);
    }

    public void setLong(long offset, long value) {
        segment.set(ValueLayout.JAVA_LONG, offset, value);
    }

    public @NonNull String getWideString(long offset) {
        long length = 0;
        while (segment.get(ValueLayout.JAVA_SHORT, offset + length * 2) != 0) {
            length++;
        }
        byte[] bytes = new byte[Math.toIntExact(length * 2)];
        MemorySegment.copy(segment, ValueLayout.JAVA_BYTE, offset, bytes, 0, bytes.length);
        return new String(bytes, StandardCharsets.UTF_16LE);
    }

    @Override
    public @NonNull String toString() {
        return "Pointer@0x" + Long.toHexString(segment.address());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pointer pointer)) return false;
        return this.segment.address() == pointer.segment.address();
    }

    @Override
    public int hashCode() {
        return Long.hashCode(segment.address());
    }
}
