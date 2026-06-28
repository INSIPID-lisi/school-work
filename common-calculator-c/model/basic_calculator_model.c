#include "basic_calculator_model.h"
#include <stdio.h>

double basic_calculator_calculate(double a, double b, int operator) {
    switch (operator) {
        case 1:
            return a + b;
        case 2:
            return a - b;
        case 3:
            return a * b;
        case 4:
            return a / b;
        case 5:
            /* 手动实现浮点数取余：a - (int)(a/b) * b */
            return a - (int)(a / b) * b;
        case 6:
            return 1.0 / a;
        default:
            printf("你好像选择了无效的运算选项\n");
            return 0;
    }
}
