# 智能程序设计课程实验项目

本项目为**智能程序设计**课程实验，包含两个独立的小型控制台 Java 应用，
均基于纯JDK 21实现， 按照课程要求，没有用到外部依赖。

---

## 项目一：通用计算器（common-calculator）

一个控制台版的简洁的通用计算器。

### 基础运算
加、减、乘、除、取余、倒数，由 `BasicCalculatorModel` 实现。

### 高级运算
所有函数由 `AdvancedCalculatorModel`手动通过泰勒级数展开等方式实现，

---

## 项目二：学生成绩管理系统（student-management）

一个控制台版的学生成绩档案管理系统。

### 功能模块

**1. 学生信息管理**（`StudentService`）
- 添加学生（学号唯一性校验）
- 删除学生（按学号）
- 修改学生姓名
- 查询学生（按学号 / 按姓名）
- 显示所有学生

**2. 成绩管理**（`GradeService`）
- 添加成绩（校验学生存在性 + 课程不重复）
- 修改成绩、删除成绩
- 查看某学生全部成绩

**3. 排序与统计**（`SortService`）
- 按总成绩排序（升序/降序，冒泡排序）
- 按单科成绩排序（未选课标记为 —）
- 统计各科平均分（HashMap 分组汇总）

---

## 构建与运行

```bash
# 通用计算器
cd common-calculator
mvn compile
java -cp target/classes Main

# 学生管理
cd student-management
mvn compile
java -cp target/classes app.App
```

---

## 项目结构

```
程序设计/
├── common-calculator/          # 计算器子项目
│   ├── src/main/java/
│   │   ├── Main.java           # 入口
│   │   ├── controller/         # 控制器层
│   │   └── model/              # 模型层（Basic/Advanced）
│   ├── pom.xml
│   └── *.md                    # 流程图文档
├── student-management/         # 学生管理子项目
│   ├── src/main/java/
│   │   ├── app/                # 入口 + 菜单
│   │   ├── model/              # Student / Course
│   │   └── service/            # StudentService / GradeService / SortService
│   ├── pom.xml
│   └── *.md                    # 流程图文档
└── 计算与智能程序设计报告V1.doc   # 实验报告
```
