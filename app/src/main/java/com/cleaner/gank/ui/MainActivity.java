package com.cleaner.gank.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.cleaner.commonandroid.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import common.ui.BaseActivity;

/**
 * 描述:
 * Created by mjd on 2017/2/10.
 */

public class MainActivity extends BaseActivity {

    @BindView(R.id.content)
    FrameLayout content;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        AndroidTagFrag androidTagFrag = new AndroidTagFrag();
        getFragmentManager().beginTransaction().add(R.id.content, androidTagFrag).commit();
    }
}
