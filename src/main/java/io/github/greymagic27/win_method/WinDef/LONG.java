package io.github.greymagic27.win_method.WinDef;

import org.jspecify.annotations.NonNull;

/**
 * A 32-bit signed integer. A 32-bit signed integer. The range is -2147483648 through 2147483647 decimal.
 */
public class LONG {

    private final int value;

    /**
     * @param value A {@link Integer} value
     */
    public LONG(int value) {
        this.value = value;
    }

    /**
     * Returns the int value
     */
    public int intValue() {
        return value;
    }

    /**
     * @param obj the reference object with which to compare
     * @return Returns if the objects are equal
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof LONG other)) return false;
        return value == other.value;
    }

    /**
     * @return Returns the {@link Integer} hash code
     */
    @Override
    public int hashCode() {
        return Integer.hashCode(value);
    }

    /**
     * @return Returns the value of {@link #intValue()} as a string
     */
    @Override
    public @NonNull String toString() {
        return String.valueOf(intValue());
    }
}
