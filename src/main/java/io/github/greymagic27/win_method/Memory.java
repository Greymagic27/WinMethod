package io.github.greymagic27.win_method;

import java.io.Closeable;
import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import org.jspecify.annotations.NonNull;

public class Memory extends Pointer implements Closeable {

    private final Arena arena;
    private final long size;
    private volatile boolean closed = false;

    /// Allocates a new block of native memory
    ///
    /// @param size The number of bytes to allocate
    @SuppressWarnings("MustBeClosedChecker")
    public Memory(long size) {
        this(size, Arena.ofShared());
    }

    /// Allocates a new block of native memory using the given arena
    ///
    /// @param size  The number of bytes to allocate
    /// @param arena The arena that owns the allocation
    public Memory(long size, @NonNull Arena arena) {
        super(arena.allocate(size));
        this.arena = arena;
        this.size = size;
    }

    /// Returns the size, in bytes, of this memory block
    public long size() {
        return size;
    }

    /// Fills the entire memory block with zero bytes
    public void clear() {
        segment.fill((byte) 0);
    }

    /// Releases the underlying native memory
    @Override
    public void close() {
        if (closed) return;
        closed = true;
        clear();
        arena.close();
    }


    /// Returns the contents of this memory block as a byte array
    ///
    /// @return A byte array containing the contents of this memory block
    public byte[] getByteArray() {
        if (size > Integer.MAX_VALUE) throw new IllegalStateException("Memory size exceeds maximum byte array size");
        return getByteArray(0, Math.toIntExact(size));
    }

    /// Returns a range of this memory block as a byte array
    ///
    /// @param offset The offset in bytes from which to start reading
    /// @param length The number of bytes to read
    /// @return A byte array containing the requested memory range
    @Override
    public byte[] getByteArray(long offset, int length) {
        if (offset < 0) throw new IndexOutOfBoundsException("Offset must be more than or equal to 0");
        if (length < 0) throw new IndexOutOfBoundsException("Length must be more than or equal to 0");
        if (offset > size || length > size - offset) throw new IndexOutOfBoundsException("Offset & Length exceeds memory size: offset=" + offset + ", length=" + length + ", size=" + size);
        byte[] bytes = new byte[length];
        MemorySegment.copy(segment, offset, MemorySegment.ofArray(bytes), 0, length);
        return bytes;
    }

    /// Writes a range of a byte array to this memory block
    ///
    /// @param offset The destination offset in bytes within this memory block
    /// @param bytes  The source byte array
    /// @param index  The starting index in the source byte array
    /// @param length The number of bytes to write
    public void write(long offset, byte[] bytes, int index, int length) {
        if (offset < 0) throw new IndexOutOfBoundsException("Offset must be more than or equal to 0");
        if (index < 0) throw new IndexOutOfBoundsException("Index must be more than or equal to 0");
        if (length < 0) throw new IndexOutOfBoundsException("Length must be more than or equal to 0");
        if (index > bytes.length || length > bytes.length - index) throw new IndexOutOfBoundsException("Index & Length exceeds array size: index=" + index + ", length=" + length + ", size=" + bytes.length);
        if (offset > size || length > size - offset) throw new IndexOutOfBoundsException("Offset & Length exceeds memory size: offset=" + offset + ", length=" + length + ", size=" + size);
        MemorySegment.copy(MemorySegment.ofArray(bytes), index, segment, offset, length);
    }
}
