package io.github.greymagic27.win_method.WinDef;

/// An atom. For more information, see <a href="https://learn.microsoft.com/en-us/windows/desktop/dataxchg/about-atom-tables">About Atom Tables</a>
public class ATOM extends WORD {

    /// Creates an {@code ATOM} from a {@code short} value
    ///
    /// @param value The value to store
    public ATOM(short value) {
        super(value);
    }
}
