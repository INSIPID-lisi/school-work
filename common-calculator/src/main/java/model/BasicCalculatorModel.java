package model;


/**
 * 基础运算模型
 * 提供加、减、乘、除、取余、倒数六种基本运算
 */
public class BasicCalculatorModel {

    /**
     * 执行二元运算
     * @param a 第一个操作数
     * @param b 第二个操作数（倒数运算时忽略）
     * @param operator 运算符编号：1加 2减 3乘 4除 5取余 6倒数
     * @return 运算结果
     */
    public double calculate(double a, double b, int operator) {
        return switch (operator) {
            case 1 -> a + b;
            case 2 -> a - b;
            case 3 -> a * b;
            case 4 -> a / b;
            case 5 -> a % b;
            case 6 -> 1/a;
            default -> throw new IllegalArgumentException("你好像选择了无效的运算选项");
        };
    }
}
