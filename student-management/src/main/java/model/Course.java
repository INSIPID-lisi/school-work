package model;

/**
 * 课程成绩实体类
 * 包含课程名称与对应的考试成绩
 */
public class Course {

    private String name;
    private int score;

    public Course() {
    }

    public Course(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return name + ": " + score;
    }
}
