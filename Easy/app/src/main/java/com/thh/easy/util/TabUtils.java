package com.thh.easy.util;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.thh.easy.adapter.MyPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 设置Tabs对应数据
 * Created by cloud on 2015/11/1.
 */
public class TabUtils {

    String[] mTitles;                           // tab上的标题
    List<View> mViewList = new ArrayList<>();   // tab对应的view
    List<RecyclerView> mRecyclerViewList = new ArrayList<>();  // view里面对应的列表数据
    int size = 0;
    LayoutInflater mInflater;                   // 视图加载
    List<RecyclerView.Adapter> mRecyclerViewAdapters = new ArrayList<>();
    Context mContext;                           // 上下文

    MyPagerAdapter adapter;

    public TabUtils(Context context){
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    /**
     * 初始化viewpager数据
     */
    public void setViewListData(int[] rootIds, int[] recyviewIds, String[] titles) {
        mTitles = titles;
        View viewRoot;
        RecyclerView recyclerView;
        for (int i = 0;  i < rootIds.length ; i++) {
            viewRoot = mInflater.inflate(rootIds[i], null);
            recyclerView = (RecyclerView)viewRoot.findViewById(recyviewIds[i]);
            mViewList.add(viewRoot);
            mRecyclerViewList.add(recyclerView);
        }
        size = titles.length;
        adapter = new MyPagerAdapter(mViewList, titles);
    }

    /**
     * 设置Tab数据
     */
    public void setTabPager(TabLayout tlActTabs, ViewPager vpActViewPager) {
        tlActTabs.setTabMode(TabLayout.MODE_FIXED);            //设置tab模式，当前为系统默认模式
        for(String title: mTitles) {
            tlActTabs.addTab(tlActTabs.newTab().setText(title));//添加tab选项卡
        }
        vpActViewPager.setAdapter(adapter);                     //给ViewPager设置适配器
        tlActTabs.setupWithViewPager(vpActViewPager);           //将TabLayout和ViewPager关联起来。
        tlActTabs.setTabsFromPagerAdapter(adapter);             //给Tabs设置适配器
    }


    /**
     * 设置view内容中Recyclerview中的数据
     */
    public  void setViewsRVAdapter(List<RecyclerView.Adapter> mRecyclerViewAdapters) {
        for (int i = 0; i < size; i++) {
           RecyclerView.Adapter mAdapter = mRecyclerViewAdapters.get(i);
           LinearLayoutManager mLinearLayout = new LinearLayoutManager(mContext);
           mRecyclerViewList.get(i).setHasFixedSize(true);
           mRecyclerViewList.get(i).setLayoutManager(mLinearLayout);
           mRecyclerViewList.get(i).setAdapter(mAdapter);
        }
    }
}
