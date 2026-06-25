```Mermaid
flowchart TD
    START([开始]) --> MENU[显示主菜单<br/>1.学生信息管理<br/>2.成绩管理<br/>3.成绩排序与统计<br/>4.退出]
    MENU --> INPUT{用户输入选择}
    INPUT -->|1| STU_MENU([进入学生信息管理])
    INPUT -->|2| GRD_MENU([进入成绩管理])
    INPUT -->|3| SRT_MENU([进入成绩排序与统计])
    INPUT -->|4| OVER([结束])
    INPUT -->|其他| ERR[提示无效选择]
    ERR --> MENU

    STU_MENU --> STU_DO[执行学生信息管理操作]
    STU_DO --> STU_BACK{返回主菜单?}
    STU_BACK -->|是| MENU
    STU_BACK -->|否| STU_DO

    GRD_MENU --> GRD_DO[执行成绩管理操作]
    GRD_DO --> GRD_BACK{返回主菜单?}
    GRD_BACK -->|是| MENU
    GRD_BACK -->|否| GRD_DO

    SRT_MENU --> SRT_DO[执行排序统计操作]
    SRT_DO --> SRT_BACK{返回主菜单?}
    SRT_BACK -->|是| MENU
    SRT_BACK -->|否| SRT_DO
```