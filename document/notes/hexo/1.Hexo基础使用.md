# hexo_blog

### 安装NodeJS + Hexo
从官网下下载最新的[nodejs](http://nodejs.cn/)。

#### NodeJS
1. `wget https://cdn.npm.taobao.org/dist/node/v14.4.0/node-v14.4.0-linux-x64.tar.xz`
2. `xz -d node-v14.4.0-linux-x64.tar.xz`
3. `tar xvf node-v14.4.0-linux-x64.tar`
4. `mv ./node-v14.4.0-linux-x64 /usr/local/nodejs`
5. 配置可执行文件
- 方式一：修改环境变量

`vi /etc/profile`
追加
`export PATH=$PATH:/usr/local/nodejs/bin`
`source /etc/profile`
- 方式二：软链接方式（推荐）
```
ln -s /usr/local/nodejs/bin/npm /usr/local/bin/
ln -s /usr/local/nodejs/bin/node /usr/local/bin/
```
6. 测试：
```
node -v
npm -v
```
 
#### Hexo使用指南
1. 主题
https://github.com/Haojen/hexo-theme-Claudia
2. 安装hexo
```
	[root@acyou ~]# npm install -g hexo-cli
	[root@acyou ~]# npm install hexo-generator-search --save //搜索插件
```
3. 部署
```
	[root@acyou hexo_blog]# git clone https://github.com/f981545521/hexo_blog.git
	[root@acyou hexo_blog]# cd hexo_blog/
	[root@acyou hexo_blog]# git pull
	[root@acyou hexo_blog]# npm install
	
	[root@acyou hexo_blog]# hexo generate
	[root@acyou hexo_blog]# hexo server
	-- 在后台运行
	[root@acyou hexo_blog]# nohup hexo server &
    //运行：nohup hexo server -p 80 &
```

 