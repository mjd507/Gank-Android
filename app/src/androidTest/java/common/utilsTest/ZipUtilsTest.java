package common.utilsTest;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.IOException;

import common.utils.FileUtils;
import common.utils.LogUtils;
import common.utils.SDCardUtils;
import common.utils.ZipUtils;

/**
 * 描述:
 * Created by mjd on 2016/12/30.
 */
@RunWith(AndroidJUnit4.class)
public class ZipUtilsTest {


    @Test
    public void zipFilesDir() {
        File resFile = new File(SDCardUtils.getDataPath() + "test.txt");
        if (!FileUtils.isFileExistsOrCreated(resFile)) return;
        File zipFile = new File(SDCardUtils.getDataPath() + "testZip.zip");
        boolean successs = false;
        try {
            successs = ZipUtils.zipFile(resFile, zipFile, "");
        } catch (IOException e) {
            e.printStackTrace();
        }
        LogUtils.d("ZipUtilsTest", successs ? "压缩完成" : "压缩失败");
    }

    @Test
    public void unZipFilesDir() {
        File zipFile = new File(SDCardUtils.getDataPath() + "testZip.zip");
        File destDir = new File(SDCardUtils.getDataPath());
        boolean successs = false;
        try {
            successs = ZipUtils.unzipFile(zipFile, destDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        LogUtils.d("ZipUtilsTest", successs ? "解压完成" : "解压失败");
    }


}
