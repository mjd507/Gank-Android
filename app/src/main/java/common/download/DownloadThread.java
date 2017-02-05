package common.download;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

import common.utils.LogUtils;

/**
 * 描述:
 * Created by mjd on 2017/2/4.
 */

public class DownloadThread extends Thread {

    private static final String TAG = "DownloadThread";

    private URL url;
    private int threadId;
    private int startPos;
    private int endPos;
    private File desFile;

    public DownloadThread(URL url, int threadId, int startPos, int endPos, File desFile) {
        this.url = url;
        this.threadId = threadId;
        this.startPos = startPos;
        this.endPos = endPos;
        this.desFile = desFile;
    }

    @Override
    public void run() {
        super.run();

        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //重要：请求服务器下载部分的文件 指定文件的位置
            conn.setRequestProperty("Range", "bytes=" + startPos + "-" + endPos);
            conn.setRequestMethod("GET");

            LogUtils.d(TAG, "thread ：" + threadId + " download ：--  " + startPos + "-->" + endPos);

            int code = conn.getResponseCode();//从服务器请求全部资源200 ok 如果从服务器请求部分资源 206 ok
            if (code == 206) {
                InputStream is = conn.getInputStream();

                RandomAccessFile raf = new RandomAccessFile(desFile, "rwd");
                raf.seek(startPos);//随机写文件的时候 从哪个位置开始写

                byte[] buffer = new byte[2048];
                int len;
                while ((len = is.read(buffer)) != -1) {
                    raf.write(buffer, 0, len);
                }
                is.close();
                raf.close();
                LogUtils.d(TAG, "Thread ：" + threadId + " download complete ！");
            } else {
                LogUtils.d(TAG, "Thread ：" + threadId + " download failure ！");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //notify you can use handler or broadcast

        }


    }
}
