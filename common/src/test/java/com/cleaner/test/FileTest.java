package com.cleaner.test;

import com.android.common.utils.ConstUtils;
import com.android.common.utils.FileUtils;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

/**
 * 描述:
 * Created by mjd on 2016/12/19.
 */

public class FileTest {

    @Test
    public void testFileSize() {
        File fileDir = FileUtils.getFileByPath("/Users/mjd/Documents/Growing/Books/");
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
    public void testReadFile2Str() {
        String path = "/Users/mjd/Documents/Growing/Books/";
        File file = new File(path + "test");
        String s = FileUtils.readFile2Str(file, "UTF-8");
        assertEquals(s, "hello test");
    }


    @Test
    public void testDelete() {
        String path = "/Users/mjd/Documents/Growing/test/";
        File dir = new File(path);
        FileUtils.deleteDir(dir);
    }

    @Test
    public void testRename() {
        String path = "/Users/mjd/Documents/Growing/Books/";
        File file = new File(path + "test");
        FileUtils.rename(file, "hello");
    }


}
