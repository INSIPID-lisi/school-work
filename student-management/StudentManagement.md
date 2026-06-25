```Mermaid
flowchart TD
    START([开始]) --> SUB[显示学生信息管理菜单<br/>1.添加 2.删除 3.修改<br/>4.查询 5.显示所有 6.返回]
    SUB --> INPUT{选择操作}
    INPUT -->|1| ADD[添加学生]
    INPUT -->|2| DEL[删除学生]
    INPUT -->|3| UPD[修改学生姓名]
    INPUT -->|4| QRY[查询学生]
    INPUT -->|5| LIST[显示所有学生]
    INPUT -->|6| RET([返回主菜单])

    ADD --> ADD_IN[输入学号与姓名]
    ADD_IN --> ADD_CHECK{学号已存在?}
    ADD_CHECK -->|是| ADD_ERR[提示冲突]
    ADD_ERR --> SUB
    ADD_CHECK -->|否| ADD_OK[添加成功]
    ADD_OK --> SUB

    DEL --> DEL_IN[输入学号]
    DEL_IN --> DEL_CHECK{学生存在?}
    DEL_CHECK -->|否| DEL_ERR[提示未找到]
    DEL_ERR --> SUB
    DEL_CHECK -->|是| DEL_OK[删除成功]
    DEL_OK --> SUB

    UPD --> UPD_IN[输入学号]
    UPD_IN --> UPD_CHECK{学生存在?}
    UPD_CHECK -->|否| UPD_ERR[提示未找到]
    UPD_ERR --> SUB
    UPD_CHECK -->|是| UPD_NM[输入新姓名]
    UPD_NM --> UPD_OK[修改成功]
    UPD_OK --> SUB

    QRY --> QRY_MODE{查询方式}
    QRY_MODE -->|按学号| QID[输入学号查找]
    QRY_MODE -->|按姓名| QN[输入姓名查找]
    QID --> QID_FOUND{找到?}
    QN --> QN_FOUND{找到?}
    QID_FOUND -->|否| QID_ERR[提示未找到]
    QID_ERR --> SUB
    QN_FOUND -->|否| QN_ERR[提示未找到]
    QN_ERR --> SUB
    QID_FOUND -->|是| SHOW[显示学生信息]
    SHOW --> SUB
    QN_FOUND -->|是| SHOW

    LIST --> LIST_EMP{有数据?}
    LIST_EMP -->|否| LIST_ERR[提示暂无数据]
    LIST_ERR --> SUB
    LIST_EMP -->|是| LIST_ALL[显示所有学生]
    LIST_ALL --> SUB
```
