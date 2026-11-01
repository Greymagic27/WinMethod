package io.github.greymagic27.win_method.WinNT;

/// The return codes used by COM interfaces. For more information, see [Structure of the COM Error Codes](https://learn.microsoft.com/en-us/windows/desktop/com/structure-of-com-error-codes)
public class HRESULT extends LONG {

    /// Creates a {@code LONG} from an {@code int} value
    ///
    /// @param value The value to store
    public HRESULT(int value) {
        super(value);
    }
}
