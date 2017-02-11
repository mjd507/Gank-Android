package com.cleaner.gank.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cleaner.commonandroid.R;
import com.cleaner.gank.tag.model.TagInfoBeen;
import com.cleaner.gank.tag.presenter.TagInfoPresenter;
import com.cleaner.gank.tag.view.ITagInfoView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import common.ui.BaseFragment;

/**
 * 描述:
 * Created by mjd on 2017/2/11.
 */

public class TagFragment extends BaseFragment implements ITagInfoView {
    protected TagInfoPresenter presenter;
    private View view;

    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tag, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter = new TagInfoPresenter(this);
    }

    @Override
    public void showSuccessView(List<TagInfoBeen> results) {

    }

    @Override
    public void showErrorView() {

    }

    @Override
    public void netUnConnect() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showLoading() {

    }
}
