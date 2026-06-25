```Mermaid
flowchart TD
    subgraph View[视图层 - 控制台]
        CONSOLE[控制台输入输出<br/>Scanner / System.out]
    end

    subgraph Controller[控制层]
        CC[CalculatorController<br/>负责菜单显示、输入验证、流程控制]
    end

    subgraph Model[模型层 - 业务逻辑]
        BCM[BasicCalculatorModel<br/>加、减、乘、除、取余、倒数]
        ACM[AdvancedCalculatorModel<br/>幂、阶乘、π、sin、cos、e^x、ln]
    end

    MAIN[Main.java<br/>程序入口] -->|创建并启动| CC
    CC -->|显示菜单 / 读取输入| CONSOLE

    CC -->|调用 calculate| BCM
    CC -->|调用 各类方法| ACM

    BCM -->|返回 double| CC
    ACM -->|返回 double/long| CC

    CC -->|输出结果| CONSOLE

    style MAIN fill:#4CAF50,color:#fff
    style CONSOLE fill:#FF9800,color:#fff
    style CC fill:#2196F3,color:#fff
    style BCM fill:#9C27B0,color:#fff
    style ACM fill:#9C27B0,color:#fff
```
