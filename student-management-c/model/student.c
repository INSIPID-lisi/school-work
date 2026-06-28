#include "student.h"
#include <stdlib.h>
#include <string.h>
#include <stdio.h>

void student_init(Student *s, const char *name, const char *id) {
    strncpy(s->id, id, sizeof(s->id) - 1);
    s->id[sizeof(s->id) - 1] = '\0';
    strncpy(s->name, name, sizeof(s->name) - 1);
    s->name[sizeof(s->name) - 1] = '\0';
    s->courses = (Course *)malloc(sizeof(Course) * INIT_COURSE_CAPACITY);
    s->course_count = 0;
    s->course_capacity = INIT_COURSE_CAPACITY;
    s->total_score = 0;
    s->avg_score = 0;
    s->scores_dirty = 0;
}

void student_destroy(Student *s) {
    free(s->courses);
    s->courses = NULL;
    s->course_count = 0;
    s->course_capacity = 0;
}

void student_add_course(Student *s, const char *name, int score) {
    if (s->course_count >= s->course_capacity) {
        s->course_capacity *= 2;
        Course *new_courses = (Course *)realloc(s->courses, sizeof(Course) * s->course_capacity);
        if (new_courses == NULL) {
            printf("内存分配失败\n");
            return;
        }
        s->courses = new_courses;
    }
    course_init(&s->courses[s->course_count], name, score);
    s->course_count++;
    s->scores_dirty = 1;
}

int student_find_course(const Student *s, const char *name) {
    int i;
    for (i = 0; i < s->course_count; i++) {
        if (strcmp(s->courses[i].name, name) == 0) {
            return i;
        }
    }
    return -1;
}

void student_refresh_scores(Student *s) {
    double total = 0;
    int i;
    for (i = 0; i < s->course_count; i++) {
        total += s->courses[i].score;
    }
    s->total_score = total;
    s->avg_score = s->course_count > 0 ? total / s->course_count : 0;
    s->scores_dirty = 0;
}

double student_get_total_score(Student *s) {
    if (s->scores_dirty) {
        student_refresh_scores(s);
    }
    return s->total_score;
}

double student_get_avg_score(Student *s) {
    if (s->scores_dirty) {
        student_refresh_scores(s);
    }
    return s->avg_score;
}
