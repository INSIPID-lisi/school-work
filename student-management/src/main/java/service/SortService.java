package service;

import model.Course;
import model.Student;

import java.util.*;

/**
 * 排序与统计服务
 * 提供按总成绩、单科成绩排序以及各科平均分统计功能
 */
public class SortService {

    private final StudentService studentService;

    public SortService(StudentService studentService) {
        this.studentService = studentService;
    }

    /**
     * 按总成绩排序（冒泡排序）
     * @param ascending true 升序 / false 降序
     * @return 排序后的学生列表
     */
    public List<Student> sortByTotalScore(boolean ascending) {
        List<Student> list = studentService.listAll();

        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = 0; j < list.size() - 1 - i; j++) {
                double score1 = list.get(j).getTotalScore();
                double score2 = list.get(j + 1).getTotalScore();
                boolean needSwap = ascending ? (score1 > score2) : (score1 < score2);
                if (needSwap) {
                    Student temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                }
            }
        }
        return list;
    }

    /**
     * 按单科成绩排序（冒泡排序）
     * @param courseName 课程名称
     * @param ascending true 升序 / false 降序
     * @return 排序后的学生列表
     */
    public List<Student> sortByCourseScore(String courseName, boolean ascending) {
        List<Student> list = studentService.listAll();

        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = 0; j < list.size() - 1 - i; j++) {
                int score1 = getCourseScore(list.get(j), courseName);
                int score2 = getCourseScore(list.get(j + 1), courseName);
                boolean needSwap = ascending ? (score1 > score2) : (score1 < score2);
                if (needSwap) {
                    Student temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                }
            }
        }
        return list;
    }

    private int getCourseScore(Student student, String courseName) {
        List<Course> courses = student.getCourseList();
        for (Course course : courses) {
            if (course.getName().equals(courseName)) {
                return course.getScore();
            }
        }
        return -1;
    }

    /**
     * 统计各科平均分
     * 遍历所有学生的成绩，按课程名分组后计算平均值
     * @return 课程名到平均分的映射
     */
    public Map<String, Double> getCourseAverages() {
        List<Student> all = studentService.listAll();
        Map<String, List<Integer>> courseScores = new HashMap<>();

        for (Student student : all) {
            List<Course> courses = student.getCourseList();
            for (Course course : courses) {

                List<Integer> scores = courseScores.get(course.getName());
                if (scores == null) {
                    scores = new ArrayList<>();
                    courseScores.put(course.getName(), scores);
                }
                scores.add(course.getScore());
            }
        }

        Map<String, Double> averages = new LinkedHashMap<>();
        for (String courseName : courseScores.keySet()) {
            List<Integer> scores = courseScores.get(courseName);
            double sum = 0;
            for (Integer score : scores) {
                sum += score;
            }
            averages.put(courseName, sum / scores.size());
        }
        return averages;
    }
}
