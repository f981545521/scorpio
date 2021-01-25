## SpringBoot 中使用Thymeleaf
1. pom.xml
```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-thymeleaf</artifactId>
        </dependency>
```

2. application.properties
```

# thymeleaf 模板引擎
spring.thymeleaf.enabled=true
spring.thymeleaf.encoding=UTF-8
spring.thymeleaf.prefix: classpath:/templates/
spring.thymeleaf.suffix: .html

```
3. 放入index.html文件

4. 写Controller
```
    @GetMapping("index")
    @ApiOperation(value = "首页")
    public String index() {
        return "index";
    }
```