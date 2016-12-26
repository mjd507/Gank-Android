package com.cleaner.test;

import com.android.common.utils.ConstUtils;
import com.android.common.utils.FileUtils;
import com.android.common.utils.TimeUtils;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * 描述:
 * Created by mjd on 2016/12/19.
 */

public class FileUtilsTest {

    @Test
    public void testFileSize() {
        File fileDir = FileUtils.getFileByPath("/Users/mjd/Documents/Growing/Books/");
        boolean fileExistsOrCreated = FileUtils.isFileExistsOrCreated(fileDir);
        if (!fileExistsOrCreated) return;
        String fileSize = FileUtils.getDirSize(fileDir);
        System.out.println("booksSize = " + fileSize);
    }

    @Test
    public void testMemorySize() {
        long size = ConstUtils.GB + ConstUtils.MB * 500;
        String str = FileUtils.byte2MemorySize(size);
        System.out.println("testMemorySize = " + str);
    }

    @Test
    public void testWriteFileFromString() {
        String path = "/Users/mjd/Documents/Growing/Books/";
        File file = new File(path + "test");
        String content = "hello test";
        FileUtils.writeFileFromString(file, content, true);
    }


    @Test
    public void testWriteFileFromIS() throws FileNotFoundException {
        String path = "/Users/mjd/Documents/test/a.md";
        File file = new File(path);
        FileUtils.writeFileFromIS(file, new FileInputStream(file), true);
    }

    @Test
    public void testReadFile2Str() {
        String path = "/Users/mjd/Documents/Growing/Books/";
        File file = new File(path + "test");
        String s = FileUtils.readFile2Str(file, "UTF-8");
        assertEquals(s, "hello test");
    }

    @Test
    public void testReadFile2Bytes() {
        String path = "/Users/mjd/Documents/test/a.md";
        File file = new File(path);
        byte[] bytes = FileUtils.readFile2Bytes(file);
        String s = FileUtils.byte2MemorySize(bytes.length);
        System.out.println(s);
    }

    @Test
    public void testDelete() {
        String path = "/Users/mjd/Documents/Growing/test/";
        File dir = new File(path);
        FileUtils.deleteDir(dir);

        String path2 = "/Users/mjd/Documents/Growing/test2/";
        File file = new File(path2);
        FileUtils.deleteFilesInDir(file);
    }

    @Test
    public void testRename() {
        String path = "/Users/mjd/Documents/Growing/Books/";
        File file = new File(path + "test");
        FileUtils.rename(file, "hello");
    }

    @Test
    public void getFileLastModified() {
        String path = "/Users/mjd/Documents/test/a.md";
        long fileLastModified = FileUtils.getFileLastModified(new File(path));
        System.out.println(TimeUtils.millis2String(fileLastModified));
    }

    @Test
    public void getLength() {
        String path = "/Users/mjd/Documents/test/a.md";
        long fileLength = FileUtils.getFileLength(new File(path));
        String s1= FileUtils.byte2MemorySize(fileLength);
        long dirLen = FileUtils.getDirLength(new File(path).getParentFile());
        String s2 = FileUtils.byte2MemorySize(dirLen);
        System.out.println(s1);
        System.out.println(s2);
        String fileSize = FileUtils.getFileSize(new File(path));
        assertEquals(s1,fileSize);
    }

    @Test
    public void isFileOrDir() {
        String path = "/Users/mjd/Documents/test/a.md";
        boolean file = FileUtils.isFile(new File(path));
        assertTrue(file);
        boolean dir = FileUtils.isDir(new File(path).getParentFile());
        assertTrue(dir);
    }



}
