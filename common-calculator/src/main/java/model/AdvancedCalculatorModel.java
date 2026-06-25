package model;

public class AdvancedCalculatorModel {

    // π缓存，避免重复计算
    private static Double piCache = null;

    //绝对值
    public double absolute(double num) {
        return num < 0 ? -num : num;
    }

    //幂运算
    public double power(double base, int exponent) {
        if (exponent == 0) {
            if (base == 0) {
                throw new ArithmeticException("0的零次幂无意义");
            }
            return 1;
        }

        double result = 1;
        int absExp = exponent > 0 ? exponent : -exponent;

        for (int i = 0; i < absExp; i++) {
            result *= base;
        }

        if (exponent < 0) {
            if (base == 0) {
                throw new ArithmeticException("0不能为负数次幂");
            }
            result = 1.0 / result;
        }

        return result;
    }

    //阶乘
    public long factorial(int num) {
        if (num < 0) {
            throw new IllegalArgumentException("负数没有阶乘，请输入非负整数");
        }
        if (num == 0 || num == 1) {
            return 1;
        }

        long result = 1;
        for (int i = 2; i <= num; i++) {
            result *= i;
            // 溢出检查long上限
            if (result < 0) {
                throw new ArithmeticException("阶乘结果过大");
            }
        }
        return result;
    }


    /**
     * 获取π的值
     * arctan x的泰勒展开：π/4 = 1 - 1/3 + 1/5 - 1/7 + ...
     * 首次计算后缓存结果，后续直接返回
     */
    public double getPi() {
        if (piCache != null) {
            return piCache;
        }
        double pi = 0;
        int maxTerms = 100000;
        for (int i = 0; i < maxTerms; i++) {
            double term = (i % 2 == 0 ? 1.0 : -1.0) / (2 * i + 1);
            pi += term;
        }
        piCache = pi * 4;
        return piCache;
    }

    // 角度转弧度
    private double toRadians(double degrees) {
        return degrees * getPi() / 180.0;
    }

    // 将弧度归约到 [-π, π] 范围
    private double reduceRange(double x) {
        double twoPi = 2 * getPi();
        x = x % twoPi;
        if (x > getPi()) x -= twoPi;
        else if (x < -getPi()) x += twoPi;
        return x;
    }

    // sin（接受角度制，如 sin(90) = 1）
    public double sinTaylor(double degrees) {
        double x = toRadians(degrees);
        x = reduceRange(x);

        double term = x;
        double sum = x;
        int n = 1;
        double eps = 1e-10;

        while (absolute(term) > eps) {
            term = -term * x * x / ((2 * n) * (2 * n + 1));
            sum += term;
            n++;
        }

        return sum;
    }

    // cos（接受角度制，如 cos(0) = 1）
    public double cosTaylor(double degrees) {
        double x = toRadians(degrees);
        x = reduceRange(x);

        double term = 1;
        double sum = 1;
        int n = 1;
        double eps = 1e-10;

        while (absolute(term) > eps) {
            term = -term * x * x / ((2 * n - 1) * (2 * n));
            sum += term;
            n++;
        }

        return sum;
    }

    // tan（接受角度制）
    public double tanTaylor(double degrees) {
        double sinVal = sinTaylor(degrees);
        double cosVal = cosTaylor(degrees);
        if (absolute(cosVal) < 1e-12) {
            throw new ArithmeticException("tan(" + degrees + "°) 无定义（cos接近0）");
        }
        return sinVal / cosVal;
    }

    // 扩展功能：计算 e^x 的泰勒展开
    public double expTaylor(double x) {
        double term = 1;
        double sum = 1;
        int n = 1;
        double eps = 1e-10;

        while (absolute(term) > eps) {
            term = term * x / n;
            sum += term;
            n++;
        }
        return sum;
    }

    // 扩展功能：自然对数 ln(1+x)
    public double lnTaylor(double x) {
        if (x <= -1) throw new IllegalArgumentException("ln(x+1) 要求 x > -1");
        if (x == 0) return 0;

        double eps = 1e-12;
        int maxIter = 100000;

        // 当 x > 0 时，原始级数在 x→1 时收敛极慢，x>1 时发散。
        // 利用恒等式变换：令 y = x/(1+x)，则 ln(1+x) = y + y^2/2 + y^3/3 + ...
        // 此时 0 < y < 1 恒成立，保证收敛。
        if (x > 0) {
            double y = x / (1 + x);
            double term = y;
            double sum = y;
            for (int n = 2; n < maxIter; n++) {
                term = power(y, n) / n;
                sum += term;
                if (term < eps) break;
            }
            return sum;
        }

        // x ∈ (-1, 0) 时使用原始交错级数，|x|<1 保证快速收敛
        double term = x;
        double sum = x;
        for (int n = 2; n < maxIter; n++) {
            term = (n % 2 == 0 ? -1.0 : 1.0) * power(x, n) / n;
            sum += term;
            if (absolute(term) < eps) break;
        }
        return sum;
    }

}
