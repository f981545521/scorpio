## Shiro的集成
Shiro是Apache下的一个开源项目。shiro属于轻量级框架，相对于SpringSecurity简单的多，也没有SpringSecurity那么复杂。
```
    <dependency>
        <groupId>org.apache.shiro</groupId>
        <artifactId>shiro-spring</artifactId>
        <version>1.4.0</version>
    </dependency>
```

##### shiro主要有三大功能模块：
1. Subject：主体，一般指用户。
2. SecurityManager：安全管理器，管理所有Subject，可以配合内部安全组件。
3. Realms：用于进行权限信息的验证，一般需要自己实现。

#### 关键点
1. 自定义Realm，在里面写登录方法，设置权限与角色。
2. CacheManager，shiro提供了基于内存的缓存，使用的是ConcurrentHashMap。也可以使用redis、j2cache第三方缓存。
3. 使用注解：判断拥有权限：`@RequiresPermissions("user:view")`，判断拥有角色：`@RequiresRoles("admin")`

> 参考：https://blog.csdn.net/bicheng4769/article/details/86668209
