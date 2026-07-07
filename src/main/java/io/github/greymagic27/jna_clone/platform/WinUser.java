package io.github.greymagic27.jna_clone.platform;

@SuppressWarnings("unused")
public interface WinUser {

    int WS_OVERLAPPED = 0x00000000;
    int WS_POPUP = 0x80000000;
    int WS_CHILD = 0x40000000;
    int WS_MINIMIZE = 0x20000000;
    int WS_VISIBLE = 0x10000000;
    int WS_DISABLED = 0x08000000;
    int WS_CLIPSIBLINGS = 0x04000000;
    int WS_CLIPCHILDREN = 0x02000000;
    int WS_MAXIMIZE = 0x01000000;
    int WS_CAPTION = 0x00C00000;
    int WS_BORDER = 0x00800000;
    int WS_DLGFRAME = 0x00400000;
    int WS_VSCROLL = 0x00200000;
    int WS_HSCROLL = 0x00100000;
    int WS_SYSMENU = 0x00080000;
    int WS_THICKFRAME = 0x00040000;
    int WS_GROUP = 0x00020000;
    int WS_TABSTOP = 0x00010000;
}
