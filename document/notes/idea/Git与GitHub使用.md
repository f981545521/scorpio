## Git 与 GitHub
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

## 修改提交的邮箱
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

## Git查看日志命令

git log --pretty=format:"%h - %an, %ar : %s"

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

