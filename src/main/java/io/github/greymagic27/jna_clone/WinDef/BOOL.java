package io.github.greymagic27.jna_clone.WinDef;

import org.jspecify.annotations.NonNull;

/**
 * A Boolean variable (should be <b>TRUE</b> or <b>FALSE</b>)
 */
public class BOOL {

    private final int value;

    /**
     * @param value An {@link Integer} value
     */
    public BOOL(int value) {
        this.value = value;
    }

    /**
     * Returns the boolean value
     */
    public boolean booleanValue() {
        return value != 0;
    }

    /**
     * Returns the int value
     */
    public int intValue() {
        return value;
    }

    /**
     * @return Returns the value of {{@link #booleanValue()}} as a string
     */
    @Override
    public @NonNull String toString() {
        return String.valueOf(booleanValue());
    }
}