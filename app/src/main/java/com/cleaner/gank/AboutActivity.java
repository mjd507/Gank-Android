package com.cleaner.gank;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.cleaner.commonandroid.R;
import com.cleaner.gank.theme.BaseThemeActivity;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 描述:
 * Created by mjd on 2017/2/19.
 */
public class AboutActivity extends BaseThemeActivity {

    @BindView(R.id.toolBar)
    Toolbar mToolBar;

    @BindView(R.id.webview_github)
    WebView mGitHubWebView;

    @BindView(R.id.webview_blog)
    WebView mBlogWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        setTitle("关于");

        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setUpWebView();

        mGitHubWebView.loadUrl("https://github.com/mjd507");

        mBlogWebView.loadUrl("https://mjd507.github.io/");
    }

    private void setUpWebView() {
        WebSettings settings = mGitHubWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        mGitHubWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request,
                                        WebResourceError error) {
                super.onReceivedError(view, request, error);
            }
        });

        WebSettings settings2 = mBlogWebView.getSettings();
        settings2.setJavaScriptEnabled(true);
        settings2.setLoadWithOverviewMode(true);
        mBlogWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request,
                                        WebResourceError error) {
                super.onReceivedError(view, request, error);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(AboutActivity.class.getSimpleName());
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (null != mGitHubWebView) {
            mGitHubWebView.onPause();
        }
        if (null != mBlogWebView) {
            mBlogWebView.onPause();
        }
        MobclickAgent.onPageEnd(AboutActivity.class.getSimpleName());
        MobclickAgent.onPause(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
