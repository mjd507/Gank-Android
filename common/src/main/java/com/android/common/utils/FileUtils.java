package com.android.common.utils;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * 描述: 文件操作相关的工具类
 * <p>
 * 功能包括:
 * 判断 文件/目录 是否存在,不存在判断是否创建成功;
 * 判断 文件/目录 是否删除,没删除判断是否删除成功;
 * 读取文件(to byte or to String);
 * 写入文件(from inputStream or from String);
 * 获取文件/目录 总长度 and 总大小
 * 重命名文件
 * <p>
 * <p>
 * 这里目前只提供了参数为 File 的操作文件的方法,如果是 String 类型的 参数,参照如下方法,获取到对应的文件/目录即可
 * File file = new File(filePath)
 * or
 * File file = FileUtils.getFileByPath(filePath)
 * <p>
 * Created by mjd on 2016/12/19.
 */

public class FileUtils {

    private static final String TAG = FileUtils.class.getSimpleName();


    //------------ Create File --------------------

    /**
     * 判断文件是否存在，不存在则判断是否创建成功
     */
    public static boolean isFileExistsOrCreated(File file) {
        if (file == null) return false;
        // 如果存在，是文件则返回true，是目录则返回false
        if (file.exists()) return file.isFile();
        //文件不存在,判断目录是否存在
        if (!isDirExistsOrCreated(file.getParentFile())) return false;
        try {
            return file.createNewFile();
        } catch (IOException e) {
            LogUtils.e(TAG, e.getMessage());
            return false;
        }
    }

    /**
     * 判断目录是否存在，不存在则判断是否创建成功
     */
    private static boolean isDirExistsOrCreated(File fileDir) {
        if (fileDir != null) {
            if (fileDir.exists()) {
                return fileDir.isDirectory();
            } else {
                return fileDir.mkdirs();
            }
        }
        return false;
    }


    //------------ Delete File --------------------

    /**
     * 文件是否成功删除
     */
    private static boolean isFileDeleted(File file) {
        if (file != null && file.exists()) {
            if (file.isFile()) {
                return file.delete();
            }
        }
        return true;
    }

    /**
     * 删除目录下的所有文件
     */
    public static boolean deleteFilesInDir(File dir) {
        if (dir == null) return false;
        // 目录不存在返回true
        if (!dir.exists()) return true;
        // 不是目录返回false
        if (!dir.isDirectory()) return false;
        // 文件存在且是目录
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (file.isFile()) {
                    if (!isFileDeleted(file)) return false;
                } else if (file.isDirectory()) {
                    if (!deleteDir(file)) return false;
                }
            }
        }
        return true;
    }

    /**
     * 删除目录
     */
    public static boolean deleteDir(File dir) {
        if (dir == null) return false;
        // 目录不存在返回true
        if (!dir.exists()) return true;
        // 不是目录返回false
        if (!dir.isDirectory()) return false;
        // 文件存在且是目录
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (file.isFile()) {
                    if (!isFileDeleted(file)) return false;
                } else if (file.isDirectory()) {
                    if (!deleteDir(file)) return false;
                }
            }
        }
        return dir.delete();
    }

    //------------ Read File --------------------

    public static byte[] readFile2Bytes(File file) {
        if (file == null) return null;
        ByteArrayOutputStream output = null;
        InputStream input = null;
        try {
            output = new ByteArrayOutputStream();
            input = new FileInputStream(file);

            byte[] buffer = new byte[1024 * 4];
            int n;
            while (-1 != (n = input.read(buffer))) {
                output.write(buffer, 0, n);
            }
            return output.toByteArray();
        } catch (Exception ex) {
            LogUtils.e(TAG, ex.getMessage());
            return null;
        } finally {
            CloseUtils.closeIO(input, output);
        }
    }

    public static String readFile2Str(File file, String charsetName) {
        if (file == null) return null;
        BufferedReader reader = null;
        try {
            StringBuilder sb = new StringBuilder();
            if (StringUtils.isEmpty(charsetName)) {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            } else {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charsetName));
            }
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            // 要去除最后的换行符
            return sb.delete(sb.length(), sb.length()).toString();
        } catch (IOException e) {
            LogUtils.e(TAG, e.getMessage());
            return null;
        } finally {
            CloseUtils.closeIO(reader);
        }
    }


    //------------ Write File --------------------

    /**
     * 将输入流写入文件
     */
    public static boolean writeFileFromIS(File file, InputStream is, boolean append) {
        if (file == null || is == null) return false;
        if (!isFileExistsOrCreated(file)) return false;
        OutputStream os = null;
        try {
            os = new BufferedOutputStream(new FileOutputStream(file, append));
            byte data[] = new byte[1024];
            int len;
            while ((len = is.read(data, 0, data.length)) != -1) {
                os.write(data, 0, len);
            }
            return true;
        } catch (IOException e) {
            LogUtils.e(TAG, e.getMessage());
            return false;
        } finally {
            CloseUtils.closeIO(is, os);
        }
    }


    /**
     * 将字符串写入文件
     */
    public static boolean writeFileFromString(File file, String content, boolean append) {
        if (file == null || content == null) return false;
        if (!isFileExistsOrCreated(file)) return false;
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(file, append));
            bw.write(content);
            return true;
        } catch (IOException e) {
            LogUtils.e(TAG, e.getMessage());
            return false;
        } finally {
            CloseUtils.closeIO(bw);
        }
    }


    //------------ useful method --------------------

    /**
     * 获取文件上次编辑时间
     */
    public static long getFileLastModified(File file) {
        if (file == null) return -1;
        return file.lastModified();
    }

    /**
     * 获取文件长度
     */
    public static long getFileLength(File file) {
        if (!isFile(file)) return -1;
        return file.length();
    }

    /**
     * 获取目录所有文件的总长度
     * 如果不是目录,返回文件长度
     */
    public static long getDirLength(File dir) {
        if (!isDir(dir)) {
            return isFile(dir) ? dir.length() : -1;
        }
        long len = 0;
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (file.isDirectory()) {
                    len += getDirLength(file);
                } else {
                    len += file.length();
                }
            }
        }
        return len;
    }

    /**
     * 获取文件大小
     */
    public static String getFileSize(File file) {
        long len = getFileLength(file);
        return len == -1 ? "" : byte2MemorySize(len);
    }

    /**
     * 获取目录所有文件的总大小
     */
    public static String getDirSize(File dir) {
        long len = getDirLength(dir);
        return len == -1 ? "" : byte2MemorySize(len);
    }

    /**
     * 重命名文件
     */
    public static boolean rename(File file, String newName) {
        // 文件为空返回false
        if (file == null) return false;
        // 文件不存在返回false
        if (!file.exists()) return false;
        // 新的文件名为空返回false
        if (StringUtils.isEmpty(newName)) return false;
        // 如果文件名没有改变返回true
        if (newName.equals(file.getName())) return true;
        File newFile = new File(file.getParent() + File.separator + newName);
        // 如果重命名的文件已存在返回false
        return !newFile.exists() && file.renameTo(newFile);
    }

    //------------ helper method --------------------

    /**
     * 根据文件路径获取文件
     */
    public static File getFileByPath(String filePath) {
        return StringUtils.isEmpty(filePath) ? null : new File(filePath);
    }

    public static boolean isDir(File file) {
        return file != null && file.exists() && file.isDirectory();
    }

    public static boolean isFile(File file) {
        return file != null && file.exists() && file.isFile();
    }

    public static String byte2MemorySize(long byteNum) {
        if (byteNum < 0) {
            return "shouldn't be less than zero!";
        } else if (byteNum < ConstUtils.KB) {  // < 1 KB  -> 保留整数
            return String.format("%dB", byteNum);
        } else if (byteNum < ConstUtils.MB) {   // < 1 MB  -> 保留 1 位小数
            return String.format("%.1fKB", byteNum * 1.0 / ConstUtils.KB);
        } else if (byteNum < ConstUtils.GB) {   // < 1 GB  -> 保留 1 位小数
            return String.format("%.1fMB", byteNum * 1.0 / ConstUtils.MB);
        } else {    //  > 1GB -> 保留 2 位小数
            return String.format("%.2fGB", byteNum * 1.00 / ConstUtils.GB);
        }
    }

}
