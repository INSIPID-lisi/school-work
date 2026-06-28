#ifndef BASIC_CALCULATOR_MODEL_H
#define BASIC_CALCULATOR_MODEL_H

/**
 * 基础运算模型
 * 提供加、减、乘、除、取余、倒数六种基本运算
 */

/**
 * 执行二元运算
 * @param a 第一个操作数
 * @param b 第二个操作数（倒数运算时忽略）
 * @param operator 运算符编号：1加 2减 3乘 4除 5取余 6倒数
 * @return 运算结果
 */
double basic_calculator_calculate(double a, double b, int operator);

#endif
