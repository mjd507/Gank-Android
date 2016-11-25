package com.android.common.utils;

import com.android.common.utils.logger.Logger;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 描述: IO流操作工具
 */
public class IOUtils {
    private static String TAG = IOUtils.class.getName();

    /**
     * 写出数据
     *
     * @param file 文件
     * @param data 文件字节数据
     */
    public static void write(File file, byte[] data) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            fos.write(data);
        } catch (IOException e) {
            Logger.e(TAG, e.getMessage());
        } finally {
            close(null, fos);
        }
    }

    /**
     * 读取文件到字节数组
     *
     * @param file 文件
     */
    public static byte[] readFileToBytes(File file) {
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
        } catch (Exception ex) {
            Logger.d(TAG, ex.getMessage());
        } finally {
            close(input, output);
        }
        return output.toByteArray();
    }

    /**
     * 关闭流
     */
    private static void close(InputStream input, OutputStream output) {
        try {
            if (input != null)
                input.close();
            if (output != null)
                output.close();
        } catch (Exception ex) {
            Logger.e(TAG, ex.getMessage());
        }
    }

    /**
     * 删除路径下的所有文件
     *
     * @param path 文件夹路径
     */
    public static void deleteAllFile(String path) {
        File folder = new File(path);
        File[] files = folder.listFiles();
        for (int i = 0; i < files.length; i++) {
            deleteFile(files[i].getAbsolutePath());
        }
    }

    /**
     * 删除文件
     *
     * @param path 文件路径
     */
    public static void deleteFile(String path) {
        File file = new File(path);
        file.delete();
    }
}
