package com.cleaner.gank;

import android.app.DownloadManager;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;

import common.download.DownloadCompleteReceiver;
import common.ui.BaseActivity;

/**
 * 描述:
 * Created by mjd on 2017/2/8.
 */

public class SplashActivity extends BaseActivity {

    private DownloadCompleteReceiver completeReceiver;
    private boolean isNeedUpdate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        isNeedUpdate = isNeedUpdate();
        if (isNeedUpdate) {

            completeReceiver = new DownloadCompleteReceiver();
            completeReceiver.setListener(new DownloadCompleteReceiver.Listener() {
                @Override
                public void downloadSuccess(long id) {

                }
            });
            IntentFilter filter = new IntentFilter();
            filter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
            String permission = "";
            registerReceiver(completeReceiver, filter, permission, null);
            updateApp();
        }
    }

    private void updateApp() {

    }

    private boolean isNeedUpdate() {
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isNeedUpdate) {
            completeReceiver.removeListener();
            unregisterReceiver(completeReceiver);
        }
    }
}
