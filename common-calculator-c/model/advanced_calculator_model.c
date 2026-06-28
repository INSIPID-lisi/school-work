#include "advanced_calculator_model.h"
#include <stdio.h>
#include <limits.h>

/* π缓存 */
static double pi_cache = 0.0;
static int pi_cached = 0;

/**
 * 求绝对值（内部辅助）
 */
static double adv_absolute(double num) {
    return num < 0 ? -num : num;
}

double advanced_power(double base, int exponent) {
    if (exponent == 0) {
        if (base == 0) {
            printf("0的零次幂无意义\n");
            return 0;
        }
        return 1;
    }

    double result = 1;
    int abs_exp = exponent > 0 ? exponent : -exponent;
    int i;

    for (i = 0; i < abs_exp; i++) {
        result *= base;
    }

    if (exponent < 0) {
        if (base == 0) {
            printf("0不能为负数次幂\n");
            return 0;
        }
        result = 1.0 / result;
    }

    return result;
}

long long advanced_factorial(int num) {
    if (num < 0) {
        printf("负数没有阶乘，请输入非负整数\n");
        return -1;
    }
    if (num == 0 || num == 1) {
        return 1;
    }

    long long result = 1;
    int i;
    for (i = 2; i <= num; i++) {
        if (result > LLONG_MAX / i) {
            printf("阶乘结果过大\n");
            return -1;
        }
        result *= i;
    }
    return result;
}

double advanced_get_pi(void) {
    if (pi_cached) {
        return pi_cache;
    }

    double pi = 0;
    int max_terms = 100000;
    int i;
    for (i = 0; i < max_terms; i++) {
        double term = (i % 2 == 0 ? 1.0 : -1.0) / (2 * i + 1);
        pi += term;
    }
    pi_cache = pi * 4;
    pi_cached = 1;
    return pi_cache;
}

/**
 * 角度转弧度（内部）
 */
static double to_radians(double degrees) {
    return degrees * advanced_get_pi() / 180.0;
}

/**
 * 将弧度归约到 [-π, π] 范围（内部）
 */
static double reduce_range(double x) {
    double two_pi = 2 * advanced_get_pi();
    x = x - (int)(x / two_pi) * two_pi;
    if (x > advanced_get_pi()) x -= two_pi;
    else if (x < -advanced_get_pi()) x += two_pi;
    return x;
}

double advanced_sin_taylor(double degrees) {
    double x = to_radians(degrees);
    x = reduce_range(x);

    double term = x;
    double sum = x;
    int n = 1;
    double eps = 1e-10;

    while (adv_absolute(term) > eps) {
        term = -term * x * x / ((2 * n) * (2 * n + 1));
        sum += term;
        n++;
    }

    return sum;
}

double advanced_cos_taylor(double degrees) {
    double x = to_radians(degrees);
    x = reduce_range(x);

    double term = 1;
    double sum = 1;
    int n = 1;
    double eps = 1e-10;

    while (adv_absolute(term) > eps) {
        term = -term * x * x / ((2 * n - 1) * (2 * n));
        sum += term;
        n++;
    }

    return sum;
}

double advanced_tan_taylor(double degrees) {
    double sin_val = advanced_sin_taylor(degrees);
    double cos_val = advanced_cos_taylor(degrees);
    if (adv_absolute(cos_val) < 1e-12) {
        printf("tan(%.0f) 无定义（cos接近0）\n", degrees);
        return 0;
    }
    return sin_val / cos_val;
}

double advanced_exp_taylor(double x) {
    double term = 1;
    double sum = 1;
    int n = 1;
    double eps = 1e-10;

    while (adv_absolute(term) > eps) {
        term = term * x / n;
        sum += term;
        n++;
    }
    return sum;
}

double advanced_ln_taylor(double x) {
    if (x <= -1) {
        printf("ln(x+1) 要求 x > -1\n");
        return 0;
    }
    if (x == 0) return 0;

    double eps = 1e-12;
    int max_iter = 100000;

    if (x > 0) {
        double y = x / (1 + x);
        double term = y;
        double sum = y;
        int n;
        for (n = 2; n < max_iter; n++) {
            term = advanced_power(y, n) / n;
            sum += term;
            if (term < eps) break;
        }
        return sum;
    }

    /* x ∈ (-1, 0) */
    double term = x;
    double sum = x;
    int n;
    for (n = 2; n < max_iter; n++) {
        term = (n % 2 == 0 ? -1.0 : 1.0) * advanced_power(x, n) / n;
        sum += term;
        if (adv_absolute(term) < eps) break;
    }
    return sum;
}
