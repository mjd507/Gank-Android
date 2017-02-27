# CommonAndroid
先来看下项目的效果：
![干货集中营](/gank.gif)

apk 文件位于项目的 app 目录下，[点击下载](/app/gank.apk)

CommonAndroid 包含该两个部分，一部分是 应用层（干货集中营），借助 [干货Api](http://gank.io/api) 的接口实现的一个小项目，另一部分是 Android 基础库。

项目的初衷还是封装 Android 开发中基础的一些类库，包括 网络请求，数据库，控件，工具类等等。
因为个人的知识，能力和精力有限，希望更多的朋友能共同完善这个项目。

## 基础类库中已经包含的部分如下：

* MVP 模块：V 与 M 完全解耦，项目架构更加清晰

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



