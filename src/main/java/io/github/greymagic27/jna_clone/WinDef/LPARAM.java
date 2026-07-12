package io.github.greymagic27.jna_clone.WinDef;

import org.jspecify.annotations.NonNull;

/**
 * A message parameter
 */
public class LPARAM {

    private final long value;

    /**
     * @param value A {@link Long} value
     */
    public LPARAM(long value) {
        this.value = value;
    }

    /**
     * Returns the long value
     */
    public long longValue() {
        return value;
    }

    /**
     * @return Returns the value of {{@link #longValue()}} as a string
     */
    @Override
    public @NonNull String toString() {
        return String.valueOf(longValue());
    }
}
