[![.github/workflows/gradle.yml](https://github.com/Greymagic27/JNA-Clone/actions/workflows/gradle.yml/badge.svg)](https://github.com/Greymagic27/JNA-Clone/actions/workflows/gradle.yml) [![Javadocs](https://github.com/Greymagic27/JNA-Clone/actions/workflows/javadocs.yml/badge.svg)](https://greymagic27.github.io/JNA-Clone/)

# Information

An implementation of my own JNA like bindings, sticking to the C++ structure as much as possible. Contributions are
welcomed. Name suggestions also welcome!

# Usage

Currently this is not published on any maven or gradle repository.

To use this in your own projects, you will need to clone the repository and run the following command:
`gradle publishToMavenLocal`

This will then allow you to add this as a dependency in your project and use the bindings

## Helpful info for me

https://learn.microsoft.com/en-us/windows/win32/winprog/windows-data-types

Long: 64 Bit

Int: 32 Bit

Short: 16 Bit

UINT - An unsigned INT. The range is 0 through 4294967295 decimal.

DWORD - A 32-bit unsigned integer. The range is 0 through 4294967295 decimal.
