package universal.utils;

import android.os.Build;

/**
 * 描述: android版本检测工具
 * Created by mjd on 2016/11/22.
 */

public class VersionUtils {
    /**
     * 判断当前android版本是否是4.1以上(包含4.1本身)
     *
     * @return 如果是返回true，如果不是返回false
     * */
    public static boolean hasJellyBean() {
        return Build.VERSION.SDK_INT >= 16;
    }

    /**
     * 判断当前android版本是否是4.4以上(包含4.4本身)
     *
     * @return 如果是返回true，如果不是返回false
     * */
    public static boolean hasKitKat() {
        return Build.VERSION.SDK_INT >= 19;
    }
}
