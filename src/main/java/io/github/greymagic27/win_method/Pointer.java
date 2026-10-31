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

    /// Reads a range of bytes from native memory
    ///
    /// @param offset The byte offset from the pointer address where reading starts
    /// @param length The number of bytes to read
    /// @return A byte array containing the requested bytes
    public byte[] getByteArray(long offset, int length) {
        byte[] bytes = new byte[length];
        MemorySegment.copy(segment, offset, MemorySegment.ofArray(bytes), 0, length);
        return bytes;
    }

    /// Reads a 64-bit integer from native memory
    ///
    /// @param offset The byte offset from the pointer address
    /// @return The 64-bit integer value
    public long getLong(long offset) {
        return segment.get(ValueLayout.JAVA_LONG_UNALIGNED, offset);
    }

    /// Reads a 32-bit integer from native memory
    ///
    /// @param offset The byte offset from the pointer address
    /// @return The 32-bit integer value
    public int getInt(long offset) {
        return segment.get(ValueLayout.JAVA_INT_UNALIGNED, offset);
    }

    /// Reads a 16-bit integer from native memory
    ///
    /// @param offset The byte offset from the pointer address
    /// @return The 16-bit integer value
    public short getShort(long offset) {
        return segment.get(ValueLayout.JAVA_SHORT_UNALIGNED, offset);
    }

    ///    Reads a pointer value stored at the specified offset
    ///
    /// @param offset The byte offset where the pointer is stored
    /// @return A pointer wrapping the retrieved memory address
    public Pointer getPointer(long offset) {
        return new Pointer(segment.get(ValueLayout.ADDRESS, offset));
    }

    /// Reads a pointer value stored at the specified offset and reinterprets it for the specified size
    ///
    /// @param offset The byte offset where the pointer is stored
    /// @param size   The number of bytes the returned pointer can access
    /// @return A pointer wrapping the retrieved memory address with the specified size
    public Pointer getPointer(long offset, long size) {
        return new Pointer(segment.get(ValueLayout.ADDRESS, offset).reinterpret(size));
    }

    ///  Writes a 64-bit integer to native memory
    ///
    /// @param offset The byte offset where the value is written
    /// @param value  The 64-bit integer value to write
    public void setLong(long offset, long value) {
        segment.set(ValueLayout.JAVA_LONG_UNALIGNED, offset, value);
    }

    ///  Writes a 32-bit integer to native memory
    ///
    /// @param offset The byte offset where the value is written
    /// @param value  The 32-bit integer value to write
    public void setInt(long offset, int value) {
        segment.set(ValueLayout.JAVA_INT_UNALIGNED, offset, value);
    }

    /// Writes a 16-bit integer to native memory
    ///
    /// @param offset The byte offset where the value is written
    /// @param value  The 16-bit integer value to write
    public void setShort(long offset, short value) {
        segment.set(ValueLayout.JAVA_SHORT_UNALIGNED, offset, value);
    }

    /// Writes a pointer value to native memory
    ///
    /// @param offset The byte offset where the pointer is stored
    /// @param value  The pointer value to write
    public void setPointer(long offset, @NonNull Pointer value) {
        segment.set(ValueLayout.ADDRESS, offset, value.segment);
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
