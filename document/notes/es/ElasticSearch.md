### 使用指南
- 官方下载：elasticsearch
https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-5.6.11.zip
- 官方下载：kibana
https://artifacts.elastic.co/downloads/kibana/kibana-5.6.11-windows-x86.zip
- 官方下载：logstash
https://artifacts.elastic.co/downloads/logstash/logstash-5.6.11.zip


### 配置文件：conf/elasticsearch.yml


### 安装LogStash，使用`logstash-input-jdbc`实现与数据库同步
1. logstash-plugin.bat install logstash-input-jdbc
```
	PS F:\xpndev\elasticsearch56\logstash-5.6.11\logstash-5.6.11\bin> .\logstash-plugin.bat install logstash-input-jdbc
	"warning: ignoring JAVA_TOOL_OPTIONS=$JAVA_TOOL_OPTIONS"
	Validating logstash-input-jdbc
	Installing logstash-input-jdbc
	Installation successful
```

2. logstash-plugin.bat install logstash-output-elasticsearch
```
	PS F:\xpndev\elasticsearch56\logstash-5.6.11\logstash-5.6.11\bin> .\logstash-plugin.bat install logstash-output-elasticsearch
	"warning: ignoring JAVA_TOOL_OPTIONS=$JAVA_TOOL_OPTIONS"
	Validating logstash-output-elasticsearch
	Installing logstash-output-elasticsearch
	Installation successful
```

3. 配置文件（实时同步数据）

PS F:\xpndev\elasticsearch56> .\logstash-5.6.11\logstash-5.6.11\bin\logstash.bat -f .\sync_table.cfg

4. 参考文档
- https://segmentfault.com/a/1190000011784259
### 实践

1. 创建一个索引
```
        IndexResponse response = client.prepareIndex("student", "tweet", "1").setSource(XContentFactory.jsonBuilder()
                .startObject()//生成文档
                .field("id", "1")
                .field("name", "刘邦")
                .field("age", "11")
                .field("birth", new Date())
                .field("update_time", new Date())
                .endObject()).get();
```
2. 全量同步
PS F:\xpndev\elasticsearch6.4\logstash-5.6.11\logstash-5.6.11\bin> .\logstash.bat -f F:\xpndev\elasticsearch6.4\mysql.conf

### Head插件的安装与使用

https://github.com/mobz/elasticsearch-head

Running with built in server
 - git clone git://github.com/mobz/elasticsearch-head.git
 - cd elasticsearch-head
 - npm install
 - npm run start
 - open http://localhost:9100/

需要在elasticsearch的elasticsearch.yml文件里面添加
```
	# 允许CORS跨域
	http.cors.enabled: true
	http.cors.allow-origin: "*"
```
### mysql语句在线转换成ElasticSearch json查询语句
	http://www.ischoolbar.com/EsParser/







