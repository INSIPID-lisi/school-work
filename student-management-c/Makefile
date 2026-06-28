CC = gcc
CFLAGS = -Wall -Wextra -std=c99
TARGET = student_management.exe

SRCS = app/app.c model/course.c model/student.c service/student_service.c service/grade_service.c service/sort_service.c
OBJS = $(SRCS:.c=.o)

.PHONY: all clean run

all: $(TARGET)

$(TARGET): $(OBJS)
	$(CC) $(CFLAGS) -o $@ $^

%.o: %.c
	$(CC) $(CFLAGS) -c -o $@ $<

clean:
	rm -f $(OBJS) $(TARGET)

run: $(TARGET)
	./$(TARGET)
