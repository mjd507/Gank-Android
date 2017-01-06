# CommonAndroid

封装了 Android 开发中出现频率较高的工具类,以及多数应用通用的框架,方便独立开发者快速开发出属于自己的应用。

下面是项目的包含的一些模块

**Base UI 模块**
- Base App Theme 的设定,使所有 Activity 有统一的 进入退出 效果
- BaseActivity 的封装,包括统一 TitleBar,以及 TitleBar 上返回键的处理,也可以自定义标题栏按钮的图片和功能。
- BaseFragment 的封装,与 BaseActivity 几乎一致。
- CommonAdapter/CommonViewHolder 的封装,简化项目大量使用 BaseAdapter 时写的重复代码。
- CommonDialog/LoadingDialog 的封装,统一项目的 对话框和加载框。

**MVP Sample**
- 如果你的项目想使用 MVP 架构,可以参考以这个非常容易理解的 MVP 架构的业务模型案例。

**Common Utils 模块**

- logger 日志,copy 自 [orhanobut/logger](https://github.com/orhanobut/logger)
- AppUtils 获取应用版本,安装应用,清除应用所有数据
- BarUtils 获取状态栏高度
- CleanUtils
    * 清除内部缓存
    * 清除内部文件
    * 清除内部数据库
    * 清除内部 Shared_prefs
    * 清除外部缓存
    * 清除自定义目录下的文件
- ConstUtils
    * KB, MB, GB 的倍数关系
    * MSEC, SEC, MIN, HOUR, DAY 的倍数关系
    * 一些通用的正则匹配 (手机,身份证,邮箱,日期......)
- DensityUtils dp 与 px , sp 与 px 的相互转化
- DeviceUtils
    * 获取设备系统版本号
    * 获取设备AndroidID
    * 获取设备厂商
    * 获取设备型号
    * 获取设备MAC地址
- EncodeUtils
    * URL 编解码(UTF-8模式)
    * Base64 编解码
- FileUtils
    * 判断 文件/目录 是否存在,不存在判断是否创建成功;
    * 判断 文件/目录 是否删除,没删除判断是否删除成功;
    * 读取文件(to byte or to String);
    * 写入文件(from inputStream or from String);
    * 获取文件/目录 总长度 and 总大小
    * 重命名文件
- HandlerUtils
    * 封装了 静态 的 Handler,并采用 弱引用 来维护要处理的对象
- LocationUtils(室内测试不通过,慎用)
    * 判断Gps是否可用
    * 判断定位是否可用
    * 打开Gps设置界面
    * 注册定位监听
    * 解注册定位监听
    * 根据经纬度获取地理位置相关的信息
- LogUtils 简单的 log 管理类
- MD5Utils 生成 MD5 加密串
- NetworkUtils

**test 模块**
- 用来测试 以上模块代码正确性的模块。
- 没有多余的布局文件,所有 Activity 的测试布局都是代码动态生成,保证了项目的干净。