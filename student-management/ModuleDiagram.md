```Mermaid
flowchart TB
    subgraph app层
        APP[App<br/>主入口/菜单控制]
    end

    subgraph service层
        SS[StudentService<br/>学生增删改查]
        GS[GradeService<br/>成绩增删改查]
        SRS[SortService<br/>排序与统计]
    end

    subgraph model层
        STU[Student<br/>学号 姓名 课程列表]
        COU[Course<br/>课程名 成绩]
    end

    APP -->|实例化| SS
    APP -->|实例化 注入SS| GS
    APP -->|实例化 注入SS| SRS

    SS -->|管理| STU
    GS -->|委托SS查学生| SS
    GS -->|添加/修改/删除| COU
    SRS -->|委托SS查学生| SS
    SRS -->|读取成绩| COU

    STU -->|包含| COU
```
