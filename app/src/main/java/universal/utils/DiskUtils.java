package universal.utils;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 描述:
 * Created by mjd on 2016/11/22.
 */
public class DiskUtils {
    /**
     * 获取SD卡的剩余空间
     *
     * @return 返回SD卡剩余空间，单位：KB
     * */
    public static long getSDFreeSize() {
        return getUsableSpace(Environment.getExternalStorageDirectory());
    }

    /**
     * 获取手机ROM的剩余空间
     *
     * @return 返回手机ROM的剩余空间，单位：byte
     * */
    public static long getROMFreeSize() {
        return getUsableSpace(Environment.getDownloadCacheDirectory());
    }

    /**
     * 判断是否存在外部存储设备
     *
     * @return 如果不存在返回false
     */
    public static boolean hasExternalStorage() {
        Boolean externalStorage = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
        return externalStorage;
    }

    /**
     * 获取目录使用的空间大小
     *
     * @param path
     *            检查的路径路径
     * @return 在字节的可用空间
     */
    public static long getUsableSpace(File path) {
        final StatFs stats = new StatFs(path.getPath());
        return (long) stats.getBlockSize() * (long) stats.getAvailableBlocks();
    }

    /**
     * 获得外部应用程序缓存目录
     *
     * @param context
     *            上下文信息
     * @return 外部缓存目录
     */
    public static File getExternalCacheDir(Context context) {
        return getExternalStoreDir(context, "cache");
    }

    /**
     * 获得外部存储根目录
     *
     * @param context
     *            上下文信息
     * @return 外部存储根目录
     */
    public static File getExternalStoreDir(Context context) {
        final String cacheDir = "/Android/data/" + context.getPackageName();
        return new File(Environment.getExternalStorageDirectory().getPath()
                + cacheDir);
    }

    /**
     * 获得外部存储根目录
     *
     * @param context
     *            上下文信息
     * @param uniqueName
     *            根目录下的子目录名，还可以是相对路径。如：download/image
     * @return 外部存储根目录下的子目录
     */
    public static File getExternalStoreDir(Context context, String uniqueName) {
        return new File(getExternalStoreDir(context).getAbsolutePath()
                + File.separator + uniqueName);
    }

    /**
     * 获得内部存储根目录
     *
     * @param context
     *            上下文信息
     * @return 内部存储根目录
     */
    public static File getInternalStoreDir(Context context) {
        return context.getCacheDir().getParentFile();
    }

    /**
     * 获得内部存储根目录
     *
     * @param context
     *            上下文信息
     * @param uniqueName
     *            根目录下的子目录名，还可以是相对路径。如：download/image
     * @return 内部存储根目录下的子目录
     */
    public static File getInternalStoreDir(Context context, String uniqueName) {
        return new File(getInternalStoreDir(context).getAbsolutePath()
                + File.separator + uniqueName);
    }

    /**
     * 检查如果外部存储器是内置的或是可移动的。
     *
     * @return 如果外部存储是可移动的(就像一个SD卡)返回为 true,否则false。
     */
    public static boolean isExternalStorageRemovable() {
        return true;
    }

    /**
     * 一个散列方法,改变一个字符串(如URL)到一个散列适合使用作为一个磁盘文件名。
     */
    public static String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    private static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    /**
     * 得到一个可用的缓存目录(如果外部可用使用外部,否则内部)。
     *
     * @param context
     *            上下文信息
     * @param uniqueName
     *            缓存目录下子目录名,还可是相对路径 如：image/big
     * @return 返回目录名字
     */
    public static File getDiskCacheDir(Context context, String uniqueName) {
        return new File(getDiskCacheDir(context).getPath() + File.separator
                + uniqueName);
    }

    /**
     * 得到一个可用的缓存目录(如果外部可用使用外部,否则内部)。
     *
     * @param context
     *            上下文信息
     * @return 返回目录名字
     */
    public static File getDiskCacheDir(Context context) {
        // 检查是否安装或存储媒体是内置的,如果是这样,试着使用
        // 外部缓存 目录
        // 否则使用内部缓存 目录
        return Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState()) || !isExternalStorageRemovable() ? getExternalCacheDir(context)
                : context.getCacheDir();
    }

    /**
     * 得到一个可用的存储目录(如果外部可用使用外部,否则内部)。
     *
     * @param context
     *            上下文信息
     * @param uniqueName
     *            存储目录下子目录名,还可是相对路径 如：image/big
     * @return 返回根目录路径
     */
    public static File getDiskStoreDir(Context context, String uniqueName) {
        return new File(getDiskStoreDir(context).getPath()+ File.separator
                + uniqueName);
    }

    /**
     * 得到一个可用的存储目录(如果外部可用使用外部,否则内部)。
     *
     * @param context
     *            上下文信息
     * @return 返回根目录路径
     */
    public static File getDiskStoreDir(Context context) {
        // 检查是否安装或存储媒体是内置的,如果是这样,试着使用
        // 外部缓存 目录
        // 否则使用内部缓存 目录
        return Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState()) || !isExternalStorageRemovable() ? getExternalStoreDir(context)
                : getInternalStoreDir(context);
    }
}
