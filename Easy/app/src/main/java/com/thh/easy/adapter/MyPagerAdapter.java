package com.thh.easy.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cloud on 2015/10/31.
 */
public class MyPagerAdapter extends PagerAdapter {

    private List<View> mViewsList = new ArrayList<>();         // 对应view集合
    private String[] mTitleList; // 标题

    public MyPagerAdapter(List<View> viewsList, String[] mTitleList) {
        super();
        this.mViewsList = viewsList;
        this.mTitleList = mTitleList;
    }

    @Override
    public int getCount() {
        return mViewsList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleList[position];
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViewsList.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mViewsList.get(position));
        return mViewsList.get(position);
    }
}
