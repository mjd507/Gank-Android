package com.cleaner.gank.detail;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.cleaner.commonandroid.R;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import butterknife.ButterKnife;
import common.ui.BaseActivity;

/**
 * 描述:
 * Created by mjd on 2017/2/14.
 */

public class InfoDetailActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    public static final String EXTRA_URL = "URL";
    public static final String EXTRA_TITLE = "TITLE";

    @BindView(R.id.toolBar)
    Toolbar mToolbar;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.webview)
    WebView mVebView;

    private String url;
    private String title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_detail);

        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        //显示返回箭头
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setUpWebView();

        if (null != getIntent()) {
            url = getIntent().getStringExtra(EXTRA_URL);
            title = getIntent().getStringExtra(EXTRA_TITLE);
        }
        setTitle(title);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorAccent);
        mVebView.loadUrl(url);
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(InfoDetailActivity.class.getSimpleName());
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (null != mVebView) {
            mVebView.onPause();
        }
        if(mSwipeRefreshLayout.isRefreshing()){
            mSwipeRefreshLayout.setRefreshing(false);
        }
        MobclickAgent.onPageEnd(InfoDetailActivity.class.getSimpleName());
        MobclickAgent.onPause(this);
    }

    private void setUpWebView() {
        WebSettings settings = mVebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        mVebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mSwipeRefreshLayout.setRefreshing(true);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request,
                                        WebResourceError error) {
                super.onReceivedError(view, request, error);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        mVebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress >= 80) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mVebView.canGoBack()) {
            mVebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_webview, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (mVebView.canGoBack()) {
                    mVebView.goBack();
                    return true;
                } else {
                    finish();
                }
                break;
            case R.id.action_share:
                sharePage();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void sharePage() {
        String title = mVebView.getTitle();
        String url = mVebView.getUrl();
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_page, title, url));
        intent.setType("text/plain");
        startActivity(Intent.createChooser(intent, "分享"));
    }

    @Override
    public void onRefresh() {
        mVebView.reload();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mVebView) {
            // After Android 5.1, there has a problem in Webview:
            // if onDetach is called after destroy, AwComponentCallbacks object will be leaked.
            if (null != mVebView.getParent()) {
                ((ViewGroup) mVebView.getParent()).removeView(mVebView);
            }
            mVebView.destroy();
        }
    }
}
