## MySQL慢查询
### 一、简介
开启慢查询日志，可以让MySQL记录下查询超过指定时间的语句，通过定位分析性能的瓶颈，才能更好的优化数据库系统的性能。

### 二、参数说明
slow_query_log 慢查询开启状态
slow_query_log_file 慢查询日志存放的位置（这个目录需要MySQL的运行帐号的可写权限，一般设置为MySQL的数据存放目录）
long_query_time 查询超过多少秒才记录

### 三、配置
查看配置：
```
show variables like 'slow_query%';
show variables like 'long_query_time';
```
方法一：
```
mysql> set global slow_query_log='ON'; 
mysql> set global slow_query_log_file='/usr/local/mysql/data/slow.log';
mysql> set global long_query_time=5;//查询超过5秒就记录
```
方法二：
```
[mysqld]
slow_query_log = ON
slow_query_log_file = /usr/local/mysql/data/slow.log
long_query_time = 5
```

### 四、测试
执行：
mysql> select sleep(6)                           
查看：`C:\ProgramData\MySQL\MySQL Server 8.0\Data`目录下生成的日志文件。


### 五、mysqldumpslow工具
如果要手工分析日志，查找、分析SQL，显然是个体力活。MySQL提供了日志分析工具mysqldumpslow。

- 得到返回记录集最多的10个SQL。

    `mysqldumpslow -s r -t 10 /database/mysql/mysql06_slow.log`
    
- 得到访问次数最多的10个SQL。

    `mysqldumpslow -s c -t 10 /database/mysql/mysql06_slow.log`
    
- 得到按照时间排序的前10条里面含有左连接的查询语句。

    `mysqldumpslow -s t -t 10 -g “left join” /database/mysql/mysql06_slow.log`

> - 建议在使用这些命令时结合 | 和more 使用 ，否则有可能出现刷屏的情况。
>
> `mysqldumpslow -s r -t 20 /mysqldata/mysql/mysql06-slow.log | more`
  



