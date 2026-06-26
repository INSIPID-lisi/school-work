package model;

/**
 * 高级运算模型
 * 通过泰勒级数展开等方式手动实现幂运算、阶乘、三角函数、
 * 指数函数、对数函数等高级数学运算，不依赖 java.lang.Math
 */
public class AdvancedCalculatorModel {

    // π缓存，避免重复计算
    private static Double piCache = null;

    /**
     * 求绝对值
     * @param num 输入数值
     * @return 绝对值
     */
    public double absolute(double num) {
        return num < 0 ? -num : num;
    }

    /**
     * 幂运算
     * @param base 底数
     * @param exponent 指数（整数，可正可负）
     * @return 幂运算结果
     * @throws ArithmeticException 0的0次幂或0的负数次幂无意义
     */
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

    /**
     * 阶乘运算
     * @param num 非负整数
     * @return 阶乘结果
     * @throws IllegalArgumentException 负数没有阶乘
     * @throws ArithmeticException 结果溢出 long 范围
     */
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

    /**
     * 角度转弧度
     * @param degrees 角度值
     * @return 弧度值
     */
    private double toRadians(double degrees) {
        return degrees * getPi() / 180.0;
    }

    /**
     * 将弧度归约到 [-π, π] 范围
     * @param x 弧度值
     * @return 归约后的弧度值
     */
    private double reduceRange(double x) {
        double twoPi = 2 * getPi();
        x = x % twoPi;
        if (x > getPi()) x -= twoPi;
        else if (x < -getPi()) x += twoPi;
        return x;
    }

    /**
     * 正弦函数（角度制）
     * 使用泰勒级数 sin(x) = x - x^3/3! + x^5/5! - ... 实现
     * @param degrees 角度值
     * @return 正弦值
     */
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

    /**
     * 余弦函数（角度制）
     * 使用泰勒级数 cos(x) = 1 - x^2/2! + x^4/4! - ... 实现
     * @param degrees 角度值
     * @return 余弦值
     */
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

    /**
     * 正切函数（角度制）
     * 通过 tan(x) = sin(x) / cos(x) 实现
     * @param degrees 角度值
     * @return 正切值
     * @throws ArithmeticException cos 接近 0 时函数无定义
     */
    public double tanTaylor(double degrees) {
        double sinVal = sinTaylor(degrees);
        double cosVal = cosTaylor(degrees);
        if (absolute(cosVal) < 1e-12) {
            throw new ArithmeticException("tan(" + degrees + "°) 无定义（cos接近0）");
        }
        return sinVal / cosVal;
    }

    /**
     * 指数函数 e^x
     * 使用泰勒级数 e^x = 1 + x + x^2/2! + x^3/3! + ... 实现
     * @param x 指数
     * @return e^x 的近似值
     */
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

    /**
     * 自然对数 ln(1+x)
     * x>0 时使用 y=x/(1+x) 变换级数保证收敛，-1<x<0 时使用交错级数
     * @param x 满足 x > -1
     * @return ln(1+x) 的近似值
     * @throws IllegalArgumentException x <= -1 时无定义
     */
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
