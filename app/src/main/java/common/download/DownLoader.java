package common.download;

import android.app.DownloadManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;

/**
 * 描述:
 * Created by mjd on 2017/2/8.
 */

public class DownLoader {

    private Context context;
    private DownloadManager downloadManager;
    private static DownLoader downLoader;

    private DownLoader(Context context) {
        this.context = context;
        downloadManager = (DownloadManager) context.getApplicationContext().getSystemService(Context.DOWNLOAD_SERVICE);
    }

    public DownloadManager getDownloadManager() {
        return downloadManager;
    }

    public static DownLoader getInstance(Context context) {
        if (downLoader == null) {
            downLoader = new DownLoader(context);
        }
        return downLoader;
    }


    public long download(String uri, String title, String description) {

        DownloadManager.Request req = new DownloadManager.Request(Uri.parse(uri));

        req.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);

        req.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        //file:///storage/emulated/0/Android/data/your-package/files/Download/
        req.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS, "update.apk");

        // 设置一些基本显示信息
        req.setTitle(title);
        req.setDescription(description);

        return downloadManager.enqueue(req);

    }

    /**
     * 获取保存文件的地址
     */
    public Uri getDownloadUri(long downloadId) {
        return downloadManager.getUriForDownloadedFile(downloadId);
    }

    /**
     * 获取下载状态
     *
     * @param downloadId an ID for the download, unique across the system.
     *                   This ID is used to make future calls related to this download.
     * @return int
     * @see DownloadManager#STATUS_PENDING
     * @see DownloadManager#STATUS_PAUSED
     * @see DownloadManager#STATUS_RUNNING
     * @see DownloadManager#STATUS_SUCCESSFUL
     * @see DownloadManager#STATUS_FAILED
     */
    public int getDownloadStatus(long downloadId) {
        DownloadManager.Query query = new DownloadManager.Query().setFilterById(downloadId);
        Cursor cursor = downloadManager.query(query);
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    return cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_STATUS));

                }
            } finally {
                cursor.close();
            }
        }
        return -1;
    }

}
