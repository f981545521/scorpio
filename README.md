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

    
- POJO 是 DO/DTO/BO/VO 的统称，禁止命名成 xxxPOJO。
- DTO(Data Transfer Object):数据传输对象

- Po(Parameter Object):请求参数

	接收提交的参数，增、删、改	类型操作
- So(Search Object):分页查询参数对象

	接收提交的参数，查询		类型
- Bo(Business Object): 业务对象

	内部转换使用
- Vo(View Object)：展示对象

	页面响应信息，接口返回使用

- DO(Data Object):此对象与数据库表结构一一对应，通过 DAO 层向上传输数据源对象。

- PO（Persistent Object）：持久化对象
