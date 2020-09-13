---
title: SpringSecurity Oauth授权实践
date: 2020-09-12 00:05:10
tags: 
---
## oauth授权
- [spring-security-oauth（GitHub 项目源码）](https://github.com/spring-projects/spring-security-oauth)
- [参考文档](http://javajgs.com/archives/1479)
- [oauth2-demo](https://github.com/lexburner/oauth2-demo)
- [springcloud-oauth](https://gitee.com/copoile/springcloud-oauth2)
- [系列文章 - 从零开始的Spring Security Oauth2](http://blog.didispace.com/spring-security-oauth2-xjf-1/)
- [spring-security-oauth2-example 数据库版](https://github.com/wanghongfei/spring-security-oauth2-example)
- [Auth-service Redis版](https://github.com/keets2012/Auth-service)
- [microservice-integration 网关整合版](https://gitee.com/keets/microservice-integration)


### 1. 授权码模式使用
1. 访问授权链接，获取授权码

    `http://localhost:9401/oauth/authorize?response_type=code&client_id=admin&redirect_uri=http://www.baidu.com&scope=all&state=normal`
2. 获取access_token
    `http://localhost:9401/oauth/token?grant_type=authorization_code&code=Q5XV3N&client_id=admin&scope=all&redirect_uri=http://www.baidu.com`

    ![](../../images/springSecurity%20oauth2_1.png)

3. 通过access_token访问资源

    ![](../../images/springSecurity%20oauth2_2.png)

### 2. 客户端模式授权
1. 访问链接直接获取access_token

    `http://localhost:9401/oauth/token?grant_type=client_credentials&client_id=admin&client_secret=admin123456`
    
    ![](../../images/springSecurity_oauth2_client.png)

2. 通过access_token访问资源同上


### 3. 用户名密码授权模式
1. 访问链接直接获取access_token
    `http://localhost:9401/oauth/token?grant_type=password&username=mark&password=123456`

2. 通过access_token访问资源同上



