---
title: OAuth2.0 协议入门指南
date: 2020-09-13 11:20:40
tags: oauth2
---

## OAuth2.0 协议入门指南

[oauth官网](https://oauth.net/2/)

### 1.  OAuth 2.0 协议
OAuth 2.0 是目前比较流行的做法，它率先被Google, Yahoo, Microsoft, Facebook等使用。
之所以标注为 2.0，是因为最初有一个1.0协议，但这个1.0协议被弄得太复杂，易用性差，所以没有得到普及。
2.0是一个新的设计，协议简单清晰，但它并不兼容1.0，可以说与1.0没什么关系。

### 2. 协议中各种角色

- RO (resource owner): 资源所有者，对资源具有授权能力的人。

- RS (resource server): 资源服务器，它存储资源，并处理对资源的访问请求。如Google资源服务器，它所保管的资源就是用户Alice的照片。

- Client: 第三方应用，它获得RO的授权后便可以去访问RO的资源。如网易印像服务。（尝试去获得用户账号信息的应用，用户需要先对此操作授权。）

此外，为了支持开放授权功能以及更好地描述开放授权协议，OAuth引入了第四个参与实体：

- AS (authorization server): 授权服务器，它认证RO的身份，为RO提供授权审批流程，并最终颁发授权令牌(Access Token)。
读者请注意，为了便于协议的描述，这里只是在逻辑上把AS与RS区分开来；在物理上，AS与RS的功能可以由同一个服务器来提供服务。

> 授权服务器用来提供接口，让用户同意或者拒接访问请求。在某些情况下，授权服务器和API资源服务器会是同一个，但是在大多数情况下，二者是独立的。



### 3. 授权类型
- 授权码 (Authorization Code Grant)
- 隐式授权 (Implicit Grant)
- RO凭证授权 (Resource Owner Password Credentials Grant)
- Client凭证授权 (Client Credentials Grant)


