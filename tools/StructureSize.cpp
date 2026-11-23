#define NOMINMAX
#include <windows.h>
#include <algorithm>
#include <cstddef>
#include <iostream>
#include <vector>

struct Field { const char* name; size_t offset, size, align; };

static size_t roundUp(size_t v, size_t a) { return (v + a - 1) / a * a; }

struct Sim { bool layoutOk; size_t size, align; };

static Sim simulate(const std::vector<Field>& f, size_t actualSize, size_t pack) {
    size_t off = 0, maxAlign = 1;
    bool ok = true;
    for (const Field& x : f) {
        size_t a = pack ? std::min(x.align, pack) : x.align;
        off = roundUp(off, a);
        if (off != x.offset) ok = false;
        off += x.size;
        maxAlign = std::max(maxAlign, a);
    }
    size_t total = roundUp(off, maxAlign);
    return { ok && total == actualSize, total, maxAlign };
}

static void check(const char* name, size_t size, size_t align, const std::vector<Field>& f) {
    Sim natural = simulate(f, size, 0);
    if (natural.layoutOk && natural.align == align) {
        std::cout << "  ok        " << name << " (" << size << ")\n";
        return;
    }
    for (size_t pack : {4, 2, 1}) {
        Sim s = simulate(f, size, pack);
        if (s.layoutOk && s.align == align) {
            if (natural.layoutOk) {
               std::cout << "  NEEDS     " << name << " -> pack(" << pack << ")"
                                         << "  (actual " << size << ", natural would be " << natural.size << ")\n";
            } else {
                std::cout << "  NEEDS     " << name << " -> pack(" << pack << ")"
                          << "  (actual " << size << ", natural would be " << natural.size << ")\n";
            }
            return;
        }
    }
    std::cout << "  ???       " << name << "  matches no pack value; check the field table,"
              << " or it may be over-aligned (DECLSPEC_ALIGN)\n";
}

#define BEGIN(T) { using S = T; const char* name = #T; size_t sz = sizeof(T), al = alignof(T); std::vector<Field> f = {
#define END      }; check(name, sz, al, f); }
#define F(m)     { #m, offsetof(S, m), sizeof(S::m), alignof(decltype(S::m)) }

int main() {
    BEGIN(POINT) F(x), F(y) END
    BEGIN(RECT) F(left), F(top), F(right), F(bottom) END
    BEGIN(MSG) F(hwnd), F(message), F(wParam), F(lParam), F(time), F(pt) END
    BEGIN(PAINTSTRUCT) F(hdc), F(fErase), F(rcPaint), F(fRestore), F(fIncUpdate), F(rgbReserved) END
    BEGIN(WNDCLASSEXW) F(cbSize), F(style), F(lpfnWndProc), F(cbClsExtra), F(cbWndExtra),
        F(hInstance), F(hIcon), F(hCursor), F(hbrBackground), F(lpszMenuName),
        F(lpszClassName), F(hIconSm) END
    BEGIN(BITMAPINFO) F(bmiHeader), F(bmiColors) END
    BEGIN(BITMAPINFOHEADER) F(biSize), F(biWidth), F(biHeight), F(biPlanes), F(biBitCount),
        F(biCompression), F(biSizeImage), F(biXPelsPerMeter), F(biYPelsPerMeter),
        F(biClrUsed), F(biClrImportant) END
    BEGIN(RGBQUAD) F(rgbBlue), F(rgbGreen), F(rgbRed), F(rgbReserved) END
    BEGIN(WIN32_FIND_DATAW) F(dwFileAttributes), F(ftCreationTime), F(ftLastAccessTime),
        F(ftLastWriteTime), F(nFileSizeHigh), F(nFileSizeLow), F(dwReserved0), F(dwReserved1),
        F(cFileName), F(cAlternateFileName) END
    BEGIN(FILETIME) F(dwLowDateTime), F(dwHighDateTime) END
    BEGIN(SECURITY_ATTRIBUTES) F(nLength), F(lpSecurityDescriptor), F(bInheritHandle) END
    BEGIN(OVERLAPPED) F(Internal), F(InternalHigh), F(Pointer), F(hEvent) END
    BEGIN(STARTUPINFOW) F(cb), F(lpReserved), F(lpDesktop), F(lpTitle), F(dwX), F(dwY),
        F(dwXSize), F(dwYSize), F(dwXCountChars), F(dwYCountChars), F(dwFillAttribute),
        F(dwFlags), F(wShowWindow), F(cbReserved2), F(lpReserved2), F(hStdInput),
        F(hStdOutput), F(hStdError) END
    BEGIN(PROCESS_INFORMATION) F(hProcess), F(hThread), F(dwProcessId), F(dwThreadId) END
    BEGIN(WAVEFORMATEX) F(wFormatTag), F(nChannels), F(nSamplesPerSec), F(nAvgBytesPerSec),
        F(nBlockAlign), F(wBitsPerSample), F(cbSize) END
    BEGIN(WAVEHDR) F(lpData), F(dwBufferLength), F(dwBytesRecorded), F(dwUser), F(dwFlags),
        F(dwLoops), F(lpNext), F(reserved) END
    BEGIN(MMTIME) F(wType), F(u) END
}