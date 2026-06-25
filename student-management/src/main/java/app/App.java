package app;

import model.Course;
import model.Student;
import service.GradeService;
import service.SortService;
import service.StudentService;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class App {

    private static final Scanner scanner = new Scanner(System.in);
    private static final StudentService studentService = new StudentService();
    private static final GradeService gradeService = new GradeService(studentService);
    private static final SortService sortService = new SortService(studentService);

    public static void main(String[] args) {
        while (true) {
            showMainMenu();
            int choice = readInt("请输入选择: ");
            switch (choice) {
                case 1:
                    studentManagementMenu();
                    break;
                case 2:
                    gradeManagementMenu();
                    break;
                case 3:
                    sortAndStatisticsMenu();
                    break;
                case 4:
                    System.out.println("感谢使用，再见！");
                    return;
                default:
                    System.out.println("无效选择，请重新输入。");
            }
        }
    }

    private static void showMainMenu() {
        System.out.println();
        System.out.println("======================================");
        System.out.println("    普通的控制台版学生成绩档案管理系统");
        System.out.println("======================================");
        System.out.println("           1. 学生信息管理");
        System.out.println("           2. 成绩管理");
        System.out.println("           3. 成绩排序与统计");
        System.out.println("           4. 退出");
        System.out.println("======================================");
    }

    private static void studentManagementMenu() {
        while (true) {
            System.out.println("\n--------------------------------------");
            System.out.println("            学生信息管理");
            System.out.println("--------------------------------------");
            System.out.println("            1. 添加学生");
            System.out.println("            2. 删除学生");
            System.out.println("            3. 修改学生姓名");
            System.out.println("            4. 查询学生");
            System.out.println("            5. 显示所有学生");
            System.out.println("            6. 返回主菜单");
            System.out.println("--------------------------------------");

            int choice = readInt("请输入选择: ");
            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    deleteStudent();
                    break;
                case 3:
                    updateStudent();
                    break;
                case 4:
                    queryStudent();
                    break;
                case 5:
                    listAllStudents();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("无效选择，请重新输入。");
            }
        }
    }

    private static void addStudent() {
        System.out.println("\n--- 添加学生 ---");
        String id = readString("请输入学号: ");
        if (studentService.findById(id) != null) {
            System.out.println("该学号已存在！");
            return;
        }
        String name = readString("请输入姓名: ");
        studentService.addStudent(new Student(name, id));
        System.out.println("学生添加成功！");
    }

    private static void deleteStudent() {
        System.out.println("\n--- 删除学生 ---");
        String id = readString("请输入要删除的学生学号: ");
        if (studentService.deleteStudent(id)) {
            System.out.println("学生删除成功！");
        } else {
            System.out.println("未找到该学号的学生！");
        }
    }

    private static void updateStudent() {
        System.out.println("\n--- 修改学生姓名 ---");
        String id = readString("请输入要修改的学生学号: ");
        String newName = readString("请输入新的姓名: ");
        if (studentService.updateStudent(id, newName)) {
            System.out.println("姓名修改成功！");
        } else {
            System.out.println("未找到该学号的学生！");
        }
    }

    private static void queryStudent() {
        System.out.println("\n--- 查询学生 ---");
        System.out.println("1. 按学号查询");
        System.out.println("2. 按姓名查询");
        int choice = readInt("请输入选择: ");
        switch (choice) {
            case 1: {
                String id = readString("请输入学号: ");
                Student s = studentService.findById(id);
                if (s != null) {
                    System.out.println(s);
                } else {
                    System.out.println("未找到该学号的学生！");
                }
                break;
            }
            case 2: {
                String name = readString("请输入姓名: ");
                List<Student> list = studentService.findByName(name);
                if (list.isEmpty()) {
                    System.out.println("未找到该姓名的学生！");
                } else {
                    for (Student student : list) {
                        System.out.println(student);
                    }
                }
                break;
            }
            default:
                System.out.println("无效选择！");
        }
    }

    private static void listAllStudents() {
        System.out.println("\n--- 所有学生信息 ---");
        List<Student> all = studentService.listAll();
        if (all.isEmpty()) {
            System.out.println("暂无学生信息。");
            return;
        }
        for (int i = 0; i < all.size(); i++) {
            System.out.println((i + 1) + ". " + all.get(i));
        }
    }

    private static void gradeManagementMenu() {
        while (true) {
            System.out.println("\n--------------------------------------");
            System.out.println("              成绩管理");
            System.out.println("--------------------------------------");
            System.out.println("            1. 添加成绩");
            System.out.println("            2. 修改成绩");
            System.out.println("            3. 删除成绩");
            System.out.println("            4. 查看学生所有成绩");
            System.out.println("            5. 返回主菜单");
            System.out.println("--------------------------------------");

            int choice = readInt("请输入选择: ");
            switch (choice) {
                case 1:
                    addGrade();
                    break;
                case 2:
                    updateGrade();
                    break;
                case 3:
                    deleteGrade();
                    break;
                case 4:
                    listGrades();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("无效选择，请重新输入。");
            }
        }
    }

    private static void addGrade() {
        System.out.println("\n--- 添加成绩 ---");
        String id = readString("请输入学生学号: ");
        String course = readString("请输入课程名称: ");
        int score = readInt("请输入成绩: ");
        if (gradeService.addGrade(id, course, score)) {
            System.out.println("成绩添加成功！");
        } else {
            System.out.println("添加失败，请检查学号是否存在或该课程成绩是否已存在！");
        }
    }

    private static void updateGrade() {
        System.out.println("\n--- 修改成绩 ---");
        String id = readString("请输入学生学号: ");
        String course = readString("请输入课程名称: ");
        int score = readInt("请输入新的成绩: ");
        if (gradeService.updateGrade(id, course, score)) {
            System.out.println("成绩修改成功！");
        } else {
            System.out.println("修改失败，请检查学号和课程名称是否正确！");
        }
    }

    private static void deleteGrade() {
        System.out.println("\n--- 删除成绩 ---");
        String id = readString("请输入学生学号: ");
        String course = readString("请输入要删除的课程名称: ");
        if (gradeService.deleteGrade(id, course)) {
            System.out.println("成绩删除成功！");
        } else {
            System.out.println("删除失败，请检查学号和课程名称是否正确！");
        }
    }

    private static void listGrades() {
        System.out.println("\n--- 查看学生成绩 ---");
        String id = readString("请输入学生学号: ");
        List<Course> courses = gradeService.listGrades(id);
        if (courses == null) {
            System.out.println("未找到该学号的学生！");
        } else if (courses.isEmpty()) {
            System.out.println("该学生暂无成绩记录。");
        } else {
            System.out.println("学生学号: " + id);
            for (Course course : courses) {
                System.out.println(course);
            }
        }
    }

    private static void sortAndStatisticsMenu() {
        while (true) {
            System.out.println("\n--------------------------------------");
            System.out.println("           成绩排序与统计");
            System.out.println("--------------------------------------");
            System.out.println("            1. 按总成绩排序");
            System.out.println("            2. 按单科成绩排序");
            System.out.println("            3. 统计各科平均分");
            System.out.println("            4. 返回主菜单");
            System.out.println("--------------------------------------");

            int choice = readInt("请输入选择: ");
            switch (choice) {
                case 1:
                    sortByTotalScore();
                    break;
                case 2:
                    sortByCourseScore();
                    break;
                case 3:
                    showCourseAverages();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("无效选择，请重新输入。");
            }
        }
    }

    private static void sortByTotalScore() {
        System.out.println("\n--- 按总成绩排序 ---");
        System.out.println("1. 升序（从低到高）");
        System.out.println("2. 降序（从高到低）");
        int order = readInt("请选择排序方式: ");
        List<Student> sorted = sortService.sortByTotalScore(order == 1);
        if (sorted.isEmpty()) {
            System.out.println("暂无学生数据。");
            return;
        }
        System.out.println("排序结果：");
        for (int i = 0; i < sorted.size(); i++) {
            Student s = sorted.get(i);
            System.out.println((i + 1) + ". " + s.getName() + " (学号: " + s.getId()
                    + ") 总分: " + (int) s.getTotalScore());
        }
    }

    private static void sortByCourseScore() {
        System.out.println("\n--- 按单科成绩排序 ---");
        String course = readString("请输入课程名称: ");
        System.out.println("1. 升序（从低到高）");
        System.out.println("2. 降序（从高到低）");
        int order = readInt("请选择排序方式: ");
        List<Student> sorted = sortService.sortByCourseScore(course, order == 1);
        if (sorted.isEmpty()) {
            System.out.println("暂无学生数据。");
            return;
        }
        System.out.println("排序结果（课程: " + course + "）：");
        for (int i = 0; i < sorted.size(); i++) {
            Student s = sorted.get(i);
            int score = getStudentCourseScore(s, course);
            String scoreStr = (score == -1) ? "未选课" : String.valueOf(score);
            System.out.println((i + 1) + ". " + s.getName() + " (学号: " + s.getId()
                    + ") " + course + ": " + scoreStr);
        }
    }

    private static int getStudentCourseScore(Student student, String courseName) {
        List<Course> courses = student.getCourseList();
        for (Course course : courses) {
            if (course.getName().equals(courseName)) {
                return course.getScore();
            }
        }
        return -1;
    }

    private static void showCourseAverages() {
        System.out.println("\n--- 各科平均分 ---");
        Map<String, Double> averages = sortService.getCourseAverages();
        if (averages.isEmpty()) {
            System.out.println("暂无成绩数据。");
            return;
        }
        for (String course : averages.keySet()) {
            System.out.println(course + ": 平均分 " + String.format("%.1f", averages.get(course)));
        }
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("请输入有效的数字！");
            }
        }
    }

    private static String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}
