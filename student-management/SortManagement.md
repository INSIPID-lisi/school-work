```Mermaid
flowchart TD
    START([开始]) --> SUB[显示成绩排序与统计菜单<br/>1.按总成绩排序<br/>2.按单科成绩排序<br/>3.各科平均分统计<br/>4.返回]
    SUB --> INPUT{选择操作}
    INPUT -->|1| S1[按总成绩排序]
    INPUT -->|2| S2[按单科成绩排序]
    INPUT -->|3| S3[各科平均分统计]
    INPUT -->|4| RET([返回主菜单])

    S1 --> S1_EMP{有学生数据?}
    S1_EMP -->|否| S1_ERR[提示无数据]
    S1_ERR --> SUB
    S1_EMP -->|是| S1_ORDER{选择升降序}
    S1_ORDER -->|升序| S1_ASC[升序排列]
    S1_ORDER -->|降序| S1_DESC[降序排列]
    S1_ASC --> S1_CALC[计算每人总成绩<br/>按总成绩排序]
    S1_DESC --> S1_CALC
    S1_CALC --> S1_SHOW[显示排名结果]
    S1_SHOW --> SUB

    S2 --> S2_EMP{有学生数据?}
    S2_EMP -->|否| S2_ERR[提示无数据]
    S2_ERR --> SUB
    S2_EMP -->|是| S2_CNAME[输入课程名称]
    S2_CNAME --> S2_ORDER{选择升降序}
    S2_ORDER -->|升序| S2_ASC[升序排列]
    S2_ORDER -->|降序| S2_DESC[降序排列]
    S2_ASC --> S2_SORT[按该科成绩排序<br/>未选课标记为-1]
    S2_DESC --> S2_SORT
    S2_SORT --> S2_SHOW[显示排名结果]
    S2_SHOW --> SUB

    S3 --> S3_EMP{有成绩数据?}
    S3_EMP -->|否| S3_ERR[提示无数据]
    S3_ERR --> SUB
    S3_EMP -->|是| S3_CALC[遍历所有成绩<br/>计算各科平均分]
    S3_CALC --> S3_SHOW[显示各科平均分]
    S3_SHOW --> SUB
```
