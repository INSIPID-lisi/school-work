#include "student_service.h"
#include <stdlib.h>
#include <string.h>
#include <stdio.h>

void student_service_init(StudentService *ss) {
    ss->students = (Student *)malloc(sizeof(Student) * INIT_STUDENT_CAPACITY);
    ss->count = 0;
    ss->capacity = INIT_STUDENT_CAPACITY;
}

void student_service_destroy(StudentService *ss) {
    int i;
    for (i = 0; i < ss->count; i++) {
        student_destroy(&ss->students[i]);
    }
    free(ss->students);
    ss->students = NULL;
    ss->count = 0;
    ss->capacity = 0;
}

int student_service_add(StudentService *ss, const char *name, const char *id) {
    if (student_service_find_by_id(ss, id) != NULL) {
        return 0;
    }

    if (ss->count >= ss->capacity) {
        ss->capacity *= 2;
        Student *new_students = (Student *)realloc(ss->students, sizeof(Student) * ss->capacity);
        if (new_students == NULL) {
            printf("内存分配失败\n");
            return 0;
        }
        ss->students = new_students;
    }

    student_init(&ss->students[ss->count], name, id);
    ss->count++;
    return 1;
}

int student_service_delete(StudentService *ss, const char *id) {
    int i;
    for (i = 0; i < ss->count; i++) {
        if (strcmp(ss->students[i].id, id) == 0) {
            student_destroy(&ss->students[i]);
            ss->count--;
            if (i < ss->count) {
                ss->students[i] = ss->students[ss->count];
            }
            return 1;
        }
    }
    return 0;
}

int student_service_update(StudentService *ss, const char *id, const char *new_name) {
    Student *s = student_service_find_by_id(ss, id);
    if (s != NULL) {
        strncpy(s->name, new_name, sizeof(s->name) - 1);
        s->name[sizeof(s->name) - 1] = '\0';
        return 1;
    }
    return 0;
}

Student *student_service_find_by_id(StudentService *ss, const char *id) {
    int i;
    for (i = 0; i < ss->count; i++) {
        if (strcmp(ss->students[i].id, id) == 0) {
            return &ss->students[i];
        }
    }
    return NULL;
}

void student_service_find_by_name(StudentService *ss, const char *name,
                                   int *result_indices, int *result_size) {
    int i;
    *result_size = 0;
    for (i = 0; i < ss->count; i++) {
        if (strcmp(ss->students[i].name, name) == 0) {
            result_indices[*result_size] = i;
            (*result_size)++;
        }
    }
}

Student *student_service_list_all(StudentService *ss, int *out_count) {
    *out_count = ss->count;
    if (ss->count == 0) return NULL;

    Student *copy = (Student *)malloc(sizeof(Student) * ss->count);
    int i;
    for (i = 0; i < ss->count; i++) {
        student_init(&copy[i], ss->students[i].name, ss->students[i].id);
        int j;
        for (j = 0; j < ss->students[i].course_count; j++) {
            student_add_course(&copy[i], ss->students[i].courses[j].name,
                               ss->students[i].courses[j].score);
        }
    }
    return copy;
}

Student *student_service_get(StudentService *ss, int index) {
    if (index < 0 || index >= ss->count) return NULL;
    return &ss->students[index];
}

int student_service_is_empty(StudentService *ss) {
    return ss->count == 0;
}

int student_service_size(StudentService *ss) {
    return ss->count;
}
