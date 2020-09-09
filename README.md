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

- DTO(Data Transfer Object):-统称-:数据传输对象

- Po(Parameter Object):请求参数

	接收提交的参数，增、删、改	类型操作
- So(Search Object):分页查询参数对象

	接收提交的参数，查询		类型
- Bo(Business Object): 业务对象

	内部转换使用
- Vo(View Object)：展示对象

	页面响应信息，接口返回使用

- DO(Data Object):-暂未使用-:此对象与数据库表结构一一对应，通过 DAO 层向上传输数据源对象。


```
POJO：Plain Old/Ordinary Java Object，是其他xO的统称。笔者也喜欢用bean来统称这些xO，但后来觉得不准确，因为bean一般指spring容器所管理的bean
DTO：Data Transfer Object，用来作为Controller的出参和Service的出参。一般分为两类：Controller的入参叫xxxReqDTO，Service层的出参叫xxxRespDTO。也有很多系统并不分得这么细
DO：Data Object，用来和单表一一对应的实体类。也有资料显示DO是Domain Object（域对象）
EO：Entity Object，实体对象。有些公司用来表示跟表一一对应的类，即相当于DO。
PO：早期的阿里Java文档定义为Persistence Object，持久化对象，跟表一一对应的
VO：View Object，阿里Java文档中说用来跟前端页面一一对应的对象。偶尔会有用错的情形，比如用来作为Controller层的入参
BO：Business Object，阿里Java文档说明由 Service 层输出的封装业务逻辑的对象
AO：Application Object，应用对象

## 新版阿里Java手册中：
POJO（Plain Ordinary Java Object）: 在本手册中，POJO 专指只有 setter/getter/toString 的简单类，包括 DO/DTO/BO/VO 等。
DO（Data Object）：此对象与数据库表结构一一对应，通过 DAO 层向上传输数据源对象。
DTO（Data Transfer Object）：数据传输对象，Service 或 Manager 向外传输的对象。
BO（Business Object）：业务对象，由 Service 层输出的封装业务逻辑的对象。
AO（Application Object）：应用对象，在 Web 层与 Service 层之间抽象的复用对象模型，极为贴 近展示层，复用度不高。
VO（View Object）：显示层对象，通常是 Web 向模板渲染引擎层传输的对象。
Query：数据查询对象，各层接收上层的查询请求。注意超过 2 个参数的查询封装，禁止使用 Map 类来传输。
```
