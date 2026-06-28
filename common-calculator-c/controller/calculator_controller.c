#include "calculator_controller.h"
#include "../model/basic_calculator_model.h"
#include "../model/advanced_calculator_model.h"
#include <stdio.h>
#include <stdlib.h>

/**
 * 读取整数输入（带校验循环）
 */
static int get_int_input(const char *prompt) {
    int value;
    char buffer[100];
    while (1) {
        printf("%s: ", prompt);
        if (fgets(buffer, sizeof(buffer), stdin) == NULL) {
            exit(0);
        }
        if (sscanf(buffer, "%d", &value) == 1) {
            return value;
        }
    }
}

/**
 * 读取浮点数输入（带校验循环）
 */
static double get_double_input(const char *prompt) {
    double value;
    char buffer[100];
    while (1) {
        printf("%s: ", prompt);
        if (fgets(buffer, sizeof(buffer), stdin) == NULL) {
            exit(0);
        }
        if (sscanf(buffer, "%lf", &value) == 1) {
            return value;
        }
    }
}

/**
 * 六则运算菜单
 */
static void basic_menu(void) {
    printf("\n--- 六则运算 ---\n");
    printf("1. 加法\n");
    printf("2. 减法\n");
    printf("3. 乘法\n");
    printf("4. 除法\n");
    printf("5. 取余\n");
    printf("6. 倒数\n");
    printf("0. 返回主菜单\n");

    int op = get_int_input("请输入运算符编号");
    if (op == 0) {
        return;
    }

    if (op < 1 || op > 6) {
        printf("你好像选择了无效的运算选项\n");
        return;
    }

    double a = get_double_input("请输入第一个数");
    double b = 0;
    if (op != 6) {
        b = get_double_input("请输入第二个数");
    }

    if (op == 4 && b == 0) {
        printf("错误: 除数不能为0\n");
        return;
    }
    if (op == 5 && (int)b == 0) {
        printf("错误: 取余运算的模数不能为0\n");
        return;
    }
    if (op == 6 && a == 0) {
        printf("错误: 0没有倒数\n");
        return;
    }

    double result = basic_calculator_calculate(a, b, op);
    printf("运算结果: %.3f\n", result);
}

/**
 * 高级运算菜单
 */
static void advanced_menu(void) {
    printf("\n--- 高级运算 ---\n");
    printf("1. 幂运算 (a^b)\n");
    printf("2. 阶乘 (n!)\n");
    printf("3. 圆周率 \xcf\x80\n");
    printf("4. sin(x)    （角度制）\n");
    printf("5. cos(x)    （角度制）\n");
    printf("6. tan(x)    （角度制）\n");
    printf("7. e^x\n");
    printf("8. ln(1+x)\n");
    printf("0. 返回主菜单\n");

    int option = get_int_input("请选择");
    if (option == 0) {
        return;
    }

    if (option < 1 || option > 8) {
        printf("你好像选择了无效的运算选项\n");
        return;
    }

    switch (option) {
        case 1: {
            double base = get_double_input("底数");
            int exp = get_int_input("指数");
            printf("结果: %.3f\n", advanced_power(base, exp));
            break;
        }
        case 2: {
            int n = get_int_input("整数 n");
            long long fact = advanced_factorial(n);
            if (fact >= 0) {
                printf("%d! = %lld\n", n, fact);
            }
            break;
        }
        case 3:
            printf("\xcf\x80 = %.3f\n", advanced_get_pi());
            break;
        case 4: {
            double x = get_double_input("角度（度）");
            printf("sin(%.3f\xc2\xb0) \xE2\x89\x88 %.3f\n", x, advanced_sin_taylor(x));
            break;
        }
        case 5: {
            double x = get_double_input("角度（度）");
            printf("cos(%.3f\xc2\xb0) \xE2\x89\x88 %.3f\n", x, advanced_cos_taylor(x));
            break;
        }
        case 6: {
            double x = get_double_input("角度（度）");
            printf("tan(%.3f\xc2\xb0) \xE2\x89\x88 %.3f\n", x, advanced_tan_taylor(x));
            break;
        }
        case 7: {
            double x = get_double_input("x");
            printf("e^%.3f \xE2\x89\x88 %.3f\n", x, advanced_exp_taylor(x));
            break;
        }
        case 8: {
            double x = get_double_input("x (要求 x > -1)");
            printf("ln(1+%.3f) \xE2\x89\x88 %.3f\n", x, advanced_ln_taylor(x));
            break;
        }
        default:
            printf("你好像选择了无效的运算选项\n");
    }
}

/**
 * 显示主菜单
 */
static void show_main_menu(void) {
    printf("\n========== 计算器系统 ==========\n");
    printf("如你所见，这是一个没有前端或客户端页面的极简控制台计算器项目\n");
    printf("1. 基础的六则运算（加、减、乘、除、取余、倒数）\n");
    printf("2. 一些高级点的运算（幂、阶乘、\xcf\x80、三角函数等）\n");
    printf("0. 退出\n");
    printf("=================================\n");
}

void calculator_controller_start(void) {
    while (1) {
        show_main_menu();
        int choice = get_int_input("请选择");
        switch (choice) {
            case 1:
                basic_menu();
                break;
            case 2:
                advanced_menu();
                break;
            case 0:
                printf("感谢使用，再见！\n");
                return;
            default:
                printf("无效选项，请重新输入\n");
        }
    }
}
