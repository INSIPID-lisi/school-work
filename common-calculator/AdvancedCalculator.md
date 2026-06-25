```Mermaid
flowchart TD
    START([进入高级运算]) --> MENU[显示运算菜单]
    MENU --> INPUT{用户选择}
    INPUT -->|0| RET([返回主菜单])
    INPUT -->|选择运算| PARAM[输入运算参数]
    PARAM --> VALIDATE{参数校验}
    VALIDATE -->|不通过| ERR[提示数学错误]
    ERR --> RET
    VALIDATE -->|通过| OP_CASE{运算类型}

    OP_CASE -->|1| POW[幂运算]
    OP_CASE -->|2| FAC[阶乘]
    OP_CASE -->|3| PI[圆周率]
    OP_CASE -->|4| SIN[sin]
    OP_CASE -->|5| COS[cos]
    OP_CASE -->|6| TAN[tan]
    OP_CASE -->|7| EXP[e^x]
    OP_CASE -->|8| LN[ln]

    POW --> RESULT
    FAC --> RESULT
    PI --> RESULT
    SIN --> RESULT
    COS --> RESULT
    TAN --> RESULT
    EXP --> RESULT
    LN --> RESULT

    RESULT([输出运算结果]) --> RET
```
