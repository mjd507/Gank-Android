package com.cleaner.gank;

import android.app.DownloadManager;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import common.download.DownLoader;
import common.download.DownloadCompleteReceiver;
import common.ui.BaseActivity;
import common.utils.AppUtils;
import common.utils.LogUtils;

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
        setContentView(new TextView(this));
        isNeedUpdate = isNeedUpdate();
        if (isNeedUpdate) {

            completeReceiver = new DownloadCompleteReceiver();
            completeReceiver.setListener(new DownloadCompleteReceiver.Listener() {
                @Override
                public void downloadSuccess(long id) {
                    Uri downloadUri = DownLoader.getInstance(getApplicationContext()).getDownloadUri(id);
                    LogUtils.d("downloadSuccess", "uri = " + downloadUri);
                    AppUtils.installApp(SplashActivity.this, downloadUri);
                }
            });
            IntentFilter filter = new IntentFilter();
            filter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
            registerReceiver(completeReceiver, filter);
            updateApp();
        }
    }

    private void updateApp() {
        AppUtils.updateApp(this, "http://releases.b0.upaiyun.com/hoolay.apk", "正在下载", "完成后点击安装", "hoolay.apk");
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
