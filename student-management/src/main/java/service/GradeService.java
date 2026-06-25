package service;

import model.Course;
import model.Student;

import java.util.List;

public class GradeService {

    private final StudentService studentService;

    public GradeService(StudentService studentService) {
        this.studentService = studentService;
    }

    public boolean addGrade(String studentId, String courseName, int score) {
        Student student = studentService.findById(studentId);
        if (student == null) return false;

        List<Course> courses = student.getCourseList();

        for (Course course : courses) {
            if (course.getName().equals(courseName)) {
                return false;
            }
        }

        return courses.add(new Course(courseName, score));
    }

    public boolean updateGrade(String studentId, String courseName, int newScore) {
        Student student = studentService.findById(studentId);
        if (student == null) return false;

        List<Course> courses = student.getCourseList();
        for (Course course : courses) {
            if (course.getName().equals(courseName)) {
                course.setScore(newScore);
                return true;
            }
        }
        return false;
    }

    public boolean deleteGrade(String studentId, String courseName) {
        Student student = studentService.findById(studentId);
        if (student == null) return false;

        List<Course> courses = student.getCourseList();
        for (Course course : courses) {
            if (course.getName().equals(courseName)) {
                courses.remove(course);
                return true;
            }
        }
        return false;
    }

    public List<Course> listGrades(String studentId) {
        Student student = studentService.findById(studentId);
        if (student == null) return null;
        return student.getCourseList();
    }
}
