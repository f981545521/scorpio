#### Spring的事务与动态代理
https://www.cnblogs.com/qizhelongdeyang/p/12411576.html
http://www.itrensheng.com/archives/spring_transactional_uneffect

- SpringBoot2.x默认的代理模式设置成cglib代理
- 如果偏要springboot 走JDK动态代理，那么需要在application.properties里面配置
```
spring.aop.proxy-target-class=false
```

一旦使用了AOP（如：@Transactional， @Aspect），Spring就会创建其代理类。

Spring已经放弃了JDK动态代理，默认使用CGLIB。

![Proxy](../images/JDK与CGLIB.png)
> 参考：https://blog.csdn.net/u011242657/article/details/99747011