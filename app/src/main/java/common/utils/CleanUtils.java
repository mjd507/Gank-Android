package common.utils;

import com.cleaner.commonandroid.utils.Utils;

import java.io.File;


/**
 * 描述: 数据清除工具类,包括:
 * 清除内部缓存
 * 清除内部文件
 * 清除内部数据库
 * 清除内部 Shared_prefs
 * 清除外部缓存
 * 清除自定义目录下的文件
 * Created by mjd on 2016/12/19.
 */

public class CleanUtils {
    /**
     * 清除内部缓存
     * <p>/data/data/(package name)/cache</p>
     */
    public static boolean cleanInternalCache() {
        return FileUtils.deleteFilesInDir(Utils.getAppContext().getCacheDir());
    }

    /**
     * 清除内部文件
     * <p>/data/data/(package name)/files</p>
     */
    public static boolean cleanInternalFiles() {
        return FileUtils.deleteFilesInDir(Utils.getAppContext().getFilesDir());
    }

    /**
     * 清除内部数据库
     * <p>/data/data/(package name)/databases</p>
     */
    public static boolean cleanInternalDbs() {
        String path = Utils.getAppContext().getFilesDir().getParent() + File.separator + "databases";
        File file = FileUtils.getFileByPath(path);
        return FileUtils.deleteFilesInDir(file);
    }

    /**
     * 根据名称清除数据库
     * <p>/data/data/(package name)/databases/dbName</p>
     */
    public static boolean cleanInternalDbByName(String dbName) {
        return Utils.getAppContext().deleteDatabase(dbName);
    }

    /**
     * 清除内部SP
     * <p>/data/data/(package name)/shared_prefs</p>
     */
    public static boolean cleanInternalSP() {
        String path = Utils.getAppContext().getFilesDir().getParent() + File.separator + "shared_prefs";
        File file = FileUtils.getFileByPath(path);
        return FileUtils.deleteFilesInDir(file);
    }

    /**
     * 清除外部缓存
     * <p>/storage/emulated/0/android/data/(package name)/cache</p>
     */
    public static boolean cleanExternalCache() {
        if (SDCardUtils.isSDCardEnable()) {
            return FileUtils.deleteFilesInDir(Utils.getAppContext().getExternalCacheDir());
        }
        return false;
    }

    /**
     * 清除自定义目录下的文件
     */
    public static boolean cleanCustomCache(File dir) {
        return FileUtils.deleteFilesInDir(dir);
    }
}
