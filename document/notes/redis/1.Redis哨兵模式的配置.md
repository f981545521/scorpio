### Redis哨兵模式

### 3个虚拟机都运行redis

-- 分别登陆连接三个redis服务，通过slaveof [ip] [port]来建立几个redis服务之间的主从关系：

在从服务器上进入redis建立关系：

`127.0.0.1:6379> SLAVEOF 192.168.1.100 6379`

100主机上：
```
127.0.0.1:6379> info replication
# Replication
role:master
...
```
101主机上：
```
127.0.0.1:6379> info replication
# Replication
role:slave
...
```
### sentinel哨兵配置
cd /usr/local/redis
vi sentinel.conf
```
# 这个文件每个节点都一样

port 26379
daemonize yes
protected-mode no
logfile "26379.log"
dir "./"

sentinel monitor mymaster 192.168.1.100 6379 1
sentinel down-after-milliseconds mymaster 10000
sentinel parallel-syncs mymaster 1
sentinel failover-timeout mymaster 15000
# sentinel auth-pass mymaster 123

bind 0.0.0.0
```

运行哨兵：

方式1：`./redis-sentinel ./sentinel.conf`

方式2：`./redis-server sentinel.conf --sentinel`

## 搭建哨兵环境步骤
1. 运行3个单机Redis
2. 随意选一个作为master,建议：在其他两台redis.conf中增加：`slaveof 192.168.1.102 6379`。也可以直接使用命令：`SLAVEOF 192.168.1.100 6379`
3. 参见：sentinel哨兵配置，在三台服务器上运行sentinel服务：`./redis-sentinel ./sentinel.conf`

## 测试问题
1. 从服务器down后，master继续写入会同步吗？

A:	会同步的

2. slave写入数据会同步到master中吗？

A: slave无法写数据：(error) READONLY You can't write against a read only replica.

> master down 后会重新选举一个master.

## Master&Slave是什么？

主从复制，主机数据更新后根据配置和策略，自动同步到备机的master/slaver机制，Master以写为主，Slave以读为主。

## 补充

### Redis5.0集群cluster (https://blog.51cto.com/8370646/2309693)
1. 在redis.conf中配置：`cluster-enabled yes`

2. 创建集群：`redis-cli --cluster create 127.0.0.1:7000 127.0.0.1:7001 127.0.0.1:7002 --cluster-replicas 1  `

3. 检查集群状态：`redis-cli --cluster check 127.0.0.1:7000`  #填写任意节点即可 会带出所有的  






