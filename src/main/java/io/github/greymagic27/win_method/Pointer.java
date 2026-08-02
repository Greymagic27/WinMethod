package io.github.greymagic27.win_method;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.nio.charset.StandardCharsets;
import org.jspecify.annotations.NonNull;

/// A native memory pointer
public class Pointer {

    /// The memory segment represented by a pointer
    public final MemorySegment segment;

    /// Creates a pointer from a memory segment
    ///
    /// @param segment The memory segment to wrap
    public Pointer(MemorySegment segment) {
        this.segment = segment;
    }

    /// Creates a pointer value from an integer resource
    ///
    /// @param i The integer resource
    /// @return A pointer containing the integer resource value
    public static @NonNull Pointer MAKEINTRESOURCEW(int i) {
        return new Pointer(MemorySegment.ofAddress(i));
    }

    /// Checks whether the pointer is null
    ///
    /// @return Returns {@code true} if the pointer address is zero, otherwise returns {@code false}
    public boolean isNull() {
        return segment.equals(MemorySegment.NULL) || segment.address() == 0;
    }

    /// Reads a null-terminated UTF-16LE string from native memory
    ///
    /// @param offset The byte offset from the pointer address where the string starts
    /// @return Returns the decoded Java string
    public @NonNull String getWideString(long offset) {
        long length = 0;
        while (segment.get(ValueLayout.JAVA_SHORT, offset + length * 2) != 0) {
            length++;
        }
        byte[] bytes = new byte[Math.toIntExact(length * 2)];
        MemorySegment.copy(segment, ValueLayout.JAVA_BYTE, offset, bytes, 0, bytes.length);
        return new String(bytes, StandardCharsets.UTF_16LE);
    }

    /// Returns a hex representation of the native pointer address
    @Override
    public @NonNull String toString() {
        return "Pointer@0x" + Long.toHexString(segment.address());
    }

    /// Compares a pointer with another pointer
    ///
    /// @param o the reference object with which to compare
    /// @return Returns {@code true} if both pointers have the same address
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pointer pointer)) return false;
        return this.segment.address() == pointer.segment.address();
    }

    /// Returns a hash code based on the native pointer address
    @Override
    public int hashCode() {
        return Long.hashCode(segment.address());
    }
}
