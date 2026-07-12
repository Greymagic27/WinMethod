package io.github.greymagic27.jna_clone.WinDef;

import org.jspecify.annotations.NonNull;

/**
 * A 16-bit unsigned integer. The range is 0 through 65535 decimal
 */
public class WORD {

    private final short value;

    /**
     * Instantiates a new WORD
     *
     * @param value A {@link Short} value
     */
    public WORD(short value) {
        this.value = value;
    }

    /**
     * Returns the short value
     */
    public short shortValue() {
        return value;
    }

    /**
     * @return Returns the value of {@link #shortValue()} as a string
     */
    @Override
    public @NonNull String toString() {
        return String.valueOf(shortValue());
    }
}
