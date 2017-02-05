package common.downloadTest;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;

import common.download.DownloadTask;
import common.utils.SDCardUtils;

/**
 * 描述:
 * Created by mjd on 2017/2/4.
 */
@RunWith(AndroidJUnit4.class)
public class DownLoadTest {

    private Context context;

    @Before
    public void getContext() {
        context = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void test() {
//        String qQString = "http://dldir1.qq.com/qqfile/qq/QQ2013/QQ2013Beta2.exe";
//        String APK = "http://down.myapp.com/android/45592/881859/qq2013_4.0.2.1550_android.apk";
//        String phaseString = "http://dictionary.b0.upaiyun.com/phrase.zip";
//        String big = "http://212.187.212.73/bt/58179a08a7873dc2c624c38abfc3f3bebef91d79/data/2012-12-16-wheezy-raspbian.zip";
//        String musicString = "http://mac.eltima.com/download/elmediaplayer.dmg";
//        String small = "http://res.yyets.com/ftp/2013/0419/YYeTs_2199a2019776bdc256fc3b127b2b93b3.zip";
        String google = "http://down11.zol.com.cn/suyan/ggpy2.1.0.apk";
        DownloadTask task = new DownloadTask();
        File desFile = new File(SDCardUtils.getDataPath() + context.getPackageName() + "/APK/" + "ggpy1.apk");
        task.download(google, desFile);
    }

}
