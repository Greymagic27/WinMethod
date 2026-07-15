package io.github.greymagic27.win_method.WinDef;

/**
 * An atom. For more information, see <a href="https://learn.microsoft.com/en-us/windows/desktop/dataxchg/about-atom-tables">About Atom Tables</a>
 */
public class ATOM extends WORD {
    /**
     * @param value A {@link Short} value
     */
    public ATOM(short value) {
        super(value);
    }
}
