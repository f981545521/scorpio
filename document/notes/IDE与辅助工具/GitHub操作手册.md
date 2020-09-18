## GitHub的一些操作手册

### 上传Package到GitHub中
1. 登录GitHub -> 个人中心 -> Settings -> Developer settings -> Personal access tokens 
2. Generate new token 生成token 12345
3. 修改Maven配置文件：`~/.m2/settings.xml`
```xml
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0
                      http://maven.apache.org/xsd/settings-1.0.0.xsd">

  <servers>
	<server>
      <id>github</id>
      <username>981545521@qq.com</username>
      <password>123456(之前生成的token)</password>
    </server>
  </servers>

  <profiles>
    <profile>
      <id>github</id>
      <repositories>
        <repository>
          <id>central</id>
          <url>https://repo1.maven.org/maven2</url>
          <releases><enabled>true</enabled></releases>
          <snapshots><enabled>true</enabled></snapshots>
        </repository>
        <repository>
          <id>github</id>
          <name>GitHub OWNER Apache Maven Packages</name>
          <url>https://maven.pkg.github.com/OWNER/REPOSITORY</url>
        </repository>
      </repositories>
    </profile>
  </profiles>

  <activeProfiles>
    <activeProfile>github</activeProfile>
  </activeProfiles>
</settings>
```
4. 项目中增加配置：`pom.xml`
```xml
    <distributionManagement>
        <repository>
            <id>github</id>
            <name>GitHub f981545521</name>
            <url>https://maven.pkg.github.com/f981545521/scorpio</url>
        </repository>
    </distributionManagement>
```
5. 执行：`mvn deploy`

- [配置 Apache Maven 用于 GitHub 包](https://docs.github.com/cn/packages/using-github-packages-with-your-projects-ecosystem/configuring-apache-maven-for-use-with-github-packages)


