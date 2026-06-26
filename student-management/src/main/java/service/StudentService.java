package service;

import model.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * 学生信息管理服务
 * 提供学生数据的增删改查功能，基于 ArrayList 内存存储
 */
public class StudentService {

    private final List<Student> students = new ArrayList<>();

    /**
     * 添加学生（学号唯一性校验）
     * @param student 学生对象
     * @return 是否添加成功
     */
    public boolean addStudent(Student student) {
        if (findById(student.getId()) != null) {
            return false;
        }
        return students.add(student);
    }

    /**
     * 按学号删除学生
     * @param id 学生学号
     * @return 是否删除成功
     */
    public boolean deleteStudent(String id) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId().equals(id)) {
                students.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * 修改学生姓名
     * @param id 学生学号
     * @param newName 新姓名
     * @return 是否修改成功
     */
    public boolean updateStudent(String id, String newName) {
        Student s = findById(id);
        if (s != null) {
            s.setName(newName);
            return true;
        }
        return false;
    }

    /**
     * 按学号查找学生
     * @param id 学号
     * @return 学生对象，未找到返回 null
     */
    public Student findById(String id) {
        for (Student student : students) {
            if (student.getId().equals(id)) {
                return student;
            }
        }
        return null;
    }

    /**
     * 按姓名查找学生（支持同名查找）
     * @param name 姓名
     * @return 匹配的学生列表
     */
    public List<Student> findByName(String name) {
        List<Student> result = new ArrayList<>();
        for (Student student : students) {
            if (student.getName().equals(name)) {
                result.add(student);
            }
        }
        return result;
    }

    /** @return 所有学生列表（副本） */
    public List<Student> listAll() {
        return new ArrayList<>(students);
    }

    public boolean isEmpty() {
        return students.isEmpty();
    }

    public int size() {
        return students.size();
    }
}
