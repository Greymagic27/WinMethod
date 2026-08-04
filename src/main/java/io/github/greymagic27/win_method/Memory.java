package io.github.greymagic27.win_method;

import java.lang.foreign.Arena;
import org.jspecify.annotations.NonNull;

public class Memory extends Pointer implements AutoCloseable {

    private final Arena arena;
    private final long size;
    private volatile boolean closed = false;

    /// Allocates a new block of native memory
    ///
    /// @param size The number of bytes to allocate
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
}
