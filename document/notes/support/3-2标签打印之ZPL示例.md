## ZPL 学习示例

### 工具
1. ZPL语言结果预览 [ZPLDesigner](https://sourceforge.net/projects/zpldesigner/files/)
2. [ZPL指令在线测试网址](http://labelary.com/viewer.html)

### JS
怎么通过JS打印：[使用JS打印ZPL教程](https://www.neodynamic.com/articles/How-to-print-raw-Zebra-ZPL-commands-from-Javascript/)

1. [JSPrintManager 测试地址](https://jsprintmanager.azurewebsites.net/)
2. [JSPrintManager 源码地址](https://github.com/neodynamic/jsprintmanager)
3. [JSPrintManager Client](https://www.neodynamic.com/downloads/jspm/) JS发送指令到打印机


##### ZPL1

```shell script
^XA
^FO50,60^A0,40^FDWorld's Best Griddle^FS
^FO60,120^BY3^BCN,60,,,,A^FD1234ABC^FS
^FO25,25^GB380,200,2^FS
^XZ
```

##### ZPL2

```shell script
^XA
^FO100,100
^BY3
^B1N,N,150,Y,N
^FD123456^FS
^XZ
```
##### ZPL3

```shell script
^XA
^FO50,50
^B8N,100,Y,N
^FD1234567^FS
^XZ
```

##### PDF417
```shell script
^XA
^LH0,0
^FO60,20
^ADN,36,20
^FD 123456 ^FS
^FO60,95
^B7N,5,3,,,N
^FD 123456 ^FS
^XZ
```
##### 复杂的

```shell script
^XA

^FO20,30
^GB750,1100,4
^FS

^FO20,30
^GB750,200,4
^FS

^FO20,30
^GB750,400,4
^FS

^FO20,30
^GB750,700,4
^FS

^FO20,226
^GB325,204,4
^FS

^FO30,40
^ADN,36,20
^FDShip to:^FS

^FO30,260
^ADN,18,10
^FDPart number #^FS

^FO360,260
^ADN,18,10
^FDDescription:^FS

^FO30,750
^ADN,36,20
^FDFrom:^FS

^FO150,125
^ADN,36,20
^FDAcme Printing^FS

^FO60,330
^ADN,36,20
^FD14042^FS

^FO400,330
^ADN,36,20
^FDScrew^FS

^FO70,480
^BY4^B3N,,200
^FD12345678^FS

^FO150,800
^ADN,36,20
^FDMacks Fabricating^FS

^XZ
```

