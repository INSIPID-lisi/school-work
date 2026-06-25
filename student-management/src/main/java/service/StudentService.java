package service;

import model.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentService {

    private final List<Student> students = new ArrayList<>();

    public boolean addStudent(Student student) {
        if (findById(student.getId()) != null) {
            return false;
        }
        return students.add(student);
    }

    public boolean deleteStudent(String id) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId().equals(id)) {
                students.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean updateStudent(String id, String newName) {
        Student s = findById(id);
        if (s != null) {
            s.setName(newName);
            return true;
        }
        return false;
    }

    public Student findById(String id) {
        for (Student student : students) {
            if (student.getId().equals(id)) {
                return student;
            }
        }
        return null;
    }

    public List<Student> findByName(String name) {
        List<Student> result = new ArrayList<>();
        for (Student student : students) {
            if (student.getName().equals(name)) {
                result.add(student);
            }
        }
        return result;
    }

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
