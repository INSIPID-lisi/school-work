#ifndef GRADE_SERVICE_H
#define GRADE_SERVICE_H

#include "student_service.h"

/**
 * 成绩管理服务
 * 通过 StudentService 查找学生，在其课程列表上执行成绩的增删改查
 */
typedef struct {
    StudentService *student_service;
} GradeService;

/**
 * 初始化成绩管理服务
 */
void grade_service_init(GradeService *gs, StudentService *ss);

/**
 * 添加成绩
 * @return 是否添加成功
 */
int grade_service_add(GradeService *gs, const char *student_id,
                      const char *course_name, int score);

/**
 * 修改成绩
 * @return 是否修改成功
 */
int grade_service_update(GradeService *gs, const char *student_id,
                         const char *course_name, int new_score);

/**
 * 删除成绩
 * @return 是否删除成功
 */
int grade_service_delete(GradeService *gs, const char *student_id,
                         const char *course_name);

/**
 * 查看某学生所有成绩
 * @param out_courses 输出参数：课程数组
 * @param out_count 输出参数：课程数量
 * @return 0 表示学生不存在
 */
int grade_service_list(GradeService *gs, const char *student_id,
                       Course **out_courses, int *out_count);

#endif
