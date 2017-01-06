# CommonAndroid

封装了 Android 开发中出现频率较高的工具类,以及多数应用通用的框架,方便独立开发者快速开发出属于自己的应用。


**Base UI 模块**
- Base App Theme 的设定,使所有 Activity 有统一的 进入退出 效果
- BaseActivity 的封装,包括统一 TitleBar,以及 TitleBar 上返回键的处理。
- CommonAdapter/CommonViewHolder 的封装,简化项目大量使用 BaseAdapter 时写的重复代码。
- CommonDialog/LoadingDialog 的封装,统一项目的 对话框和加载框。


**Common Utils 模块**

- logger 日志,copy 自 [orhanobut/logger](https://github.com/orhanobut/logger)
- AppUtils 获取应用版本,安装应用,清除应用所有数据
- BarUtils 获取状态栏高度
