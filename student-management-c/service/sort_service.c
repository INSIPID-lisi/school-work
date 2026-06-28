#include "sort_service.h"
#include <string.h>
#include <stdio.h>

void sort_service_init(SortService *ss, StudentService *student_service) {
    ss->student_service = student_service;
}

/* 获取学生某课程成绩，未选课返回 -1 */
static int get_course_score(const Student *student, const char *course_name) {
    int idx = student_find_course(student, course_name);
    if (idx < 0) return -1;
    return student->courses[idx].score;
}

void sort_service_by_total_score(SortService *ss, int *indices, int *out_count, int ascending) {
    int n = ss->student_service->count;
    *out_count = n;
    int i;
    for (i = 0; i < n; i++) {
        indices[i] = i;
    }

    /* 冒泡排序 */
    int j;
    for (i = 0; i < n - 1; i++) {
        for (j = 0; j < n - 1 - i; j++) {
            double s1 = student_get_total_score(&ss->student_service->students[indices[j]]);
            double s2 = student_get_total_score(&ss->student_service->students[indices[j + 1]]);
            int need_swap = ascending ? (s1 > s2) : (s1 < s2);
            if (need_swap) {
                int temp = indices[j];
                indices[j] = indices[j + 1];
                indices[j + 1] = temp;
            }
        }
    }
}

void sort_service_by_course_score(SortService *ss, int *indices, int *out_count,
                                   const char *course_name, int ascending) {
    int n = ss->student_service->count;
    *out_count = n;
    int i;
    for (i = 0; i < n; i++) {
        indices[i] = i;
    }

    int j;
    for (i = 0; i < n - 1; i++) {
        for (j = 0; j < n - 1 - i; j++) {
            int s1 = get_course_score(&ss->student_service->students[indices[j]], course_name);
            int s2 = get_course_score(&ss->student_service->students[indices[j + 1]], course_name);
            int need_swap = ascending ? (s1 > s2) : (s1 < s2);
            if (need_swap) {
                int temp = indices[j];
                indices[j] = indices[j + 1];
                indices[j + 1] = temp;
            }
        }
    }
}

void sort_service_course_averages(SortService *ss, char (*course_names)[50],
                                   double *averages, int *out_count) {
    int i, j;
    int course_count = 0;
    int max_courses = 100;

    /* 用于去重的课程名集合 */
    char all_names[100][50];
    double sums[100];
    int counts[100];

    for (i = 0; i < ss->student_service->count; i++) {
        Student *s = &ss->student_service->students[i];
        for (j = 0; j < s->course_count; j++) {
            const char *cname = s->courses[j].name;
            int found = 0;
            int k;
            for (k = 0; k < course_count; k++) {
                if (strcmp(all_names[k], cname) == 0) {
                    found = 1;
                    sums[k] += s->courses[j].score;
                    counts[k]++;
                    break;
                }
            }
            if (!found && course_count < max_courses) {
                strncpy(all_names[course_count], cname, 49);
                all_names[course_count][49] = '\0';
                sums[course_count] = s->courses[j].score;
                counts[course_count] = 1;
                course_count++;
            }
        }
    }

    *out_count = course_count;
    for (i = 0; i < course_count; i++) {
        strncpy(course_names[i], all_names[i], 49);
        course_names[i][49] = '\0';
        averages[i] = sums[i] / counts[i];
    }
}
