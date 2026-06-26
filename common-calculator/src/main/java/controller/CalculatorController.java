package controller;

import model.BasicCalculatorModel;
import model.AdvancedCalculatorModel;
import java.util.Scanner;

/**
 * 计算器控制器
 * 负责菜单显示、用户输入处理与流程控制，
 * 委托计算逻辑给 BasicCalculatorModel 和 AdvancedCalculatorModel
 */
public class CalculatorController {

    private final BasicCalculatorModel basicModel = new BasicCalculatorModel();
    private final AdvancedCalculatorModel advancedModel = new AdvancedCalculatorModel();
    private final Scanner scanner = new Scanner(System.in);

    /** 启动计算器主循环 */
    public void start() {
        while (true) {
            showMainMenu();
            int choice = getIntInput("请选择");
            switch (choice) {
                case 1 -> basicMenu();
                case 2 -> advancedMenu();
                case 0 -> {
                    System.out.println("感谢使用，再见！");
                    return;
                }
                default -> System.out.println("无效选项，请重新输入");
            }
        }
    }

    private void showMainMenu() {
        System.out.println("\n========== 计算器系统 ==========");
        System.out.println("如你所见，这是一个没有前端或客户端页面的极简控制台计算器项目");
        System.out.println("1. 基础的六则运算（加、减、乘、除、取余、倒数）");
        System.out.println("2. 一些高级点的运算（幂、阶乘、π、三角函数等）");
        System.out.println("0. 退出");
        System.out.println("=================================");
    }

    /**
     * 六则运算菜单
     * 处理加、减、乘、除、取余、倒数的输入与校验
     */
    private void basicMenu() {
        System.out.println("\n--- 六则运算 ---");
        System.out.println("1. 加法");
        System.out.println("2. 减法");
        System.out.println("3. 乘法");
        System.out.println("4. 除法");
        System.out.println("5. 取余");
        System.out.println("6. 倒数");
        System.out.println("0. 返回主菜单");

        int op = getIntInput("请输入运算符编号");
        if (op == 0) {
            return;
        }

        if (op < 1 || op > 6) {
            System.out.println("你好像选择了无效的运算选项");
            return;
        }

        double a = getDoubleInput("请输入第一个数");
        double b = 0;
        // 第六个选项，求倒数只需要取一个数
        if (op != 6) {
            b = getDoubleInput("请输入第二个数");
        }
        // 除法和取余等的零检查
        if (op == 4 && b == 0) {
            System.out.println("错误: 除数不能为0");
            return;
        }
        if (op == 5 && b == 0) {
            System.out.println("错误: 取余运算的模数不能为0");
            return;
        }
        if (op == 6 && a == 0) {
            System.out.println("错误: 0没有倒数");
            return;
        }

        try {
            double result = basicModel.calculate(a, b, op);
            System.out.printf("运算结果: %.3f%n", result);
        } catch (Exception e) {
            System.out.println("出现错误: " + e.getMessage());
        }
    }

    /**
     * 高级运算菜单
     * 处理幂、阶乘、π、三角函数、指数、对数等高级运算的输入与校验
     */
    private void advancedMenu() {
        System.out.println("\n--- 高级运算 ---");
        System.out.println("1. 幂运算 (a^b)");
        System.out.println("2. 阶乘 (n!)");
        System.out.println("3. 圆周率 π");
        System.out.println("4. sin(x)    （角度制）");
        System.out.println("5. cos(x)    （角度制）");
        System.out.println("6. tan(x)    （角度制）");
        System.out.println("7. e^x");
        System.out.println("8. ln(1+x)");
        System.out.println("0. 返回主菜单");

        int option = getIntInput("请选择");
        if (option == 0) {
            return;
        }

        if (option < 1 || option > 8) {
            System.out.println("你好像选择了无效的运算选项");
            return;
        }

        try {
            switch (option) {
                case 1 -> {
                    double base = getDoubleInput("底数");
                    int exp = getIntInput("指数");
                    System.out.printf("结果: %.3f%n", advancedModel.power(base, exp));
                }
                case 2 -> {
                    int n = getIntInput("整数 n");
                    System.out.printf("%d! = %.3f%n", n, (double) advancedModel.factorial(n));
                }
                case 3 -> System.out.printf("π = %.3f%n", advancedModel.getPi());
                case 4 -> {
                    double x = getDoubleInput("角度（度）");
                    System.out.printf("sin(%.3f°) ≈ %.3f%n", x, advancedModel.sinTaylor(x));
                }
                case 5 -> {
                    double x = getDoubleInput("角度（度）");
                    System.out.printf("cos(%.3f°) ≈ %.3f%n", x, advancedModel.cosTaylor(x));
                }
                case 6 -> {
                    double x = getDoubleInput("角度（度）");
                    System.out.printf("tan(%.3f°) ≈ %.3f%n", x, advancedModel.tanTaylor(x));
                }
                case 7 -> {
                    double x = getDoubleInput("x");
                    System.out.printf("e^%.3f ≈ %.3f%n", x, advancedModel.expTaylor(x));
                }
                case 8 -> {
                    double x = getDoubleInput("x (要求 x > -1)");
                    System.out.printf("ln(1+%.3f) ≈ %.3f%n", x, advancedModel.lnTaylor(x));
                }
                default -> System.out.println("你好像选择了无效的运算选项");
            }
        } catch (ArithmeticException e) {
            System.out.println("数学错误: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("输入错误: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("出现了未知错误: " + e.getMessage());
        }
    }

    /**
     * 读取整数输入（带校验循环）
     * @param prompt 输入提示文字
     * @return 用户输入的整数
     */
    private int getIntInput(String prompt) {
        System.out.print(prompt + ": ");
        while (true) {
            if (scanner.hasNextInt()) {
                return scanner.nextInt();
            } else {
                String invalid = scanner.next();
                System.out.print("请输入有效的整数（你输入了: " + invalid + "）: ");
            }
        }
    }

    /**
     * 读取浮点数输入（带校验循环）
     * @param prompt 输入提示文字
     * @return 用户输入的浮点数
     */
    private double getDoubleInput(String prompt) {
            System.out.print(prompt + ": ");
            while (true) {
                if (scanner.hasNextDouble()) {
                    return scanner.nextDouble();
                } else {
                    String invalid = scanner.next();
                    System.out.print("请输入有效的数字（你输入了: " + invalid + "）: ");
                }
            }
        }
}