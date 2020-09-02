## Redis
### Redis 发布与订阅
典型的广播模式，一个消息可以发布到多个消费者

"发布/订阅"模式包含两种角色，分别是发布者和订阅者。订阅者可以订阅一个或者多个频道(channel),而发布者可以向指定的频道(channel)发送消息，所有订阅此频道的订阅者都会收到此消息。

发出去的消息不会被持久化，也就是有客户端订阅channel:1后只能接收到后续发布到该频道的消息，之前的就接收不到了。

不会收到订阅之前就发布到该频道的消息

> 消息一旦发布，不能接收。换句话就是发布时若客户端不在线，则消息丢失，不能寻回

#### 一、将信息 message 发送到指定的频道 channel 。
PUBLISH channel message
返回值：接收到信息 message 的订阅者数量。

redis> publish bad_channel "can any body hear me?"
(integer) 0
 
#### 二、订阅给定的一个或多个频道的信息。
SUBSCRIBE channel [channel ...]
返回值：接收到的信息
```
# 订阅 msg 和 chat_room 两个频道

# 1 - 6 行是执行 subscribe 之后的反馈信息
# 第 7 - 9 行才是接收到的第一条信息
# 第 10 - 12 行是第二条

redis> subscribe msg chat_room
Reading messages... (press Ctrl-C to quit)
1) "subscribe"       # 返回值的类型：显示订阅成功
2) "msg"             # 订阅的频道名字
3) (integer) 1       # 目前已订阅的频道数量

1) "subscribe"
2) "chat_room"
3) (integer) 2

1) "message"         # 返回值的类型：信息
2) "msg"             # 来源(从那个频道发送过来)
3) "hello moto"      # 信息内容

1) "message"
2) "chat_room"
3) "testing...haha"
```
### LPUSH/BRPOP
- BRPOP
它是 RPOP 命令的阻塞版本，当给定列表内没有任何元素可供弹出的时候，连接将被 BRPOP 命令阻塞，直到等待超时或发现可弹出元素为止。
- LPUSH
将一个或多个值 value 插入到列表 key 的表头
