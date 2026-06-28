#ifndef ADVANCED_CALCULATOR_MODEL_H
#define ADVANCED_CALCULATOR_MODEL_H

/**
 * 高级运算模型
 * 通过泰勒级数展开等方式手动实现幂运算、阶乘、三角函数、
 * 指数函数、对数函数等高级数学运算，不依赖 math.h
 */

/**
 * 幂运算
 * @param base 底数
 * @param exponent 指数（整数，可正可负）
 * @return 幂运算结果
 */
double advanced_power(double base, int exponent);

/**
 * 阶乘运算
 * @param num 非负整数
 * @return 阶乘结果
 */
long long advanced_factorial(int num);

/**
 * 获取π的值（莱布尼茨级数）
 * @return π的近似值
 */
double advanced_get_pi(void);

/**
 * 正弦函数（角度制），使用泰勒级数
 * @param degrees 角度值
 * @return 正弦值
 */
double advanced_sin_taylor(double degrees);

/**
 * 余弦函数（角度制），使用泰勒级数
 * @param degrees 角度值
 * @return 余弦值
 */
double advanced_cos_taylor(double degrees);

/**
 * 正切函数（角度制）
 * @param degrees 角度值
 * @return 正切值
 */
double advanced_tan_taylor(double degrees);

/**
 * 指数函数 e^x，使用泰勒级数
 * @param x 指数
 * @return e^x 的近似值
 */
double advanced_exp_taylor(double x);

/**
 * 自然对数 ln(1+x)
 * @param x 满足 x > -1
 * @return ln(1+x) 的近似值
 */
double advanced_ln_taylor(double x);

#endif
