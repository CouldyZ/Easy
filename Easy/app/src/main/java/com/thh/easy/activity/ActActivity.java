package com.thh.easy.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.android.volley.ext.HttpCallback;
import com.android.volley.ext.RequestInfo;
import com.android.volley.ext.tools.HttpTools;
import com.squareup.picasso.Picasso;
import com.thh.easy.R;
import com.thh.easy.adapter.ActRVAdapter;
import com.thh.easy.adapter.MyPagerAdapter;
import com.thh.easy.constant.StringConstant;
import com.thh.easy.entity.Activities;
import com.thh.easy.entity.User;
import com.thh.easy.util.FileUtil;
import com.thh.easy.util.LogUtil;
import com.thh.easy.util.RoundedTransformation;
import com.thh.easy.util.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 活动界面
 *
 */
public class ActActivity extends BaseDrawerActivity implements BaseDrawerActivity.OnStartActivityListener ,ActRVAdapter.OnActItemClickListener {

    @Bind(R.id.tl_activity_tabs)
    TabLayout tlActTabs;                             // tab指示器

    @Bind(R.id.vp_activity_views)
    ViewPager vpActViewPager;                        // activity内容
    MyPagerAdapter myPagerAdapter;                   // viewpager 适配器

    LayoutInflater mInflater;                        // 视图加载

    String[] mTabTitles;                             // tabs上的文字
    List<View> mViewList = new ArrayList<>();        // viewpager包括的两个view对象
    View llOrgView, llJoinView;                      // viewPager中的两个view

    RecyclerView rvOrgActivity, rvJoinActivity;      // view里面对应的列表
    ActRVAdapter rvOrgActAdapter, rvJoinActAdapter;  // viewpager两个view里面的recyclerview适配器

    HttpTools httpTools;

    int userId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act);
        setOnStartActivityListener(this);

        // 初始化Volley
        HttpTools.init(getApplicationContext());
        httpTools = new HttpTools(getApplicationContext());
        userId = Utils.getUserId(ActActivity.this);

        if(userId == 0){
            Toast.makeText(ActActivity.this, "请先登录", Toast.LENGTH_LONG).show();
        }
        setUserSp();

        loadOrgActivity();
        loadJoinActivity();

        setUpPagerData();
        setUpTabData();
        setUpRecyclerViews();


    }


    int currentPage = 1;                                    // 当前页
    private List<Activities> actLists = new ArrayList<>();  //正在组织的活动
    private List<Activities> joinLists = new ArrayList<>(); // 已经参加的活动柜


    /**
     * 应用加载正在组织活动信息
     */
    private void loadOrgActivity() {
        if (rvOrgActAdapter == null) {
            rvOrgActAdapter = new ActRVAdapter(ActActivity.this, actLists);
        }

           // 向服务器发送数据 pageIndex=1&rowCount=6&users.id=1
            Map<String, String> params = new HashMap<String, String>(3);
            params.put(StringConstant.CURRENT_PAGE_KEY, currentPage + "");
            params.put(StringConstant.PER_PAGE_KEY, StringConstant.PER_PAGE_COUNT + "");
            params.put(StringConstant.COMMENT_UID, "" + userId);

            RequestInfo info = new RequestInfo(StringConstant.SERVER_ORGING_ACT_URL, params);
            httpTools.post(info, new HttpCallback() {
                @Override
                public void onStart() {
                }

                @Override
                public void onFinish() {
                }

                @Override
                public void onResult(String s) {

                    LogUtil.i(ActActivity.this, "服务器返回数据啦：　ｓ：" + s);

                    if (StringConstant.NULL_VALUE.equals(s)) {
                        Toast.makeText(ActActivity.this, "网络貌似粗错了", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if ("[]".equals(s)) {
                        Toast.makeText(ActActivity.this, "并没有什么正在组织的活动", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    onReadOrgJson(s);

                    currentPage++;
                }

                @Override
                public void onError(Exception e) {
                }

                @Override
                public void onCancelled() {
                }

                @Override
                public void onLoading(long l, long l1) {

                }
            });
    }

    public void onReadOrgJson(String json) {

        int insertPos = rvOrgActAdapter.getItemCount();
        try {

                Activities activities = null;
                JSONArray jsonArray = new JSONArray(json);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String userImage = "";

                    if(jsonObject.getString("user_img") != null) {
                        userImage = jsonObject.getString("user_img");
                    }

                    LogUtil.d(ActActivity.this, "查看所有的活动： 发起人的头像url : " + userImage);

                    activities = new Activities(
                            jsonObject.getString("user.id"),
                            jsonObject.getString("theme"),
                            jsonObject.getString("user.name"),
                            userImage,
                            jsonObject.getInt("user.rp"),
                            jsonObject.getString("start_date"),
                            jsonObject.getString("end_date"));
                    activities.setId(""+jsonObject.getInt("act.id"));
                    actLists.add(activities);
                }

            if (rvOrgActAdapter == null) {
                rvOrgActAdapter = new ActRVAdapter(ActActivity.this, actLists);
            }

            LogUtil.d(ActActivity.this, "orgList.size:" + actLists.size());
            rvOrgActAdapter.notifyItemRangeInserted(insertPos, actLists.size() - insertPos);
            rvOrgActAdapter.notifyItemRangeChanged(insertPos, actLists.size() - insertPos);

            currentPage++;

        } catch (JSONException e) {
            LogUtil.e(ActActivity.this, " 解析onReadORGJson出错" + e.getMessage());
        }
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
    public void onCheckDetail(View view, int position, int flag) {

        String actId= "";
        if(flag == 0){
            actId = actLists.get(position).getId();
        } else {
            actId = joinLists.get(position).getId();
        }

        Intent intent = new Intent(this, ActDetailActivity.class);
        intent.putExtra(StringConstant.ACT_ID, actId);

        startActivity(intent);
    }


    /**
     * 初始化viewpager数据
     */
    private void setUpPagerData() {
        LogUtil.i(ActActivity.this, "into setUppagerData");
        mInflater = LayoutInflater.from(this);
        mTabTitles = new String[]{"正在组织","已经参加"};                     // tab上的文字

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
        LogUtil.i(ActActivity.this, "into setUpTabData");
        tlActTabs.setTabMode(TabLayout.MODE_FIXED);                 //设置tab模式，当前为系统默认模式
        tlActTabs.addTab(tlActTabs.newTab().setText(mTabTitles[0]));//添加tab选项卡
        tlActTabs.addTab(tlActTabs.newTab().setText(mTabTitles[1]));

        vpActViewPager.setAdapter(myPagerAdapter);                  //给ViewPager设置适配器
        tlActTabs.setupWithViewPager(vpActViewPager);               //将TabLayout和ViewPager关联起来。
        tlActTabs.setTabsFromPagerAdapter(myPagerAdapter);          //给Tabs设置适配器
    }


    /**
     * 初始化recyleview
     *
     */
    public void setUpRecyclerViews() {
        LogUtil.i(ActActivity.this, "into setUpRecyclerViews");

        LinearLayoutManager orgLinearLayoutManager = new LinearLayoutManager(this);
        rvOrgActivity.setLayoutManager(orgLinearLayoutManager);
        rvOrgActivity.setHasFixedSize(true);
        rvOrgActivity.setAdapter(rvOrgActAdapter);
        rvOrgActAdapter.setOnActItemClickListener(this, 0);

        LinearLayoutManager joinLinearLayoutManager = new LinearLayoutManager(this);
        rvJoinActivity.setLayoutManager(joinLinearLayoutManager);
        rvJoinActivity.setHasFixedSize(true);
        rvJoinActivity.setAdapter(rvJoinActAdapter);
        rvJoinActAdapter.setOnActItemClickListener(this, 1);
    }




    /**
     * 应用加载参加过、组织过的活动信息
     */
    private void loadJoinActivity() {

        if (rvJoinActAdapter == null) {
            rvJoinActAdapter = new ActRVAdapter(ActActivity.this, joinLists);
        }

        // 向服务器发送数据 pageIndex=1&rowCount=6&users.id=1
        Map<String, String> params = new HashMap<>(3);
        params.put(StringConstant.CURRENT_PAGE_KEY, currentPage + "");
        params.put(StringConstant.PER_PAGE_KEY, StringConstant.PER_PAGE_COUNT + "");
        params.put(StringConstant.COMMENT_UID, "" + userId);

        RequestInfo info = new RequestInfo(StringConstant.SERVER_MY_All_ACT_URL, params);
        httpTools.post(info, new HttpCallback() {
            @Override
            public void onStart() {
            }

            @Override
            public void onFinish() {
            }

            @Override
            public void onResult(String s) {

                LogUtil.i(ActActivity.this, "参加过、组织过的活动信息 服务器返回数据啦：　ｓ：" + s);

                if (StringConstant.NULL_VALUE.equals(s)) {
                    Toast.makeText(ActActivity.this, "网络貌似粗错了", Toast.LENGTH_SHORT).show();
                    return;
                }

                if ("[]".equals(s)) {
                    Toast.makeText(ActActivity.this, "并没有什么正在组织的活动", Toast.LENGTH_SHORT).show();
                    return;
                }
                  onReadJoinJson(s);

               currentPage++;
            }

            @Override
            public void onError(Exception e) {
            }

            @Override
            public void onCancelled() {
            }

            @Override
            public void onLoading(long l, long l1) {

            }
        });
    }

    public void onReadJoinJson(String json) {

        int insertPos = rvJoinActAdapter.getItemCount();
        try {

            Activities activities = null;
            JSONArray jsonArray = new JSONArray(json);


            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String userImage = "";
                if(jsonObject.getString("user_img") != null)
                {
                    userImage = jsonObject.getString("user_img");
                }

                LogUtil.d(ActActivity.this, "发起人的头像url : " + userImage);

                activities = new Activities(
                        jsonObject.getString("user.id"),
                        jsonObject.getString("theme"),
                        jsonObject.getString("user.name"),
                        userImage,
                        jsonObject.getInt("user.rp"),
                        jsonObject.getString("start_date"),
                        jsonObject.getString("end_date"));
                activities.setId(""+jsonObject.getInt("act.id"));
                activities.setUid(jsonObject.getInt("user.id"));
                joinLists.add(activities);
            }

        if (rvJoinActAdapter == null) {
            rvJoinActAdapter = new ActRVAdapter(ActActivity.this, joinLists);
        }

        LogUtil.d(ActActivity.this, "actLists.size:" + joinLists.size());
        rvJoinActAdapter.notifyItemRangeInserted(insertPos, joinLists.size() - insertPos);
        rvJoinActAdapter.notifyItemRangeChanged(insertPos, joinLists.size() - insertPos);

        currentPage++;

        } catch (JSONException e) {
            e.printStackTrace();
            LogUtil.e(ActActivity.this, " 解析onReadORGJson出错");
        }
    }

    /**
     * 设置用户配置信息
     */
    private void setUserSp() {

        // 验证用户之前是否登录
        sp = getSharedPreferences("user_sp", Context.MODE_PRIVATE);

        if (sp.getBoolean("user_login", false)) {
            u = (User) FileUtil.readObject(this, "user");
            if (u == null)
                return;
            setUserInfo(u);
        }

    }

    SharedPreferences sp;
    User u;

    /**
     * 设置用户信息
     * @param u
     */
    private void setUserInfo (User u) {
        username.setText(u.getUsername());

        if ("0".equals(u.getGender())) {
            gender.setBackgroundResource(R.mipmap.ic_user_female);
        }
        else if ("1".equals(u.getGender())) {
            gender.setBackgroundResource(R.mipmap.ic_user_male);
        }
        else {
            gender.setBackgroundResource(R.mipmap.ic_user_sox);
        }

        if (u.getAvatarFilePath() != null) {
            File avatar = new File (u.getAvatarFilePath());
            LogUtil.d(ActActivity.this, "头像url :" + u.getAvatarFilePath());
            Picasso.with(getApplicationContext())
                    .load(avatar)
                    .centerCrop()
                    .resize(avatarSize, avatarSize)
                    .transform(new RoundedTransformation())
                    .into(ivMenuUserProfilePhoto);
        }
    }



}
