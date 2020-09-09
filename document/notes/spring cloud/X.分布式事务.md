## 分布式事务
### atomikos
基于XA协议的2PC方案。有开源版本和付费版本，有付费的就不考虑了。
- [Atomikos 官网](https://www.atomikos.com/Main/WebHome)
### TX-LCN
基于2PC方案的柔性事务框架。

- [TX-LCN 源码地址](https://github.com/codingapi/tx-lcn)
- [TX-LCN 中文参考手册](https://www.codingapi.com/docs/txlcn-preface/)

有空可以研究一下。

### TCC-Transaction

Try-Confirm-Cancel 编程，需要对应的confirm、cancel方法的逻辑，太麻烦就不考虑了。

### RocketMQ
最终一致性的分布式事务

好像是用来确保本地事务和MQ消息的原子性问题的？？？
1. rocketMQ保证本地事务成功时，消息一定会发送成功并被成功消费，如果本地事务失败了，消息就不会被发送。
2. 如何判断本地事务是否执行成功？这也是一个问题！！！


- Half Message(**半消息**)
暂不能被Consumer消费的消息。需要 Producer对消息的二次确认后，Consumer才能去消费它。
- 消息回查
Producer 端一直没有对 Half Message(半消息) 进行 二次确认。这是Brock服务器会定时扫描长期处于半消息的消息，会主动询问 Producer端 该消息的最终状态(Commit或者Rollback)，该消息即为消息回查。

### Seata事务框架

阿里开源

### 再研究
- [微服务架构的分布式事务解决方案](https://wenku.baidu.com/video/courseview/41ea27fff705cc175527093e?fr=baidu&fromplaylist=1)



