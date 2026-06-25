package service;

import model.Course;
import model.Student;

import java.util.*;

public class SortService {

    private final StudentService studentService;

    public SortService(StudentService studentService) {
        this.studentService = studentService;
    }

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
