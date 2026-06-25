```Mermaid
flowchart TD
    START([开始]) --> SUB[显示成绩管理菜单<br/>1.添加 2.修改 3.删除<br/>4.查看 5.返回]
    SUB --> INPUT{选择操作}
    INPUT -->|1| GA[添加成绩]
    INPUT -->|2| GU[修改成绩]
    INPUT -->|3| GD[删除成绩]
    INPUT -->|4| GL[查看成绩]
    INPUT -->|5| RET([返回主菜单])

    GA --> GA_SID[输入学号]
    GA_SID --> GA_STU{学生存在?}
    GA_STU -->|否| GA_STU_ERR[提示未找到]
    GA_STU_ERR --> SUB
    GA_STU -->|是| GA_COURSE[输入课程名称与成绩]
    GA_COURSE --> GA_DUP{该课程已存在?}
    GA_DUP -->|是| GA_DUP_ERR[提示已存在]
    GA_DUP_ERR --> SUB
    GA_DUP -->|否| GA_OK[添加成功]
    GA_OK --> SUB

    GU --> GU_SID[输入学号]
    GU_SID --> GU_STU{学生存在?}
    GU_STU -->|否| GU_ERR[提示未找到]
    GU_ERR --> SUB
    GU_STU -->|是| GU_CNAME[输入课程名称]
    GU_CNAME --> GU_GRADE{成绩存在?}
    GU_GRADE -->|否| GU_ERR
    GU_GRADE -->|是| GU_SCORE[输入新成绩]
    GU_SCORE --> GU_OK[修改成功]
    GU_OK --> SUB

    GD --> GD_SID[输入学号]
    GD_SID --> GD_STU{学生存在?}
    GD_STU -->|否| GD_ERR[提示未找到]
    GD_ERR --> SUB
    GD_STU -->|是| GD_CNAME[输入课程名称]
    GD_CNAME --> GD_GRADE{成绩存在?}
    GD_GRADE -->|否| GD_ERR
    GD_GRADE -->|是| GD_OK[删除成功]
    GD_OK --> SUB

    GL --> GL_SID[输入学号]
    GL_SID --> GL_STU{学生存在?}
    GL_STU -->|否| GL_ERR[提示未找到]
    GL_ERR --> SUB
    GL_STU -->|是| GL_EMP{有成绩记录?}
    GL_EMP -->|否| GL_EMPTY[提示暂无成绩]
    GL_EMPTY --> SUB
    GL_EMP -->|是| GL_SHOW[显示所有课程成绩]
    GL_SHOW --> SUB
```
