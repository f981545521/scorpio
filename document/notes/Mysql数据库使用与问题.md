### Mysql

####  only_full_group_by
[Err] 1055 - Expression #1 of SELECT list is not in GROUP BY clause and contains nonaggregated column 'xxx'
 which is not functionally dependent on columns in GROUP BY clause; this is incompatible with sql_mode=only_full_group_by
 
 1. 修改 my.ini 文件
 2. 在 [mysqld] 下面添加代码：
 ```
 sql_mode=STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION
 ```
> 1、不同的系统，mysql 的配置文件名以及路径不同

> 2、Mac或Linux文件  /etc/my.cnf

> 3、windows 在数据库安装目录下的 my.ini

3. 重启服务

#### mybatis最大执行SQL的长度
写一个10wList数据的查询：
>  Packet for query is too large (4500277 > 4194304). You can change this value on the server by setting the max_allowed_packet' variable.
>
查看大小：show VARIABLES like '%max_allowed_packet%';
默认为：4194304 = 4*1024*1024 即：4M

命令行修改：
`set global max_allowed_packet = 6*1024*1024;`

配置文件修改：
可以编辑 my.cnf（windows 下 my.ini，linux 下一般是/etc/my.cnf）来修改 ,在[mysqld]段或者 mysql 的 server 配置段增加下面配置：
```
[mysqld]
max_allowed_packet=6M
```

#### mysql-如何选择直到总和达到某个值
[如何选择直到总和达到某个值](http://www.cocoachina.com/articles/97874)

您需要一个累加的总和才能起作用.一种方法使用变量：
```
select t.*
from (select *, (@sum := @sum + number) as cume_number
      from test cross join
           (select @sum := 0) params
      order by id 
     ) t
where cume_number < 9 or (cume_number >= 9 and cume_number - number < 9);
```
#### 别的设备不能访问
开启允许外部访问
```
update user set host = '%' where user = 'root';
FLUSH PRIVILEGES;
GRANT ALL PRIVILEGES ON *.* TO 'root'@'%'WITH GRANT OPTION;
```













