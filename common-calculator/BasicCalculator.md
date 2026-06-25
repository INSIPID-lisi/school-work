```Mermaid
flowchart TD
    START([进入六则运算]) --> MENU[显示运算菜单]
    MENU --> INPUT{用户选择}
    INPUT -->|0| RET([返回主菜单])
    INPUT -->|选择运算| PARAM[输入运算数]
    PARAM --> VALIDATE{参数校验}
    VALIDATE -->|不通过| ERR[提示数学错误]
    ERR --> RET
    VALIDATE -->|通过| CALC[调用 BasicCalculatorModel]
    CALC --> RESULT([输出运算结果])
    RESULT --> RET
```
