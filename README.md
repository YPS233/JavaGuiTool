# Java内存马分析辅助工具

目的是快速完成重复性的工作，内存马编写测试分析总离不开下面三部分的互相转换

java源码 <--> class字节码 <--> base64编码

其中java编译为class需要对应环境依赖，工具不考虑做，剩下的就是class字节码和base64互转，以及base64直接查看java源码。

通过base64保存class文件和直接查看java源码的功能。

### 使用方法

```bash
java -jar GuiTool-1.0.jar
```

### 待办
- [x] base64保存class文件
- [x] base64直接查看java源码
- [ ] 上传class转化为base64
- [ ] ...


![](image/example.gif)

### 参考链接
https://github.com/java-decompiler/jd-core