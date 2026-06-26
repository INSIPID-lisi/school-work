package service;

import model.Course;
import model.Student;

import java.util.List;

/**
 * 成绩管理服务
 * 通过 StudentService 委托查找学生，在其课程列表上执行成绩的增删改查
 */
public class GradeService {

    private final StudentService studentService;

    public GradeService(StudentService studentService) {
        this.studentService = studentService;
    }

    /**
     * 添加成绩
     * @param studentId 学号
     * @param courseName 课程名称
     * @param score 成绩
     * @return 是否添加成功
     */
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

    /**
     * 修改成绩
     * @param studentId 学号
     * @param courseName 课程名称
     * @param newScore 新成绩
     * @return 是否修改成功
     */
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

    /**
     * 删除成绩
     * @param studentId 学号
     * @param courseName 课程名称
     * @return 是否删除成功
     */
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

    /**
     * 查看某学生所有成绩
     * @param studentId 学号
     * @return 课程成绩列表，学生不存在返回 null
     */
    public List<Course> listGrades(String studentId) {
        Student student = studentService.findById(studentId);
        if (student == null) return null;
        return student.getCourseList();
    }
}
