## Git 与 GitHub
### 客户端
- [Git.exe 下载地址](https://git-scm.com/downloads)
- [TortoiseGit](https://tortoisegit.org/download/)
     1. 先安装语言包
     2. 安装程序

### 使用前配置（必须）
1.查看用户名和邮箱地址
```
$ git config user.name
$ git config user.email
```

2.修改全局用户名和邮箱地址：
```
$ git config --global user.name  youfang
$ git config --global user.email youfang@acyou.cn
```
 
3.修改局部用户名和邮箱地址:
```
$ cd ~/you project                       
$ git config user.name youfang
$ git config user.email youfang@acyou.cn
```

- [Git 参考手册](https://git-scm.com/book/zh/v2)
- [GitHub 使用指南](https://docs.github.com/cn/github/getting-started-with-github)
- [更改作者信息](https://docs.github.com/cn/github/using-git/changing-author-info)

#### 没有配置的补救方法：修改提交的邮箱
1. git clone --bare https://github.com/user/repo.git
2. cd repo.git
3. vi update_github.sh
```
#!/bin/sh

git filter-branch --env-filter '

OLD_EMAIL="原先的邮箱"
CORRECT_NAME="youfang"
CORRECT_EMAIL="981545521@qq.com"

if [ "$GIT_COMMITTER_EMAIL" = "$OLD_EMAIL" ]
then
    export GIT_COMMITTER_NAME="$CORRECT_NAME"
    export GIT_COMMITTER_EMAIL="$CORRECT_EMAIL"
fi
if [ "$GIT_AUTHOR_EMAIL" = "$OLD_EMAIL" ]
then
    export GIT_AUTHOR_NAME="$CORRECT_NAME"
    export GIT_AUTHOR_EMAIL="$CORRECT_EMAIL"
fi
' --tag-name-filter cat -- --branches --tags
```
4. 运行脚本。
5. git push --force --tags origin 'refs/heads/*'


## Git的tag与branch
- tag就像是一个里程碑一个标志一个点，branch是一个新的征程一条线；
- tag是静态的，branch要向前走；
- 稳定版本备份用tag，新功能多人开发用branch（开发完成后merge到master）。

### 分支
- develop 分支：持续开发的分支，我们希望每个开发组都在这个分支上保持线性的持续小步迭代。
- release 分支：用于发布过程的分支，包括开发转测(实际上我们认为这里的测试集成测试)、测试和BugFix以及发布上线的过程，当发布成功时要打一个发布beta Tag(如5.2.1-beta)，并将代码合并到 master 分支
- master 分支：即有质量保证的、可安全运行的分支，禁止直接代码提交，避免被污染，仅用于代码合并和归集，在这个分支上的代码应该永远是可用的、稳定的。当需要拉一个特别的开发分时，应该基于 master。

### Tag 推荐写法

```
alibaba     -> v1.8.0
spring-boot -> v2.3.3.RELEASE
```

#### 版本号：
1. 主版本号：当功能模块有较大的变动，比如增加多个模块或者整体架构发生变化。此版本号由项目决定是否修改。
2. 子版本号：当功能有一定的增加或变化，比如增加了对权限控制、增加XXX功能。此版本号由项目决定是否修改。(偶数为稳定版本，奇数为开发版本)
3. 阶段版本号： 一般是 Bug 修复或是一些小的变动，要经常发布修订版，时间间隔不限，修复一个严重的bug即可发布一个修订版。此版本号由项目经理决定是否修改。
4. 阶段号： 规则如下


#### 阶段号规则：
- Alpha(α)：预览版，或du者叫内部测试版；一般不向外zhi部发布，会有很多Bug；一般只有测试人员使用。
- Beta(β)：测试版，或者叫公开测试版；这个阶段的版本会一直加入新的功能；在 Alpha版之后推出。
- RC：(Release　Candidate) 候选发布版本，稳定版本，并不一定会发布。(RC版不会再加入新的功能了，主要着重于除错。)`springboot -> v2.1.0.RC1`
- RELEASE：发布版本，稳定版本，在项目中真正可用的版本。
    > GA：General Availability  正式发布的版本，在国外都是用GA来说明release版本的。
    >
    > STABLE：稳定版、最终发行版。
- M1：(Mn)：M是milestone的缩写，是里程碑版本。`springboot -> v2.1.0.M4`
- OEM：是给计算机厂商随着计算机贩卖的，也就是随机版。只能随机器出货，不能零售。
    > 原厂设备制造商OEM（Original Equipment Manufacturer）是受托厂商按来样厂商之需求与授权，按照厂家特定的条件而生产，所有的设计图等都完全依照来样厂商的设计来进行制造加工。

#### 参考项目
- [Spring Projects GitHub地址](https://github.com/spring-projects)
- [Alibaba Projects GitHub地址](https://github.com/alibaba/)

## Git查看日志命令

命令：`git log --pretty=format:"%h - %an, %ar : %s"`

- %H	提交的完整哈希值
- %h	提交的简写哈希值
- %T	树的完整哈希值
- %t	树的简写哈希值
- %P	父提交的完整哈希值
- %p	父提交的简写哈希值
- %an	作者名字
- %ae	作者的电子邮件地址
- %ad	作者修订日期（可以用 --date=选项 来定制格式）
- %ar	作者修订日期，按多久以前的方式显示
- %cn	提交者的名字
- %ce	提交者的电子邮件地址
- %cd	提交日期
- %cr	提交日期（距今多长时间）
- %s	提交说明

