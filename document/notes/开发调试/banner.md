## SpringBoot Banner

banner.txt文件，放到application.properties同级即可。
```
`${AnsiColor.BRIGHT_RED}` ：设置控制台中输出内容的颜色
`${application.version}` ：用来获取`MANIFEST.MF` 文件中的版本号
`${application.formatted-version}` ：格式化后的`${application.version}` 版本信息
`${spring-boot.version}` ：Spring Boot的版本号
`${spring-boot.formatted-version}` ：格式化后的`${spring-boot.version}` 版本信息
```

- [在线生成工具](https://www.bootschool.net/ascii)
- [图片生成字符画](https://www.fontke.com/tool/image2ascii/)

### 也可以放置一个banner.gif
### 也可以放置一个banner.png
### 只要是banner文件名即可。