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