## 如何解决tomcat 启动失败一闪而过

1. 进入tomcat目录下bin文件夹内。
2. 点击右键startup.bat选择编辑，在startup.bat 文件最后加入pause 并且将call “%EXECUTABLE%” start %CMD_LINE_ARGS% 改成run,最后一行添加pause,
```
call "%EXECUTABLE%" run %CMD_LINE_ARGS%

:end

pause
```