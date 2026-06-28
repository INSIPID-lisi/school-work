#ifndef COURSE_H
#define COURSE_H

/**
 * 课程成绩实体
 * 包含课程名称与对应的考试成绩
 */
typedef struct {
    char name[50];
    int score;
} Course;

/**
 * 初始化课程
 */
void course_init(Course *c, const char *name, int score);

#endif
