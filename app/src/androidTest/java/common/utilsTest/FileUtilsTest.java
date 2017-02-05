package common.utilsTest;


import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import common.utils.ConstUtils;
import common.utils.FileUtils;
import common.utils.LogUtils;
import common.utils.SDCardUtils;
import common.utils.TimeUtils;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * 描述:
 * Created by mjd on 2016/12/19.
 */

public class FileUtilsTest {

    @Test
    public void testMemorySize() {
        long size = ConstUtils.GB + ConstUtils.MB * 500;
        String str = FileUtils.byte2MemorySize(size);
        LogUtils.d("FileUtilsTest", "testMemorySize = " + str);
    }

    @Test
    public void testWriteFileFromString() {
        String path = SDCardUtils.getDataPath();
        File file = new File(path + "test");
        String content = "hello test";
        FileUtils.writeFileFromString(file, content, true);
    }

    @Test
    public void testReadFile2Str() {
        String path = SDCardUtils.getDataPath();
        File file = new File(path + "test");
        String s = FileUtils.readFile2Str(file, "UTF-8");
        assertEquals(s, "hello test");
    }


    @Test
    public void testFileSize() {
        File fileDir = FileUtils.getFileByPath(SDCardUtils.getDataPath() + "test");
        boolean fileExistsOrCreated = FileUtils.isFileExistsOrCreated(fileDir);
        if (!fileExistsOrCreated) return;
        String fileSize = FileUtils.getDirSize(fileDir);
        LogUtils.d("FileUtilsTest", "Size = " + fileSize);
    }

    @Test
    public void testWriteFileFromIS() throws FileNotFoundException {
        String path = SDCardUtils.getDataPath();
        File file = new File(path + "test");
        FileUtils.writeFileFromIS(file, new FileInputStream(file), true);
    }

    @Test
    public void testReadFile2Bytes() {
        String path = SDCardUtils.getDataPath();
        File file = new File(path + "test");
        byte[] bytes = FileUtils.readFile2Bytes(file);
        String s = FileUtils.byte2MemorySize(bytes.length);
        LogUtils.d("FileUtilsTest", s);
    }

    @Test
    public void testRename() {
        String path = SDCardUtils.getDataPath();
        File file = new File(path + "test");
        FileUtils.rename(file, "hello");
    }

    @Test
    public void getFileLastModified() {
        String path = SDCardUtils.getDataPath();
        long fileLastModified = FileUtils.getFileLastModified(new File(path));
        LogUtils.d("FileUtilsTest", TimeUtils.millis2String(fileLastModified));
    }

    @Test
    public void getLength() {
        String path = SDCardUtils.getDataPath();
        long fileLength = FileUtils.getFileLength(new File(path));
        String s1 = FileUtils.byte2MemorySize(fileLength);
        long dirLen = FileUtils.getDirLength(new File(path).getParentFile());
        String s2 = FileUtils.byte2MemorySize(dirLen);
        LogUtils.d("FileUtilsTest", "文件大小:" + s1);
        LogUtils.d("FileUtilsTest", "父目录大小:" + s2);
        String fileSize = FileUtils.getFileSize(new File(path));
        assertEquals(s1, fileSize);
    }

    @Test
    public void isFileOrDir() {
        String path = SDCardUtils.getDataPath();
        boolean file = FileUtils.isFile(new File(path));
        assertTrue(!file);
        boolean dir = FileUtils.isDir(new File(path).getParentFile());
        assertTrue(dir);
    }

    @Test
    public void testDelete() {
        String path = SDCardUtils.getDataPath();

        File file = new File(path);
        FileUtils.deleteFilesInDir(file);

        File dir = new File(path);
        FileUtils.deleteDir(dir);

    }


}
