/**
 * 学生成绩管理系统入口
 * 控制台菜单导航，整合学生管理、成绩管理、排序统计三大模块
 */
#include "../service/student_service.h"
#include "../service/grade_service.h"
#include "../service/sort_service.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <windows.h>

static StudentService student_service;
static GradeService grade_service;
static SortService sort_service;

/* ---------- 辅助函数 ---------- */

static int read_int(const char *prompt) {
    char buffer[100];
    int value;
    while (1) {
        printf("%s", prompt);
        if (fgets(buffer, sizeof(buffer), stdin) == NULL) {
            exit(0);
        }
        if (sscanf(buffer, "%d", &value) == 1) {
            return value;
        }
        printf("请输入有效的数字！\n");
    }
}

static void read_string(const char *prompt, char *out, int max_len) {
    printf("%s", prompt);
    if (fgets(out, max_len, stdin) == NULL) {
        exit(0);
    }
    size_t len = strlen(out);
    if (len > 0 && out[len - 1] == '\n') {
        out[len - 1] = '\0';
    }
}

/* ---------- 学生管理操作 ---------- */

static void add_student(void) {
    printf("\n--- 添加学生 ---\n");
    char id[20], name[50];
    read_string("请输入学号: ", id, sizeof(id));
    if (student_service_find_by_id(&student_service, id) != NULL) {
        printf("该学号已存在！\n");
        return;
    }
    read_string("请输入姓名: ", name, sizeof(name));
    if (student_service_add(&student_service, name, id)) {
        printf("学生添加成功！\n");
    }
}

static void delete_student(void) {
    printf("\n--- 删除学生 ---\n");
    char id[20];
    read_string("请输入要删除的学生学号: ", id, sizeof(id));
    if (student_service_delete(&student_service, id)) {
        printf("学生删除成功！\n");
    } else {
        printf("未找到该学号的学生！\n");
    }
}

static void update_student(void) {
    printf("\n--- 修改学生姓名 ---\n");
    char id[20], new_name[50];
    read_string("请输入要修改的学生学号: ", id, sizeof(id));
    read_string("请输入新的姓名: ", new_name, sizeof(new_name));
    if (student_service_update(&student_service, id, new_name)) {
        printf("姓名修改成功！\n");
    } else {
        printf("未找到该学号的学生！\n");
    }
}

static void query_student(void) {
    printf("\n--- 查询学生 ---\n");
    printf("1. 按学号查询\n");
    printf("2. 按姓名查询\n");
    int choice = read_int("请输入选择: ");
    if (choice == 1) {
        char id[20];
        read_string("请输入学号: ", id, sizeof(id));
        Student *s = student_service_find_by_id(&student_service, id);
        if (s != NULL) {
            printf("学号: %s, 姓名: %s", s->id, s->name);
            int j;
            for (j = 0; j < s->course_count; j++) {
                printf(", 成绩: %s: %d", s->courses[j].name, s->courses[j].score);
            }
            if (s->course_count > 0) {
                printf(", 总分: %d, 平均分: %.1f",
                       (int)student_get_total_score(s), student_get_avg_score(s));
            }
            printf("\n");
        } else {
            printf("未找到该学号的学生！\n");
        }
    } else if (choice == 2) {
        char name[50];
        int indices[100], result_size;
        read_string("请输入姓名: ", name, sizeof(name));
        student_service_find_by_name(&student_service, name, indices, &result_size);
        if (result_size == 0) {
            printf("未找到该姓名的学生！\n");
        } else {
            int i;
            for (i = 0; i < result_size; i++) {
                Student *s = student_service_get(&student_service, indices[i]);
                printf("学号: %s, 姓名: %s", s->id, s->name);
                int j;
                for (j = 0; j < s->course_count; j++) {
                    printf(", %s: %d", s->courses[j].name, s->courses[j].score);
                }
                if (s->course_count > 0) {
                    printf(", 总分: %d, 平均分: %.1f",
                           (int)student_get_total_score(s), student_get_avg_score(s));
                }
                printf("\n");
            }
        }
    } else {
        printf("无效选择！\n");
    }
}

static void list_all_students(void) {
    printf("\n--- 所有学生信息 ---\n");
    if (student_service_is_empty(&student_service)) {
        printf("暂无学生信息。\n");
        return;
    }
    int i;
    for (i = 0; i < student_service.count; i++) {
        Student *s = &student_service.students[i];
        printf("%d. 学号: %s, 姓名: %s", i + 1, s->id, s->name);
        int j;
        for (j = 0; j < s->course_count; j++) {
            printf(", %s: %d", s->courses[j].name, s->courses[j].score);
        }
        if (s->course_count > 0) {
            printf(", 总分: %d, 平均分: %.1f",
                   (int)student_get_total_score(s), student_get_avg_score(s));
        }
        printf("\n");
    }
}

/* ---------- 成绩管理 ---------- */

static void add_grade(void) {
    printf("\n--- 添加成绩 ---\n");
    char id[20], course[50];
    read_string("请输入学生学号: ", id, sizeof(id));
    read_string("请输入课程名称: ", course, sizeof(course));
    int score = read_int("请输入成绩: ");
    if (grade_service_add(&grade_service, id, course, score)) {
        printf("成绩添加成功！\n");
    } else {
        printf("添加失败，请检查学号是否存在或该课程成绩是否已存在！\n");
    }
}

static void update_grade(void) {
    printf("\n--- 修改成绩 ---\n");
    char id[20], course[50];
    read_string("请输入学生学号: ", id, sizeof(id));
    read_string("请输入课程名称: ", course, sizeof(course));
    int score = read_int("请输入新的成绩: ");
    if (grade_service_update(&grade_service, id, course, score)) {
        printf("成绩修改成功！\n");
    } else {
        printf("修改失败，请检查学号和课程名称是否正确！\n");
    }
}

static void delete_grade(void) {
    printf("\n--- 删除成绩 ---\n");
    char id[20], course[50];
    read_string("请输入学生学号: ", id, sizeof(id));
    read_string("请输入要删除的课程名称: ", course, sizeof(course));
    if (grade_service_delete(&grade_service, id, course)) {
        printf("成绩删除成功！\n");
    } else {
        printf("删除失败，请检查学号和课程名称是否正确！\n");
    }
}

static void list_grades(void) {
    printf("\n--- 查看学生成绩 ---\n");
    char id[20];
    read_string("请输入学生学号: ", id, sizeof(id));
    Course *courses;
    int count;
    int found = grade_service_list(&grade_service, id, &courses, &count);
    if (!found) {
        printf("未找到该学号的学生！\n");
    } else if (count == 0) {
        printf("该学生暂无成绩记录。\n");
    } else {
        printf("学生学号: %s\n", id);
        int i;
        for (i = 0; i < count; i++) {
            printf("  %s: %d\n", courses[i].name, courses[i].score);
        }
        free(courses);
    }
}

/* ---------- 排序与统计 ---------- */

static void sort_by_total_score(void) {
    printf("\n--- 按总成绩排序 ---\n");
    printf("1. 升序（从低到高）\n");
    printf("2. 降序（从高到低）\n");
    int order = read_int("请选择排序方式: ");
    int indices[100], count;
    sort_service_by_total_score(&sort_service, indices, &count, order == 1);
    if (count == 0) {
        printf("暂无学生数据。\n");
        return;
    }
    printf("排序结果：\n");
    int i;
    for (i = 0; i < count; i++) {
        Student *s = student_service_get(&student_service, indices[i]);
        printf("%d. %s (学号: %s) 总分: %d\n",
               i + 1, s->name, s->id, (int)student_get_total_score(s));
    }
}

static void sort_by_course_score(void) {
    printf("\n--- 按单科成绩排序 ---\n");
    char course[50];
    read_string("请输入课程名称: ", course, sizeof(course));
    printf("1. 升序（从低到高）\n");
    printf("2. 降序（从高到低）\n");
    int order = read_int("请选择排序方式: ");
    int indices[100], count;
    sort_service_by_course_score(&sort_service, indices, &count, course, order == 1);
    if (count == 0) {
        printf("暂无学生数据。\n");
        return;
    }
    printf("排序结果（课程: %s）：\n", course);
    int i;
    for (i = 0; i < count; i++) {
        Student *s = student_service_get(&student_service, indices[i]);
        int idx = student_find_course(s, course);
        if (idx >= 0) {
            printf("%d. %s (学号: %s) %s: %d\n",
                   i + 1, s->name, s->id, course, s->courses[idx].score);
        } else {
            printf("%d. %s (学号: %s) %s: 未选课\n",
                   i + 1, s->name, s->id, course);
        }
    }
}

static void show_course_averages(void) {
    printf("\n--- 各科平均分 ---\n");
    char course_names[100][50];
    double averages[100];
    int count;
    sort_service_course_averages(&sort_service, course_names, averages, &count);
    if (count == 0) {
        printf("暂无成绩数据。\n");
        return;
    }
    int i;
    for (i = 0; i < count; i++) {
        printf("%s: 平均分 %.1f\n", course_names[i], averages[i]);
    }
}

/* ---------- 菜单 ---------- */

static void student_management_menu(void) {
    while (1) {
        printf("\n--------------------------------------\n");
        printf("            学生信息管理\n");
        printf("--------------------------------------\n");
        printf("            1. 添加学生\n");
        printf("            2. 删除学生\n");
        printf("            3. 修改学生姓名\n");
        printf("            4. 查询学生\n");
        printf("            5. 显示所有学生\n");
        printf("            6. 返回主菜单\n");
        printf("--------------------------------------\n");

        int choice = read_int("请输入选择: ");
        switch (choice) {
            case 1: add_student(); break;
            case 2: delete_student(); break;
            case 3: update_student(); break;
            case 4: query_student(); break;
            case 5: list_all_students(); break;
            case 6: return;
            default: printf("无效选择，请重新输入。\n");
        }
    }
}

static void grade_management_menu(void) {
    while (1) {
        printf("\n--------------------------------------\n");
        printf("              成绩管理\n");
        printf("--------------------------------------\n");
        printf("            1. 添加成绩\n");
        printf("            2. 修改成绩\n");
        printf("            3. 删除成绩\n");
        printf("            4. 查看学生所有成绩\n");
        printf("            5. 返回主菜单\n");
        printf("--------------------------------------\n");

        int choice = read_int("请输入选择: ");
        switch (choice) {
            case 1: add_grade(); break;
            case 2: update_grade(); break;
            case 3: delete_grade(); break;
            case 4: list_grades(); break;
            case 5: return;
            default: printf("无效选择，请重新输入。\n");
        }
    }
}

static void sort_and_statistics_menu(void) {
    while (1) {
        printf("\n--------------------------------------\n");
        printf("           成绩排序与统计\n");
        printf("--------------------------------------\n");
        printf("            1. 按总成绩排序\n");
        printf("            2. 按单科成绩排序\n");
        printf("            3. 统计各科平均分\n");
        printf("            4. 返回主菜单\n");
        printf("--------------------------------------\n");

        int choice = read_int("请输入选择: ");
        switch (choice) {
            case 1: sort_by_total_score(); break;
            case 2: sort_by_course_score(); break;
            case 3: show_course_averages(); break;
            case 4: return;
            default: printf("无效选择，请重新输入。\n");
        }
    }
}

int main(void) {
    SetConsoleOutputCP(CP_UTF8);

    student_service_init(&student_service);
    grade_service_init(&grade_service, &student_service);
    sort_service_init(&sort_service, &student_service);

    while (1) {
        printf("\n");
        printf("======================================\n");
        printf("    普通的控制台版学生成绩档案管理系统\n");
        printf("======================================\n");
        printf("           1. 学生信息管理\n");
        printf("           2. 成绩管理\n");
        printf("           3. 成绩排序与统计\n");
        printf("           4. 退出\n");
        printf("======================================\n");

        int choice = read_int("请输入选择: ");
        switch (choice) {
            case 1: student_management_menu(); break;
            case 2: grade_management_menu(); break;
            case 3: sort_and_statistics_menu(); break;
            case 4:
                printf("感谢使用，再见！\n");
                student_service_destroy(&student_service);
                return 0;
            default:
                printf("无效选择，请重新输入。\n");
        }
    }
}
