import controller.CalculatorController;

/**
 * 计算器系统入口
 * 启动控制台计算器应用的主类
 */
public class Main {
    public static void main(String[] args) {
        CalculatorController controller = new CalculatorController();
        controller.start();
    }
}