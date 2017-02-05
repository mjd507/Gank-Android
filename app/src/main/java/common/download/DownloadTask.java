package common.download;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

import common.utils.FileUtils;
import common.utils.LogUtils;

/**
 * 描述:
 * Created by mjd on 2017/2/4.
 */

public class DownloadTask {

    private static final String TAG = "DownloadTask";
    private int runningThread = 2;

    /**
     * @param path    下载路径
     * @param desFile 目标文件
     */
    public void download(String path, File desFile) {
        try {
            boolean fileExistsOrCreated = FileUtils.isFileExistsOrCreated(desFile);
            if (!fileExistsOrCreated) {
                LogUtils.d(TAG, "file not exists");
                return;
            }
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            int code = conn.getResponseCode();
            if (code == 200) {
                int total = conn.getContentLength();

                RandomAccessFile raf = new RandomAccessFile(desFile, "rwd");
                raf.setLength(total);

                int block = total / runningThread;

                for (int i = 0; i < runningThread; i++) {
                    int startPos = i * block;
                    int endPos = (i + 1) * block - 1;
                    if (i == runningThread - 1) {
                        endPos = total;
                    }
                    DownloadThread thread = new DownloadThread(url, i, startPos, endPos, desFile);
                    thread.start();
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
