#ifndef STUDENT_H
#define STUDENT_H

#include "course.h"

#define INIT_COURSE_CAPACITY 4

/**
 * 学生实体
 * 包含学生基本信息（学号、姓名）及课程成绩列表
 */
typedef struct {
    char id[20];
    char name[50];
    Course *courses;
    int course_count;
    int course_capacity;
    double total_score;   /* 缓存总分 */
    double avg_score;     /* 缓存平均分 */
    int scores_dirty;     /* 标记缓存是否需要刷新 */
} Student;

/**
 * 初始化学生
 */
void student_init(Student *s, const char *name, const char *id);

/**
 * 释放学生内部资源（课程数组）
 */
void student_destroy(Student *s);

/**
 * 添加课程成绩
 */
void student_add_course(Student *s, const char *name, int score);

/**
 * 按课程名查找成绩索引，未找到返回 -1
 */
int student_find_course(const Student *s, const char *name);

/**
 * 刷新总分/平均分缓存
 */
void student_refresh_scores(Student *s);

/**
 * 获取总分（自动刷新缓存）
 */
double student_get_total_score(Student *s);

/**
 * 获取平均分（自动刷新缓存）
 */
double student_get_avg_score(Student *s);

#endif
