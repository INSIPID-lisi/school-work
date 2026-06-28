#ifndef SORT_SERVICE_H
#define SORT_SERVICE_H

#include "student_service.h"

/**
 * 排序与统计服务
 * 提供按总成绩、单科成绩排序以及各科平均分统计功能
 */
typedef struct {
    StudentService *student_service;
} SortService;

/**
 * 初始化排序统计服务
 */
void sort_service_init(SortService *ss, StudentService *student_service);

/**
 * 按总成绩排序（冒泡排序）
 * @param indices 输出参数：排序后的学生索引数组
 * @param out_count 输出参数：学生数量
 * @param ascending 1 升序 / 0 降序
 */
void sort_service_by_total_score(SortService *ss, int *indices, int *out_count, int ascending);

/**
 * 按单科成绩排序（冒泡排序）
 * @param indices 输出参数：排序后的学生索引数组
 * @param out_count 输出参数：学生数量
 * @param course_name 课程名称
 * @param ascending 1 升序 / 0 降序
 */
void sort_service_by_course_score(SortService *ss, int *indices, int *out_count,
                                   const char *course_name, int ascending);

/**
 * 统计各科平均分
 * @param course_names 输出参数：课程名数组
 * @param averages 输出参数：对应平均分数组
 * @param out_count 输出参数：课程数量
 */
void sort_service_course_averages(SortService *ss, char (*course_names)[50],
                                   double *averages, int *out_count);

#endif
