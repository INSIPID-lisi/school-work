/**
 * 计算器系统入口
 * 启动控制台计算器应用的主函数
 */
#include "controller/calculator_controller.h"
#include <windows.h>

int main(void) {
    SetConsoleOutputCP(CP_UTF8);
    calculator_controller_start();
    return 0;
}
