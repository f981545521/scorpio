---
title: 标题
date: 2020-09-20 19:24:59
tags: 
---
## MySQL的规范


### Count问题
>
> 阿里巴巴开发规范的**SQL 语句**章节中
> 1. 【强制】不要使用 count(列名)或 count(常量)来替代 count(*)，count(*)是 SQL92 定义的标
> 准统计行数的语法，跟数据库无关，跟 NULL 和非 NULL 无关。
> 说明：count(*)会统计值为 NULL 的行，而 count(列名)不会统计此列为 NULL 值的行。
> 2. 【强制】count(distinct col) 计算该列除 NULL 之外的不重复行数，注意 count(distinct col1,
> col2) 如果其中一列全为 NULL，那么即使另一列有不同的值，也返回为 0。
>
>

参照[实战说明](1.测试MySQL的Like查询效率.md)后得到结论：以后还是用count(*)


### Between 包含问题

Mysql和Oracle的between and都是闭区间，包含两端的值

