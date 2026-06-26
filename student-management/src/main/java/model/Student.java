package model;

import java.util.ArrayList;
import java.util.List;

/**
 * 学生实体类
 * 包含学生基本信息（学号、姓名）及课程成绩列表
 */
public class Student {

    private String name;
    private String id;
    private List<Course> courseList;

    public Student() {
    }

    public Student(String name, String id) {
        this.name = name;
        this.id = id;
        this.courseList = new ArrayList<>();
    }

    public Student(String name, String id, List<Course> courseList) {
        this.name = name;
        this.id = id;
        this.courseList = courseList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Course> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
    }

    /**
     * 计算所有课程总分
     * @return 总分
     */
    public double getTotalScore() {
        double total = 0;
        for (int i = 0; i < courseList.size(); i++) {
            total += courseList.get(i).getScore();
        }
        return total;
    }

    /**
     * 计算各科平均分
     * @return 平均分（无成绩时返回 0）
     */
    public double getAverageScore() {
        if (courseList.isEmpty()) return 0;
        return getTotalScore() / courseList.size();
    }

    @Override
    public String toString() {
        String result = "学号: " + id + ", 姓名: " + name;
        if (courseList != null && !courseList.isEmpty()) {
            result += ", 成绩: " + courseList;
            result += ", 总分: " + (int) getTotalScore();
            result += ", 平均分: " + String.format("%.1f", getAverageScore());
        }
        return result;
    }
}
