package model;


public class BasicCalculatorModel {

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
