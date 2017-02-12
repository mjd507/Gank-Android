package com.cleaner.gank.tag.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述:
 * Created by mjd on 2017/2/12.
 */

public class PageAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragments = new ArrayList<>();
    private final List<String> mFragmentsTitles = new ArrayList<>();

    public PageAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(Fragment fragment, String fragmentTitle) {
        mFragments.add(fragment);
        mFragmentsTitles.add(fragmentTitle);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentsTitles.get(position);
    }
}
