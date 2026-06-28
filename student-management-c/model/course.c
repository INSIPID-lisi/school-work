#include "course.h"
#include <string.h>

void course_init(Course *c, const char *name, int score) {
    strncpy(c->name, name, sizeof(c->name) - 1);
    c->name[sizeof(c->name) - 1] = '\0';
    c->score = score;
}
