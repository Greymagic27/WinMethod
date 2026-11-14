package io.github.greymagic27.win_method.BaseTsd;

/// An unsigned long type for pointer precision
public class DWORD_PTR extends ULONG_PTR {
    /// Creates a {@code DWORD_PTR} from a {@code long} value
    ///
    /// @param value The value to store
    public DWORD_PTR(long value) {
        super(value);
    }
}
