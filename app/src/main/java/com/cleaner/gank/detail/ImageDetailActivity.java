package com.cleaner.gank.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.cleaner.commonandroid.R;
import com.cleaner.gank.tag.TagType;
import com.cleaner.gank.tag.model.TagInfoBeen;
import com.cleaner.gank.tag.presenter.TagInfoPresenter;
import com.cleaner.gank.tag.view.ITagInfoView;
import com.cleaner.gank.theme.BaseThemeActivity;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import common.http.common.Listener;
import common.image.ImagePresenter;
import common.utils.EncodeUtils;

/**
 * 描述:
 * Created by mjd on 2017/2/14.
 */

public class ImageDetailActivity extends BaseThemeActivity implements ITagInfoView {

    public static final String URL = "url";
    public static final String DATE = "date";
    public static final String POSITION = "mPosition";
    public static final String PIC_LIST = "pic_list";

    @BindView(R.id.toolBar)
    Toolbar toolbar;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    @BindView(R.id.iv_one_pic)
    ImageView ivOnePic;

    private TagInfoPresenter presenter;
    private ImageDetailAdapter mAdapter;

    private List<TagInfoBeen> infoList;
    private int mCurrentPage;
    private int mPosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        mPosition = intent.getIntExtra(POSITION, -1);
        if (mPosition == -1) { //首页进来
            String url = intent.getStringExtra(URL) + "?imageView2/0/w/720";
            String date = intent.getStringExtra(DATE);
            ivOnePic.setVisibility(View.VISIBLE);
            mViewPager.setVisibility(View.GONE);
            setTitle(date.substring(0, 10));
            new ImagePresenter().getImage(ivOnePic, url);
            return;
        } else { //福利进来
            ivOnePic.setVisibility(View.GONE);
            mViewPager.setVisibility(View.VISIBLE);

            infoList = (List<TagInfoBeen>) intent.getSerializableExtra(PIC_LIST);
            mAdapter = new ImageDetailAdapter();
            mViewPager.setAdapter(mAdapter);
            mAdapter.addList(infoList);
            mViewPager.setCurrentItem(mPosition);
            mAdapter.notifyDataSetChanged();

            setTitle(infoList.get(mPosition).publishedAt.substring(0, 10));
            mCurrentPage = mPosition / 10 + 1;
            presenter = new TagInfoPresenter(this);

        }

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                TagInfoBeen been = infoList.get(position);
                setTitle(been.publishedAt.substring(0, 10));

                if (position == infoList.size() - 1) {
                    loadMore(++mCurrentPage);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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

    public void loadMore(int page) {
        presenter.getInfo(EncodeUtils.urlEncode(TagType.Welfare), page + "");
    }

    @Override
    public void showSuccessView(List<TagInfoBeen> results) {
        if (results == null || results.size() == 0) return;
        infoList.addAll(results);
        mAdapter.addList(results);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showErrorView(Listener.ErrorType errorType) {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(this.getClass().getSimpleName());
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(this.getClass().getSimpleName());
        MobclickAgent.onPause(this);
        if (presenter != null) presenter.cancelAll();
    }

}

