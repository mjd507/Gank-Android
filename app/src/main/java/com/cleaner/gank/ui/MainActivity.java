package com.cleaner.gank.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.cleaner.commonandroid.R;
import com.cleaner.gank.tag.TagType;
import com.cleaner.gank.tag.view.adapter.PageAdapter;
import com.cleaner.gank.tag.view.frag.AndroidTagFrag;
import com.cleaner.gank.tag.view.frag.IOSTagFrag;
import com.cleaner.gank.tag.view.frag.WebTagFrag;

import butterknife.BindView;
import butterknife.ButterKnife;
import common.ui.BaseActivity;

/**
 * 描述:
 * Created by mjd on 2017/2/10.
 */

public class MainActivity extends BaseActivity {

    private String[] tags = {TagType.Android, TagType.iOS, TagType.Web, TagType.Other, TagType.RestVideo, TagType.Welfare};
    @BindView(R.id.toolBar)
    Toolbar mToolbar;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        AndroidTagFrag androidTagFrag = new AndroidTagFrag();
        IOSTagFrag iosTagFrag = new IOSTagFrag();
        WebTagFrag webTagFrag = new WebTagFrag();

        PageAdapter mViewPageAdapter = new PageAdapter(getSupportFragmentManager());
        mViewPageAdapter.addFragment(androidTagFrag, TagType.Android);
        mViewPageAdapter.addFragment(iosTagFrag, TagType.iOS);
        mViewPageAdapter.addFragment(webTagFrag, TagType.Web);
        mViewPager.setAdapter(mViewPageAdapter);
        mTabLayout.addTab(mTabLayout.newTab().setText(TagType.Android));
        mTabLayout.addTab(mTabLayout.newTab().setText(TagType.iOS));
        mTabLayout.addTab(mTabLayout.newTab().setText(TagType.Web));
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
