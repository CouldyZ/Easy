package com.thh.easy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.thh.easy.R;
import com.thh.easy.adapter.ActRVAdapter;
import com.thh.easy.util.TabUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 活动界面
 */
public class ActActivity extends BaseDrawerActivity implements BaseDrawerActivity.OnStartActivityListener ,ActRVAdapter.OnActItemClickListener{

    @Bind(R.id.tl_activity_tabs)
    TabLayout tlActTabs;                         // tab指示器

    @Bind(R.id.vp_activity_views)
    ViewPager vpActViewPager;                    // activity内容

    List<RecyclerView.Adapter> rvAdapterLists = new ArrayList<>();   // 适配器列表

    public static ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act);
        setOnStartActivityListener(this);
        viewPager = vpActViewPager;
        setUpData();
    }

    /**
     * 初始化各种数据
     */
    private void setUpData() {
        TabUtils tabUtils = new TabUtils(this);
        tabUtils.setViewListData(
                new int[]{R.layout.act_org_view,R.layout.act_join_view},   // view根布局
                new int[]{R.id.rv_act_org,R.id.rv_act_join},               // recyclerview布局
                new String[]{"正在组织","已经参加"});                       // tab上的title
        tabUtils.setTabPager(tlActTabs, vpActViewPager);                   // 绑定ViewPager

        // 添加adapter数据
        ActRVAdapter actOrgAdapter = new ActRVAdapter(this);
        ActRVAdapter actJoinAdapter = new ActRVAdapter(this);

        // 添加recycleview 的监听事件
        actOrgAdapter.setOnActItemClickListener(this);
        actJoinAdapter.setOnActItemClickListener(this);

        rvAdapterLists.add(actOrgAdapter);
        rvAdapterLists.add(actJoinAdapter);
        tabUtils.setViewsRVAdapter(rvAdapterLists);                                      // 绑定recyclerview
    }


    /**
     * 点击跳转到发起活动的界面
     */
    @OnClick(R.id.fab_org_act)
    void startOrgActivity() {
        // TODO 考虑是否添加动画效果
        startActivity(new Intent(this, OrgActActivity.class));
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(0, 0);
    }

    @Override
    public void onStartActivity(Class<?> targetActivity) {
        if (ActActivity.class == targetActivity)
            return;

        if (targetActivity == MainActivity.class) {
            finish();
            overridePendingTransition(0, 0);
            return;
        }

        Intent intent = new Intent(ActActivity.this, targetActivity);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
        overridePendingTransition(0, 0);
    }

    /**
     * 点击跳转到发起活动的界面
     */
    @OnClick(R.id.fab_org_act)
    void startOrgActActivity() {
        // TODO 考虑是否添加动画效果
        startActivity(new Intent(this, OrgActActivity.class));
    }


    /**
     * 点击页面item进入看看，跳转到详情界面
     * @param view
     * @param position
     */
    @Override
    public void onCheckDetail(View view, int position) {

        // TODO 带参跳转， 网络数据获取
        Intent intent = new Intent(this, ActDetailActivity.class);
        startActivity(intent);
    }


}
