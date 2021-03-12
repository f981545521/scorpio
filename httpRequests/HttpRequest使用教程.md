## IDEA Http Request使用

### 参考文档
- [IDEA HTTP Client（史上最全）](https://www.cnblogs.com/crazymakercircle/p/14317222.html)
- [随便请求网址](https://httpbin.org/)

### Rest 接口可执行文件的接口语法

```
### 使用三个 # 来分隔多个请求

GET/POST 请求地址（可拼接查询参数）
请求头键值对

请求体

> {% %}

# 响应处理
# 使用 >符号 打头，和 shell 很像，然后用 {% %} 括起来的脚本内容
# 在脚本中可以使用 javascript 原生语法，这就很强大了
# 脚本中有几个内置对象 client 表示当前客户端，response 表示响应结果


```

1. 不记录日志：
`// @no-log`
2. 输出日志
`client.log("XXX");`
### 存储变量

通过 `client.global.set` 存储全局变量，通过 `client.global.get` 获取变量。
可以通过 client 对象在内存中存储数据，
**可以保留到 idea 关闭之前。**

除了 get 获取值外，还可以通过变量获取值: `{{access_token}}`

### 示例：
```
### 输出到控制台
// @no-log
GET {{sui_bian}}
Accept: application/json

> {%
client.log("XXX");
client.log(response.body.origin)
%}
```


