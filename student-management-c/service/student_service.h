#ifndef STUDENT_SERVICE_H
#define STUDENT_SERVICE_H

#include "../model/student.h"

#define INIT_STUDENT_CAPACITY 16

/**
 * 学生信息管理服务
 * 提供学生数据的增删改查功能，基于动态数组内存存储
 */
typedef struct {
    Student *students;
    int count;
    int capacity;
} StudentService;

/**
 * 初始化学生服务
 */
void student_service_init(StudentService *ss);

/**
 * 释放学生服务内部资源
 */
void student_service_destroy(StudentService *ss);

/**
 * 添加学生（学号唯一性校验）
 * @return 是否添加成功
 */
int student_service_add(StudentService *ss, const char *name, const char *id);

/**
 * 按学号删除学生
 * @return 是否删除成功
 */
int student_service_delete(StudentService *ss, const char *id);

/**
 * 修改学生姓名
 * @return 是否修改成功
 */
int student_service_update(StudentService *ss, const char *id, const char *new_name);

/**
 * 按学号查找学生
 * @return 学生指针，未找到返回 NULL
 */
Student *student_service_find_by_id(StudentService *ss, const char *id);

/**
 * 按姓名查找学生
 * @param result 存放结果索引的数组
 * @param result_size 输出参数：找到的数量
 */
void student_service_find_by_name(StudentService *ss, const char *name,
                                   int *result_indices, int *result_size);

/**
 * 列出所有学生（返回副本数组，需 free）
 * @param out_count 输出参数：学生数量
 * @return 动态分配的学生数组副本
 */
Student *student_service_list_all(StudentService *ss, int *out_count);

/**
 * 获取某个索引的学生指针
 */
Student *student_service_get(StudentService *ss, int index);

/**
 * 判断是否为空
 */
int student_service_is_empty(StudentService *ss);

/**
 * 获取学生数量
 */
int student_service_size(StudentService *ss);

#endif
