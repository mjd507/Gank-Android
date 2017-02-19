# CommonAndroid
先来看下项目的效果：
![干货集中营](/gank.gif)

apk 文件位于项目的 app 目录下，[点击下载](/app/gank.apk)

CommonAndroid 包含该两个部分，一部分是 应用层（干货集中营），借助 [干货Api](http://gank.io/api) 的接口实现的一个小项目，另一部分是 Android 基础库。

项目的初衷还是封装 Android 开发中基础的一些类库，包括 网络请求，数据库，控件，工具类等等。
因为个人的知识，能力和精力有限，希望更多的朋友能共同完善这个项目。

基础类库中已经包含的部分如下：

## NetState 网络监测模块
采用 单例 + 观察者模式, 监测网络状态的变化。无需在每个 Activity 注册 广播,解放了一定的生产力。
使用方式:在 Application 创建时,初始化 NetStateReceiver
```
    /**
     * 应用全局的网络变化处理
     */
    private void initNetChangeReceiver() {

        //获取当前网络类型
        mNetType = NetworkUtils.getNetworkType(this);

        //定义网络状态的广播接受者
        netStateReceiver = NetStateReceiver.getReceiver();

        //给广播接受者注册一个观察者
        netStateReceiver.registerObserver(netChangeObserver);

        //注册网络变化广播
        NetworkUtils.registerNetStateReceiver(this, netStateReceiver);

    }

    private NetChangeObserver netChangeObserver = new NetChangeObserver() {

        @Override
        public void onConnect(NetworkUtils.NetworkType errorType) {
            //do something
        }

        @Override
        public void onDisConnect() {
            //do something
        }
    };
```

## DB 模块
可能是最简单的数据库封装,算上注解总共只有 7 个类,使用方法:

- 在 Application onCreate() 时初始化 DbManager
    ```
        //初始化 数据库
        DbManager.DbParams params = new DbManager.DbParams();
        params.dbName = "xx.db";
        params.dbVersion = 1;
        DbManager dbManager = DbManager.getInstance();
        dbManager.init(this, params);
    ```

- 创建数据库
    ```
        DbDao dao = dbManager.getDao(null); //param  --> dbUpdateListener -- see DemoDbActivity how to use
    ```

- 创建表
    ```
        TablesManager tablesManager = TablesManager.getInstance();
        tablesManager.register(Person.class);  // Person -- javabeen
        tablesManager.createTables(dao);
    ```

- 增删改查
    ```
        Person person = new Person();
        person.setName("张三");
        person.setAge(20);

        //insert
        long result = dao.insert(person);

        //delete
        Boolean delete = dao.delete(Person.class, "name=?", new String[]{"张三"});

        //update
        Person person = new Person("李四", 18);
        long update = dao.update(Person.class, person, "name = ?", new String[]{"张三"});

        //query
        ArrayList<Person> personList = dao.query(false, Person.class, null, null, null, null, null, null, null);

    ```

## Http 模块
- 对 volley 库进行了二次封装，包括请求的和响应的统一处理，json 数据的解析，使得项目数据提供层更加清晰。
- 后期会在 该模块下加入 RxAndroid 等响应式编程的封装。

## download 模块
- 封装系统的 DownloadManager，在大文件下载，比如 apk 更新等操作时，更加方便

## 图片加载模块
- 项目中使用的图片加载时 volley 自带的，为了避免三分类库的过多使用
- 后期会加入 Glide 作为图片加载的底层支持库


## Common Utils 模块

- logger 日志,copy 自 [orhanobut/logger](https://github.com/orhanobut/logger)
- AppUtils
    * 获取应用版本
    * 安装应用
    * 清除应用所有数据
- BarUtils
    * 获取状态栏高度
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
    * 一些通用的正则匹配规则 (手机,身份证,邮箱,日期......)
- DensityUtils
    * dp 与 px , sp 与 px 的相互转化
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
- LogUtils
    * 简单的 log 管理类
- MD5Utils
    * 生成 MD5 加密串
- NetworkUtils
    * 打开网络设置界面
    * 判断网络是否连接
    * 判断网络是否可用
    * 判断网络是否是4G
    * 判断wifi是否打开
    * 打开或关闭wifi
    * 判断wifi是否连接状态
    * 判断wifi数据是否可用
    * 获取网络运营商名称
    * 获取当前网络类型
    * 获取IP地址
    * 获取域名ip地址
- RegexUtils
    * 验证手机号（精确）
    * 验证电话号码
    * 验证身份证号码15位
    * 验证身份证号码18位
    * 验证邮箱
    * 验证URL
    * 验证汉字
    * 验证yyyy-MM-dd格式的日期校验，已考虑平闰年
    * ......
- ScreenUtils
    * 获取屏幕宽高
    * 获取屏幕旋转角度
    * 获取当前屏幕截图，包含状态栏
    * 获取当前屏幕截图，不包含状态栏
    * 判断是否锁屏
- SDCardUtils
    * SD 卡是否挂载
    * 获取 SD 卡路径/获取 SD 卡 data 路径
    * 获取 SD 卡剩余空间
- ShellUtils
    * 执行命令
- SPUtils
    * SharedPreferences 读写常见数据类型
- StringUtils
    * 判断字符串是否为空
- TimeUtils
    * millis => String
    * millis <==> date
    * date => String
    * 获取合适型两个时间差(返回天、小时、分钟、秒和毫秒)
    * 获取友好型与当前时间的差(刚刚/30秒前/3分钟前/今天15:32/昨天15:32/2017-01-06)
    * 判断是否同一天/判断是否闰年
    * 获取星期/获取星期 index/获取月份中的第几周/获取年份中的第几周
    * 获取生肖
    * 获取星座
- ToastUtils
    * 长吐司/短吐司
- ZipUtils
    * 压缩文件/目录
    * 解压文件
