package io.github.greymagic27.win_method.types;

/// Values from fileapi.h
public interface FileApi {

    /// Creates a new file, always
    int CREATE_ALWAYS = 2;
    /// Opens a file or device, only if it exists
    int OPEN_EXISTING = 3;

}
