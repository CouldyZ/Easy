package com.thh.easy.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.ext.HttpCallback;
import com.android.volley.ext.RequestInfo;
import com.android.volley.ext.tools.HttpTools;
import com.squareup.picasso.Picasso;
import com.thh.easy.R;
import com.thh.easy.adapter.UserProfileAdapter;
import com.thh.easy.constant.StringConstant;
import com.thh.easy.view.RevealBackgroundView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *   个人信息界面:
 *         个人信息
 *         我的图集
 *
 *  //TODO 从点击的位置，添加用户信息进来，用户id，这边查询用户所有信息
 * @author cloud
 * @time 2015 10 24
 *
 */
public class UserProfileActivity extends AppCompatActivity implements RevealBackgroundView.OnStateChangeListener {

    public static final String ARG_REVEAL_START_LOCATION = "reveal_start_location";
    public static final String TAG = "UserProfileActivity";

    @Bind(R.id.iv_user_profile_photo)
    ImageView ivUserPhoto;           // 用户头像

    @Bind(R.id.tv_profile_user_name)
    TextView tvUserName;             // 用户名

    @Bind(R.id.tv_profile_user_rp)
    TextView tvUserRP;               // 用户节操值

    @Bind(R.id.tv_profile_user_email)
    TextView tvEmail;             // 用户简介

    @Bind(R.id.tv_profile_post_num)
    TextView tvPosts;                // 发过的帖子数

    @Bind(R.id.tv_profile_org_act)
    TextView tvOrgActs;              // 发起的活动数

    @Bind(R.id.tv_profile_collect)
    TextView tvCollects;             // 收藏的帖子数

    @Bind(R.id.vRevealBackground)
    RevealBackgroundView vRevealBackground; // 展开动画背景

    @Bind(R.id.rvUserProfile)
    RecyclerView rvUserProfile; // 图集
    private UserProfileAdapter userPhotosAdapter; // 图集adapter



    HttpTools httpTools;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        ButterKnife.bind(UserProfileActivity.this);
        userId = getIntent().getIntExtra(StringConstant.COMMENT_UID, 1);
        HttpTools.init(this);
        httpTools = new HttpTools(this);

        loadProfileData();
        setupUserProfileGrid();
        setupRevealBackground(savedInstanceState);
    }
    int userId;
    /**
     * 加载用户信息
     */
    private void loadProfileData(){
        Map<String , String> params = new HashMap<>();
        params.put(StringConstant.COMMENT_UID, "" + userId);
        RequestInfo info = new RequestInfo(StringConstant.SERVER_PROFILE_URL, params);

        httpTools.post(info, new HttpCallback() {
            @Override
            public void onStart() {

            }

            @Override
            public void onFinish() {

            }

            @Override
            public void onResult(String s) {
                readJson(s);
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

    /**
     * 解析网络数据
     * @param jsonResult
     */
    private void readJson(String jsonResult){
        // TODO 解析数据，并将数据设置到界面上

        try {
            JSONArray jsonArray = new JSONArray(jsonResult);

            JSONObject jsonObject = jsonArray.getJSONObject(0);

            JSONObject userObject = jsonObject.getJSONObject("users");

            String imgUrl = userObject.getJSONObject("image").getString("urls");

            tvUserName.setText(userObject.getString("name"));             // 用户名
            tvUserRP.setText("rp: " + userObject.getInt("rp"));           // 节操值
            tvEmail.setText(userObject.getString("email"));               // 用户邮箱
            tvPosts.setText(""+jsonObject.getInt("post"));                 // 发过的帖子数
            tvCollects.setText(""+jsonObject.getInt("collect"));             // 收藏的帖子数

            // TODO 设置活动数
           // tvOrgActs.setText(jsonObject.getInt("activity"));
            // 加载头像
            if(imgUrl!=null){
                Picasso.with(this)
                        .load(imgUrl)
                        .placeholder(R.mipmap.bili_default_avatar)
                        .into(ivUserPhoto);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG,"171-->"+" JSON 解析出错");
        }
    }



    /**
     * 初始化展开动画
     * @param savedInstanceState
     */
    private void setupRevealBackground(Bundle savedInstanceState) {
        vRevealBackground.setOnStateChangeListener(this);
        if (savedInstanceState == null) {
            final int[] startingLocation =
                    getIntent().getIntArrayExtra(ARG_REVEAL_START_LOCATION);

            vRevealBackground.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    vRevealBackground.getViewTreeObserver().removeOnPreDrawListener(this);
                    vRevealBackground.startFromLocation(startingLocation);
                    return false;
                }
            });
        } else {
            userPhotosAdapter.setLockedAnimations(true);
            vRevealBackground.setToFinishedFrame();
        }
    }


    /**
     * 设置图集内容
     */
    private void setupUserProfileGrid() {
        final StaggeredGridLayoutManager layoutManager =
                new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);

        rvUserProfile.setLayoutManager(layoutManager);
        rvUserProfile.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                userPhotosAdapter.setLockedAnimations(true);
            }
        });
    }


    /**
     * 进入界面时，先有个水波纹展开界面，再开始图集的动画
     * @param state
     */
    @Override
    public void onStateChange(int state) {
        if (RevealBackgroundView.STATE_FINISHED == state) {

            // 界面展开之后，先将图集设置为可见，再开始动画
            rvUserProfile.setVisibility(View.VISIBLE);
            userPhotosAdapter = new UserProfileAdapter(this);
            rvUserProfile.setAdapter(userPhotosAdapter);
        } else {

            // 页面展开动画还没结束，用户图集设置为不可见
            rvUserProfile.setVisibility(View.INVISIBLE);
        }
    }


    /**
     * 从特定的地方开始动画
     * @param startingLocation
     * @param startingActivity
     */
    public static void startUserProfileFromLocation(int usersId, int[] startingLocation, Activity startingActivity) {
        Intent intent = new Intent(startingActivity, UserProfileActivity.class);
        intent.putExtra(StringConstant.COMMENT_UID, usersId);
        intent.putExtra(ARG_REVEAL_START_LOCATION, startingLocation);
        startingActivity.startActivity(intent);
    }

    /**
     * toolbar返回
     */
    @OnClick(R.id.iv_profile_back)
    void onToolbarBackPress() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
