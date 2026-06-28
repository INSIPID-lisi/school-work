cmake_minimum_required(VERSION 3.10)
project(student-management-c C)
set(CMAKE_C_STANDARD 99)
set(CMAKE_C_STANDARD_REQUIRED ON)

add_executable(student_management
    app/app.c
    model/course.c
    model/student.c
    service/student_service.c
    service/grade_service.c
    service/sort_service.c
)
