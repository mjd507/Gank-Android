package common.download;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * 描述:
 * Created by mjd on 2017/2/8.
 */

public class DownloadCompleteReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            if (listener != null) {
                listener.downloadSuccess(id);
            }
        }
    }

    private Listener listener;

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public void removeListener() {
        if (listener != null) listener = null;
    }

    public interface Listener {
        void downloadSuccess(long id);
    }


}
