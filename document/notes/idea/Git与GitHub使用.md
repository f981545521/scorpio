## Git 与 GitHub
1.查看用户名和邮箱地址
```
$ git config user.name
$ git config user.email
```

2.修改全局用户名和邮箱地址：
```
$ git config --global user.name  "username"     
$ git config --global user.email "email"       
```
 
3.修改局部用户名和邮箱地址:
```
$ cd ~/you project                       
$ git config user.name  "username"      
$ git config user.email "email" 
```

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




-- test commit
-- test commit2
-- test commit3