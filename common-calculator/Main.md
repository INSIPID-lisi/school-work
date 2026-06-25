```Mermaid
flowchart TD
    START([开始]) --> MENU[显示主菜单<br/>1. 六则运算<br/>2. 高级运算<br/>0. 退出]
    MENU --> INPUT{用户输入选择}
    INPUT -->|1| BASIC([进入六则运算])
    INPUT -->|2| ADVANCED([进入高级运算])
    INPUT -->|0| OVER([结束])
    INPUT -->|其他| ERR[提示无效选项]
    ERR --> MENU

    BASIC --> BASIC_DO[执行六则运算操作]
    BASIC_DO --> BASIC_BACK{返回主菜单?}
    BASIC_BACK -->|是| MENU
    BASIC_BACK -->|否| BASIC_DO

    ADVANCED --> ADV_DO[执行高级运算操作]
    ADV_DO --> ADV_BACK{返回主菜单?}
    ADV_BACK -->|是| MENU
    ADV_BACK -->|否| ADV_DO
```
