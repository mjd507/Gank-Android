package com.cleaner.gank;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.cleaner.commonandroid.R;
import com.cleaner.gank.daily.view.DailyInfoFrag;
import com.cleaner.gank.tag.TagType;
import com.cleaner.gank.tag.view.adapter.PageAdapter;
import com.cleaner.gank.tag.view.frag.AndroidTagFrag;
import com.cleaner.gank.tag.view.frag.IOSTagFrag;
import com.cleaner.gank.tag.view.frag.OtherTagFrag;
import com.cleaner.gank.tag.view.frag.VideoTagFrag;
import com.cleaner.gank.tag.view.frag.WebTagFrag;
import com.cleaner.gank.tag.view.frag.WelfareTagFrag;

import butterknife.BindView;
import butterknife.ButterKnife;
import common.ui.BaseActivity;

/**
 * 描述:
 * Created by mjd on 2017/2/10.
 */

public class MainActivity extends BaseActivity {

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

        DailyInfoFrag dailyInfoFrag = new DailyInfoFrag();
        AndroidTagFrag androidTagFrag = new AndroidTagFrag();
        IOSTagFrag iosTagFrag = new IOSTagFrag();
        WebTagFrag webTagFrag = new WebTagFrag();
        WelfareTagFrag welfareTagFrag = new WelfareTagFrag();
        VideoTagFrag videoTagFrag = new VideoTagFrag();
        OtherTagFrag otherTagFrag = new OtherTagFrag();

        PageAdapter mViewPageAdapter = new PageAdapter(getSupportFragmentManager());
        mViewPageAdapter.addFragment(dailyInfoFrag, TagType.Daily);
        mViewPageAdapter.addFragment(welfareTagFrag, TagType.Welfare);
        mViewPageAdapter.addFragment(androidTagFrag, TagType.Android);
        mViewPageAdapter.addFragment(iosTagFrag, TagType.iOS);
        mViewPageAdapter.addFragment(webTagFrag, TagType.Web);
        mViewPageAdapter.addFragment(videoTagFrag, TagType.RestVideo);
        mViewPageAdapter.addFragment(otherTagFrag, TagType.Other);

        mViewPager.setAdapter(mViewPageAdapter);

        mTabLayout.setupWithViewPager(mViewPager);
    }
}
