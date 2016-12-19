package com.android.common.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

/**
 * 描述: 文件操作相关的工具类
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


    //------------ Delete File --------------------

    /**
     * 删除文件
     */
    public static void deleteFile(String path) {
        File file = new File(path);
        file.delete();
    }

    /**
     *
     */
    public static void deleteAllFile(String path) {
        File folder = new File(path);
        File[] files = folder.listFiles();
        for (int i = 0; i < files.length; i++) {
            deleteFile(files[i].getAbsolutePath());
        }
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
            close(input, output);
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
            e.printStackTrace();
            return null;
        } finally {
            close(reader, null);
        }
    }


    //------------ Write File --------------------

    /**
     * 写出数据
     */
    public static void write(File file, byte[] data) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            fos.write(data);
        } catch (IOException e) {
            LogUtils.e(TAG, e.getMessage());
        } finally {
            close(null, fos);
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
     * 获取目录所有文件的长度
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
        return len == -1 ? "" : byte2FitMemorySize(len);
    }

    /**
     * 获取目录大小
     */
    public static String getDirSize(File dir) {
        long len = getDirLength(dir);
        return len == -1 ? "" : byte2FitMemorySize(len);
    }


    //------------ helper method --------------------

    /**
     * 根据文件路径获取文件
     */
    public static File getFileByPath(String filePath) {
        return StringUtils.isEmpty(filePath) ? null : new File(filePath);
    }

    public static boolean isDir(File file) {
        if (file != null && file.exists()) {
            return file.isDirectory();
        }
        return false;
    }

    public static boolean isFile(File file) {
        if (file != null && file.exists()) {
            return file.isFile();
        }
        return false;
    }

    public static String byte2FitMemorySize(long byteNum) {
        if (byteNum < 0) {
            return "shouldn't be less than zero!";
        } else if (byteNum < ConstUtils.KB) {
            return String.format("%.1fB", byteNum + 0.05);
        } else if (byteNum < ConstUtils.MB) {
            return String.format("%.1fKB", byteNum / ConstUtils.KB + 0.05);
        } else if (byteNum < ConstUtils.GB) {
            return String.format("%.1fMB", byteNum / ConstUtils.MB+ 0.0);
        } else {
            return String.format("%.1fGB", byteNum / ConstUtils.GB + 0.05);
        }
    }

    /**
     * 关闭 IO 流
     */
    private static void close(InputStream input, OutputStream output) {
        try {
            if (input != null)
                input.close();
            if (output != null)
                output.close();
        } catch (Exception ex) {
            LogUtils.d(TAG, ex.getMessage());
        }
    }

    private static void close(Reader reader, Writer writer) {
        try {
            if (reader != null)
                reader.close();
            if (writer != null)
                writer.close();
        } catch (Exception ex) {
            LogUtils.d(TAG, ex.getMessage());
        }
    }

}
