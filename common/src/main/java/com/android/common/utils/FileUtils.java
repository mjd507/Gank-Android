package com.android.common.utils;

import android.text.TextUtils;

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
            if (TextUtils.isEmpty(charsetName)) {
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

    public static long getFileLastModified(File file) {
        if (file == null) return -1;
        return file.lastModified();
    }



    //------------ helper method --------------------

    /**
     * 根据文件路径获取文件
     */
    public static File getFileByPath(String filePath) {
        return TextUtils.isEmpty(filePath) ? null : new File(filePath);
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
