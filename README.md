### SCORPIO

### Github中.md文件图片不显示？
1. 打开路径C:\Windows\System32\drivers\etc下的hosts文件
2. 在文件中追加：
```
    # GitHub Start 
    192.30.253.112    github.com 
    192.30.253.119    gist.github.com
    151.101.184.133    assets-cdn.github.com
    151.101.184.133    raw.githubusercontent.com
    151.101.184.133    gist.githubusercontent.com
    151.101.184.133    cloud.githubusercontent.com
    151.101.184.133    camo.githubusercontent.com
    151.101.184.133    avatars0.githubusercontent.com
    151.101.184.133    avatars1.githubusercontent.com
    151.101.184.133    avatars2.githubusercontent.com
    151.101.184.133    avatars3.githubusercontent.com
    151.101.184.133    avatars4.githubusercontent.com
    151.101.184.133    avatars5.githubusercontent.com
    151.101.184.133    avatars6.githubusercontent.com
    151.101.184.133    avatars7.githubusercontent.com
    151.101.184.133    avatars8.githubusercontent.com
     
    # GitHub End
```
### 约定
- POJO（Plain Ordinary Java Object）:

    在本规约中，POJO 专指只有 setter/getter/toString 的简单类，包括 DO/DTO/BO/VO 等。

- DTO(Data Transfer Object) 数据传输对象
- PO(persistant object) 持久对象

    通常对应数据模型 ( 数据库 )
    
    