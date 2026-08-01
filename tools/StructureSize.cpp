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
    return 0;
}