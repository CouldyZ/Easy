package com.thh.easy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.android.volley.ext.HttpCallback;
import com.android.volley.ext.RequestInfo;
import com.android.volley.ext.tools.HttpTools;
import com.thh.easy.R;
import com.thh.easy.adapter.ActRVAdapter;
import com.thh.easy.adapter.MyPagerAdapter;
import com.thh.easy.constant.StringConstant;
import com.thh.easy.entity.Activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 活动界面
 * // TODO 添加下拉刷新
 */
public class ActActivity extends BaseDrawerActivity implements BaseDrawerActivity.OnStartActivityListener ,ActRVAdapter.OnActItemClickListener{

    @Bind(R.id.tl_activity_tabs)
    TabLayout tlActTabs;                         // tab指示器

    @Bind(R.id.vp_activity_views)
    ViewPager vpActViewPager;                    // activity内容
    MyPagerAdapter myPagerAdapter;                  // viewpager 适配器

    LayoutInflater mInflater;                       // 视图加载

    String[] mTabTitles;                            // tabs上的文字
    List<View> mViewList = new ArrayList<>();       // viewpager包括的两个view对象
    View llOrgView,llJoinView;                      // viewPager中的两个view

    RecyclerView rvOrgActivity,rvJoinActivity;      // view里面对应的列表
    ActRVAdapter rvOrgActAdapter, rvJoinActAdapter; // viewpager两个view里面的recyclerview适配器

    HttpTools httpTools;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act);
        setOnStartActivityListener(this);

        // 初始化Volley
        HttpTools.init(getApplicationContext());
        httpTools = new HttpTools(getApplicationContext());

        setUpPagerData();
        setUpTabData();
        setUpRecyclerViews();

    }


    /**
     * 初始化viewpager数据
     */
    private void setUpPagerData() {
        mInflater = LayoutInflater.from(this);
        mTabTitles = new String[]{"正在组织","已经参加"}; // tab上的文字

        // 正在组织
        llOrgView = mInflater.inflate(R.layout.act_org_view, null);
        rvOrgActivity = (RecyclerView)llOrgView.findViewById(R.id.rv_act_org);
        mViewList.add(llOrgView);

        // 已经参加
        llJoinView = mInflater.inflate(R.layout.act_join_view, null);
        rvJoinActivity = (RecyclerView)llJoinView.findViewById(R.id.rv_act_join);
        mViewList.add(llJoinView);
        myPagerAdapter = new MyPagerAdapter(mViewList, mTabTitles);
    }

    /**
     * 设置Tab数据
     */
    private void setUpTabData() {

        tlActTabs.setTabMode(TabLayout.MODE_FIXED);                 //设置tab模式，当前为系统默认模式
        tlActTabs.addTab(tlActTabs.newTab().setText(mTabTitles[0]));//添加tab选项卡
        tlActTabs.addTab(tlActTabs.newTab().setText(mTabTitles[1]));

        vpActViewPager.setAdapter(myPagerAdapter);                  //给ViewPager设置适配器
        tlActTabs.setupWithViewPager(vpActViewPager);               //将TabLayout和ViewPager关联起来。
        tlActTabs.setTabsFromPagerAdapter(myPagerAdapter);          //给Tabs设置适配器
    }


    /**
     * 初始化recyleview
     *  数据需根据ActRVAdapter修改
     */
    public void setUpRecyclerViews() {

        LinearLayoutManager orgLinearLayoutManager = new LinearLayoutManager(this);
        rvOrgActivity.setLayoutManager(orgLinearLayoutManager);
        rvOrgActivity.setHasFixedSize(true);

        rvOrgActAdapter = new ActRVAdapter(this, actLists);
        rvOrgActivity.setAdapter(rvOrgActAdapter);

        LinearLayoutManager joinLinearLayoutManager = new LinearLayoutManager(this);
        rvJoinActivity.setLayoutManager(joinLinearLayoutManager);
        rvJoinActivity.setHasFixedSize(true);

        rvJoinActAdapter = new ActRVAdapter(this, joinLists);
        rvJoinActivity.setAdapter(rvJoinActAdapter);

    }

    /**
     * http://localhost:8080/thhh/act/act_findAct.action?pageIndex=1&rowCount=6&users.id=1
     */

    int currentPage = 1;                        // 当前页
    private List<Activities> actLists = new ArrayList<>(); //正在组织的活动

    private List<Activities> joinLists = new ArrayList<>(); // 已经参加的活动柜
    private void loadActivity() {
        // 向服务器发送数据
        Map<String, String> params = new HashMap<String, String>(3);
        params.put(StringConstant.CURRENT_PAGE_KEY, currentPage+"");
        params.put(StringConstant.PER_PAGE_KEY, StringConstant.PER_PAGE_COUNT + "");
        // TODO 获得用户ID
        params.put(StringConstant.COMMENT_UID,"0");
        RequestInfo info = new RequestInfo(StringConstant.SERVER_NEWPOST_URL, params);
        httpTools.post(info, new HttpCallback() {
            @Override
            public void onStart() {
              //  isLoading = true;
                Log.i("New - HttpCallback", "当前页" + currentPage);
            }

            @Override
            public void onFinish() {
                // 一共加载多少条
//                isLoading = false;
//                srContainer.setRefreshing(false);
            }

            @Override
            public void onResult(String s) {
                Log.i("New - HttpCallback", s);

                if (StringConstant.NULL_VALUE.equals(s)) {
                   // Snackbar.make(clContainer, "网络貌似粗错了", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if ("[]".equals(s)) {
                    // Snackbar.make(clContainer, "什么帖子都没有呦", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                onReadJson(s);
               // Log.d("New - HttpCallback", postRVAdapter.getItemCount() + " loadPost");

                currentPage++;
            }

            @Override
            public void onError(Exception e) {
            }

            @Override
            public void onCancelled() {
               // srContainer.setRefreshing(false);
            }

            @Override
            public void onLoading(long l, long l1) {

            }
        });
    }

    public void onReadJson(String json) {

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
