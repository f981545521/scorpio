## Hexo集成GitHubPages

- [Hexo官方参考教程](https://hexo.io/zh-cn/docs/github-pages)
- [使用Hexo博客生成工具](https://hans2936.github.io/2018/06/06/HexoLog/)


### 按照教程操作
1. 新建一个 repository。如果你希望你的站点能通过 <你的 GitHub 用户名>.github.io 域名访问，你的 repository 应该直接命名为 <你的 GitHub 用户名>.github.io。

    创建`f981545521.github.io`的仓库。

2. 初始化Hexo，在本地访问`http://localhost:4000/`

> 基本操作
>
> - hexo g 生成/public 文件夹，里面是网站
>
> - hexo d 把这个网站文件夹推送到服务器
>
> - hexo clean 删除网站文件夹
>
> - hexo s 本地查看效果

3. 将 [Travis CI](https://github.com/marketplace/travis-ci) 添加到你的 GitHub 账户中。

4. 前往 GitHub 的 [Applications settings](https://github.com/settings/installations)，配置 Travis CI 权限，使其能够访问你的 repository。
你应该会被重定向到 Travis CI 的页面。如果没有，请 [手动前往](https://travis-ci.com/)。

5. 在浏览器新建一个标签页，前往 GitHub 新建 [Personal Access Token](https://github.com/settings/tokens)，只勾选 repo 的权限并生成一个新的 Token。Token 生成后请复制并保存好。

6. 回到 Travis CI，前往你的 repository 的设置页面，在 Environment Variables 下新建一个环境变量，Name 为 GH_TOKEN，Value 为刚才你在 GitHub 生成的 Token。确保 DISPLAY VALUE IN BUILD LOG 保持 不被勾选 避免你的 Token 泄漏。点击 Add 保存。

    `https://travis-ci.com/github/f981545521/f981545521.github.io/settings`
    
    上传项目
    
7. 在 GitHub 中前往你的 repository 的设置页面，修改 GitHub Pages 的部署分支为 gh-pages

8. 访问：`https://f981545521.github.io/`

### 问题与解答
##### Q1. 无法加载文件 C:\Users\10186\AppData\Roaming\npm\hexo.ps1，因为在此系统上禁止运行脚本
1. 按下win+s输入powershell。然后右键以管理员身份运行。
2. 然后输入set-ExecutionPolicy RemoteSigned命令，
3. 输入y，执行回车。

