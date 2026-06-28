#include "grade_service.h"
#include <stdlib.h>
#include <string.h>
#include <stdio.h>

void grade_service_init(GradeService *gs, StudentService *ss) {
    gs->student_service = ss;
}

int grade_service_add(GradeService *gs, const char *student_id,
                      const char *course_name, int score) {
    Student *student = student_service_find_by_id(gs->student_service, student_id);
    if (student == NULL) return 0;

    if (student_find_course(student, course_name) >= 0) {
        return 0;
    }

    student_add_course(student, course_name, score);
    return 1;
}

int grade_service_update(GradeService *gs, const char *student_id,
                         const char *course_name, int new_score) {
    Student *student = student_service_find_by_id(gs->student_service, student_id);
    if (student == NULL) return 0;

    int idx = student_find_course(student, course_name);
    if (idx < 0) return 0;

    student->courses[idx].score = new_score;
    student->scores_dirty = 1;
    return 1;
}

int grade_service_delete(GradeService *gs, const char *student_id,
                         const char *course_name) {
    Student *student = student_service_find_by_id(gs->student_service, student_id);
    if (student == NULL) return 0;

    int idx = student_find_course(student, course_name);
    if (idx < 0) return 0;

    student->courses[idx] = student->courses[student->course_count - 1];
    student->course_count--;
    student->scores_dirty = 1;
    return 1;
}

int grade_service_list(GradeService *gs, const char *student_id,
                       Course **out_courses, int *out_count) {
    Student *student = student_service_find_by_id(gs->student_service, student_id);
    if (student == NULL) {
        *out_courses = NULL;
        *out_count = 0;
        return 0;
    }

    *out_count = student->course_count;
    if (student->course_count == 0) {
        *out_courses = NULL;
        return 1;
    }

    *out_courses = (Course *)malloc(sizeof(Course) * student->course_count);
    int i;
    for (i = 0; i < student->course_count; i++) {
        (*out_courses)[i] = student->courses[i];
    }
    return 1;
}
