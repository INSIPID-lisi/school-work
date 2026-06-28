CC = gcc
CFLAGS = -Wall -Wextra -std=c99
TARGET = calculator.exe

SRCS = main.c controller/calculator_controller.c model/basic_calculator_model.c model/advanced_calculator_model.c
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
