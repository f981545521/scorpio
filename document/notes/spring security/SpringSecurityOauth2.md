---
title: SpringSecurity Oauth授权
date: 2020-09-12 00:05:10
tags: 
---
## oauth授权
关于oauth2，其实是一个规范，本文重点讲解spring对他进行的实现，如果你还不清楚授权服务器，资源服务器，认证授权等基础概念，
可以参考[理解OAuth 2.0](http://www.ruanyifeng.com/blog/2014/05/oauth_2_0.html)

### 应用场景
1. A厂家有一套HTTP接口需要提供给B厂家使用，由于是外网环境，所以需要有一套安全机制保障，这个时候oauth2就可以作为一个方案。
2. 微信/QQ 第三方登录
3. 微信服务端API

oauth2根据使用场景不同，分成了4种模式

- 授权码模式（authorization code）
- 简化模式（implicit）
- 密码模式（resource owner password credentials）
- 客户端模式（client credentials）

### 官方开发文档

- [OAuth 2 Developers Guide](https://projects.spring.io/spring-security-oauth/docs/oauth2.html)

- [spring-security-oauth官方示例](https://github.com/spring-projects/spring-security-oauth/tree/master/samples)

- [Spring Security OAuth 2.4 迁移示例](https://github.com/jgrandja/spring-security-oauth-2-4-migrate)

    这是Spring Security OAuth 2.4示例，该示例应用于将Spring Security OAuth 2.x应用程序迁移到Spring Security 5.2。
    
- [OAuth2 Boot](https://docs.spring.io/spring-security-oauth2-boot/docs/current/reference/html5/#boot-features-security-oauth2-single-sign-on)
