package io.github.greymagic27.jna_clone;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.nio.charset.StandardCharsets;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public record Pointer(MemorySegment segment) {

    public static final Pointer NULL = new Pointer(MemorySegment.NULL);

    @Contract(pure = true)
    public Pointer {
    }

    public boolean isNull() {
        return segment.equals(MemorySegment.NULL) || segment.address() == 0;
    }

    public int getInt(long offset) {
        return segment.get(ValueLayout.JAVA_INT, offset);
    }

    public void setInt(long offset, int value) {
        segment.set(ValueLayout.JAVA_INT, offset, value);
    }

    public long getLong(long offset) {
        return segment.get(ValueLayout.JAVA_LONG, offset);
    }

    public void setLong(long offset, long value) {
        segment.set(ValueLayout.JAVA_LONG, offset, value);
    }

    @Contract("_ -> new")
    public @NotNull String getWideString(long offset) {
        long length = 0;
        while (segment.get(ValueLayout.JAVA_SHORT, offset + length * 2) != 0) {
            length++;
        }
        byte[] bytes = new byte[Math.toIntExact(length * 2)];
        MemorySegment.copy(segment, ValueLayout.JAVA_BYTE, offset, bytes, 0, bytes.length);
        return new String(bytes, StandardCharsets.UTF_16LE);
    }

    @Override
    public @NotNull String toString() {
        return "Pointer@0x" + Long.toHexString(segment.address());
    }
}
