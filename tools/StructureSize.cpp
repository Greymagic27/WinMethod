#include <windows.h>
#include <commdlg.h>
#include <iostream>

#define PRINT_SIZE(type) \
    std::cout << #type << "=" << sizeof(type) << "\n";

int main()
{
    PRINT_SIZE(OPENFILENAMEW);
    PRINT_SIZE(POINT);
    PRINT_SIZE(RECT);
    PRINT_SIZE(MSG);
    PRINT_SIZE(PAINTSTRUCT);
    PRINT_SIZE(WNDCLASSEXW);
    PRINT_SIZE(BITMAPINFO);
    PRINT_SIZE(BITMAPINFOHEADER);
    PRINT_SIZE(RGBQUAD);
    PRINT_SIZE(WIN32_FIND_DATAW);
    PRINT_SIZE(FILETIME);
    return 0;
}
