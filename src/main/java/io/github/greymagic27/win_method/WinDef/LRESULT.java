package io.github.greymagic27.win_method.WinDef;

import org.jspecify.annotations.NonNull;

/**
 * Signed result of message processing
 */
public class LRESULT {

    private final long value;

    /**
     * @param value A {@link Long} value
     */
    public LRESULT(long value) {
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
