package common.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.webkit.MimeTypeMap;

import java.io.File;
import java.util.List;

/**
 * 描述:
 * Created by mjd on 2016/12/28.
 */

public class AppUtils {

    /**
     * 安装App（支持6.0）
     */
    public static void installApp(Context context, File file) {
        if (file != null && file.exists()) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            String type;
            if (Build.VERSION.SDK_INT < 23) {
                type = "application/vnd.android.package-archive";
            } else {
                String filePath = file.getPath();
                String extension = "";
                int lastPoi = filePath.lastIndexOf('.');
                int lastSep = filePath.lastIndexOf(File.separator);
                if (lastPoi != -1 && lastSep >= lastPoi) {
                    extension = filePath.substring(lastPoi + 1);
                }
                type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
            }
            intent.setDataAndType(Uri.fromFile(file), type);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

    /**
     * 获取App版本号
     */
    public static String getAppVersionName(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            return pi == null ? null : pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取App版本码
     */
    public static int getAppVersionCode(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            return pi == null ? -1 : pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 判断App是否处于前台
     */
    public static boolean isAppForeground(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> infos = manager.getRunningAppProcesses();
        if (infos == null || infos.size() == 0) return false;
        for (ActivityManager.RunningAppProcessInfo info : infos) {
            if (info.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return info.processName.equals(context.getPackageName());
            }
        }
        return false;
    }

    /**
     * 清除App所有数据
     * @param dirs 自定义的目录 (不指定默认会清除 应用内部的 File,DB,SP,Cache,ExternalCache)
     */
    public static boolean cleanAppData(Context context, File... dirs) {
        boolean isSuccess = CleanUtils.cleanInternalCache(context);
        isSuccess &= CleanUtils.cleanInternalDbs(context);
        isSuccess &= CleanUtils.cleanInternalSP(context);
        isSuccess &= CleanUtils.cleanInternalFiles(context);
        isSuccess &= CleanUtils.cleanExternalCache(context);
        for (File dir : dirs) {
            isSuccess &= CleanUtils.cleanCustomCache(dir);
        }
        return isSuccess;
    }
}
