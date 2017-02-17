package com.cleaner.gank;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

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
import com.cleaner.gank.theme.BaseThemeActivity;
import com.cleaner.gank.theme.ThemeChooseDialog;
import com.umeng.analytics.MobclickAgent;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import common.ui.dialog.CommonDialog;
import common.utils.AppUtils;
import common.utils.SPUtils;
import common.utils.ScreenUtils;
import common.utils.ToastUtils;

import static com.cleaner.commonandroid.R.id.navigationView;


/**
 * 描述:
 * Created by mjd on 2017/2/10.
 */

public class MainActivity extends BaseThemeActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolBar)
    Toolbar mToolbar;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(navigationView)
    NavigationView mNavigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        mNavigationView.setNavigationItemSelectedListener(this);
        mNavigationView.setItemIconTintList(null);//让图片就是显示他本身的颜色

        //切换主题，要更新的颜色
        colorful.mBuilder
                .backgroundColor(R.id.toolBar, R.attr.toolbar_bg)
                .backgroundColor(R.id.tabLayout, R.attr.toolbar_bg)
                .backgroundColor(mNavigationView.getHeaderView(0), R.attr.toolbar_bg);

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

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_theme:
                final ThemeChooseDialog themeDialog = new ThemeChooseDialog(this);
                int width = ScreenUtils.getScreenSize(this)[0];
                themeDialog.setDialogWidth(width / 4 * 3);
                themeDialog.setListener(new ThemeChooseDialog.OnThemeItemClickListener() {
                    @Override
                    public void onClick(int themeId) {
                        themeDialog.dismiss();
                        if (themeId == 1) {
                            colorful.setTheme(R.style.Theme1);
                        } else if (themeId == 2) {
                            colorful.setTheme(R.style.Theme2);
                        } else if (themeId == 3) {
                            colorful.setTheme(R.style.Theme3);
                        } else if (themeId == 4) {
                            colorful.setTheme(R.style.Theme4);
                        }
                        SPUtils.getInstence().putInt(THEME, themeId);
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                    }
                });
                themeDialog.show();
                break;
            case R.id.nav_update:

                break;
            case R.id.nav_clean:
                CommonDialog cleanDialog = new CommonDialog(this);
                cleanDialog.setCanceledOnTouchOutside(true);
                cleanDialog.setTitleText("提示");
                cleanDialog.setMsgText("确定要清理所有缓存吗？");
                cleanDialog.setPositiveBtn("确定", new CommonDialog.OnDialogClickListener() {
                    @Override
                    public void onClick(Dialog dialog, int which) {
                        boolean isSuccess = AppUtils.cleanAppData(MainActivity.this, new File("") {
                        });
                        if (isSuccess) ToastUtils.showShort(MainActivity.this, "清理完成");
                        dialog.dismiss();
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                    }
                });
                cleanDialog.setNegativeBtn("取消", new CommonDialog.OnDialogClickListener() {
                    @Override
                    public void onClick(Dialog dialog, int which) {
                        dialog.dismiss();
                    }
                });
                cleanDialog.show();
                break;
            case R.id.nav_about:
                break;
            case R.id.nav_feedback:
                break;
            default:
                break;
        }
        item.setChecked(false);
//        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);//统计时长
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }


    private long firstTime = 0;

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (System.currentTimeMillis() - firstTime > 2000) {
                ToastUtils.showShort(this, "再按一次返回键退出");
                firstTime = System.currentTimeMillis();
                return;
            } else {
                GankApplication application = (GankApplication) getApplication();
                application.destroyReceiver();
                finish();
            }
            super.onBackPressed();
        }
    }
}
