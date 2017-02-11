package com.cleaner.gank.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.cleaner.commonandroid.R;
import com.cleaner.gank.tag.model.TagInfoBeen;
import com.cleaner.gank.tag.presenter.TagInfoPresenter;
import com.cleaner.gank.tag.view.ITagInfoView;

import java.util.List;

import common.http.volley.VolleyFactory;
import common.ui.BaseActivity;
import common.ui.BaseFragment;
import common.utils.ToastUtils;

/**
 * 描述:
 * Created by mjd on 2017/2/11.
 */

public class TagFragment extends BaseFragment implements ITagInfoView {
    protected TagInfoPresenter presenter;
    private View view;

    //@BindView(R.id.recyclerView)
    protected RecyclerView mRecyclerView;

    private BaseActivity mActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (BaseActivity) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tag,null);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
       // ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter = new TagInfoPresenter(this);
    }

    @Override
    public void showSuccessView(List<TagInfoBeen> results) {
        ToastUtils.showShort(mActivity,"加载成功");
    }

    @Override
    public void showErrorView() {
        mActivity.showBaseErrorView();
    }

    @Override
    public void netUnConnect() {
        mActivity.showNetError();
    }

    @Override
    public void hideLoading() {
        mActivity.closeBaseLoading();
    }

    @Override
    public void showLoading() {
        mActivity.showBaseLoading();
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

        private List<TagInfoBeen> infos;

        public MyAdapter(List<TagInfoBeen> results) {
            infos = results;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tag, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            ImageLoader imageLoader = VolleyFactory.getInstance().getImageLoader();
            holder.tvDesc.setText(infos.get(position).desc);
            List<String> images = infos.get(position).images;
            if(images != null&& images.size()>0){
                imageLoader.get(images.get(0), new ImageLoader.ImageListener() {
                    @Override
                    public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                        holder.ivImage.setImageBitmap(response.getBitmap());
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return infos.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder{

            private final TextView tvDesc;
            private final ImageView ivImage;

            public MyViewHolder(View view) {
                super(view);
                tvDesc = (TextView) view.findViewById(R.id.tv_desc);
                ivImage = (ImageView) view.findViewById(R.id.iv_iamge);
            }
        }
    }

}
