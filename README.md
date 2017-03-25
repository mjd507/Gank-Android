# Gank Android客户端
借助  [干货Api](http://gank.io/api) 的接口实现的一个小项目。

[点击查看动图效果](./gank.gif)

项目结构上包含该两个部分：
- 应用层（干货集中营的界面和业务逻辑），使用 MVP 模式，层次结构更加清晰
- 组件层（http、image、net state、database ...），为应用层提供友好调用支持

## 底层组件支持的模块：

* NetState 网络监测模块：当网络变化时，对其进行检测

* DB 模块：通过 JavaBeen 自动建表，与原生数据库一致的增删改查操作

* Http 模块：提供了 volley 和 okHttp 两种流行的网络请求库的封装，以及对请求结果 response 的统一处理。

* 图片模块：提供了图片使用的外层包装，在使用时，底层可以随意切换需要的图片加载库如 Glide 等。

* Download 模块：封装系统的 DownloadManager，在大文件下载，比如 apk 更新等操作时，更加方便

* Common Utils 模块：该模块内含有大量的实用工具类，如 日志类，时间日期类，文件类，网络状态类，屏幕工具类，常用正则类，设备类....

## 关于基础库的使用

1. common 文件夹下是库的全部类容
2. CommonApplication 里面默认初始化了使用时需要初始化的组件，使用时可以将 应用的 Application 继承 CommonApplication，或者直接 copy 到 自己的 Application 当中。
3. 一些权限在 manifest 中，不要忘记添加



