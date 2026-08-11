package io.github.greymagic27.win_method.types;

import io.github.greymagic27.win_method.WinNT.LONG;

/// Values defined in WinNT.h
public interface WinNT {

    /// The {@link io.github.greymagic27.win_method.WinNT.HANDLE} that identifies a directory
    int FILE_ATTRIBUTE_DIRECTORY = 0x00000010;
    /// A file that does not have other attributes set. This attribute is valid only when used alone
    int FILE_ATTRIBUTE_NORMAL = 0x00000080;
    /// Read access
    LONG GENERIC_READ = new LONG(0x80000000);
    /// Write access
    LONG GENERIC_WRITE = new LONG(0x40000000);
    /// Enables subsequent open operations on a file or device to request read access
    int FILE_SHARE_READ = 0x00000001;
    int FILE_SHARE_WRITE = 0x00000002;
}
