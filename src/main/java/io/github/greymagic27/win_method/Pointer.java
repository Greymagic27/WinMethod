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

    /// Reads an integer value from the memory segment pointed to by a pointer
    ///
    /// @param offset The byte offset from the pointer address
    /// @return The integer value stored at the specified offset
    public int getInt(long offset) {
        return segment.get(ValueLayout.JAVA_INT, offset);
    }

    /// Reads a long value from the memory segment pointed to by a pointer
    ///
    /// @param offset The byte offset from the pointer address
    /// @return The long value stored at the specified offset
    public long getLong(long offset) {
        return segment.get(ValueLayout.JAVA_LONG, offset);
    }

    /// Reads a native pointer value from the memory segment pointed to by a pointer
    ///
    /// @param offset The byte offset from the pointer address
    /// @return A new {@code Pointer} representing the native address
    public Pointer getPointer(long offset) {
        return new Pointer(segment.get(ValueLayout.ADDRESS, offset));
    }

    /// Writes an integer value to the memory pointed to by a pointer
    ///
    /// @param offset The byte offset from the pointer address
    /// @param value  The integer value to write
    public void setInt(long offset, int value) {
        segment.set(ValueLayout.JAVA_INT, offset, value);
    }

    /// Writes a Long value to the memory pointed to by a pointer
    ///
    /// @param offset The byte offset from the pointer address
    /// @param value  The long value to write
    public void setLong(long offset, long value) {
        segment.set(ValueLayout.JAVA_LONG, offset, value);
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
