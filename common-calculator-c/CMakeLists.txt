cmake_minimum_required(VERSION 3.10)
project(common-calculator-c C)
set(CMAKE_C_STANDARD 99)
set(CMAKE_C_STANDARD_REQUIRED ON)

add_executable(calculator
    main.c
    controller/calculator_controller.c
    model/basic_calculator_model.c
    model/advanced_calculator_model.c
)
