package common.download;

import java.io.File;

/**
 * 描述:
 * Created by mjd on 2017/2/4.
 */

public class Test {

    public static void main(String[] agrs){
        String google = "http://down11.zol.com.cn/suyan/ggpy2.1.0.apk";
        DownloadTask task = new DownloadTask();
        File desFile = new File("/Users/mjd/Downloads/ggpy.apk");
        task.download(google, desFile);
    }
}
