package com.cleaner.commonandroid;

import com.android.common.utils.FileUtils;

import org.junit.Test;

import java.io.File;

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
}
