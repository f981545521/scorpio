## MyBatis使用笔记

1. 使用mapper中查询List，结果不可能为null。没有结果时，返回size为0的List。

2. 查询单个实体，不存在的时候是null，这个时候需要判断是否为null。