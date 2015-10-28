package com.thh.easy.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewTreeObserver;

import com.thh.easy.R;
import com.thh.easy.adapter.UserProfileAdapter;
import com.thh.easy.view.RevealBackgroundView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 *   个人信息界面:
 *         个人信息
 *         我的图集
 *
 * @author cloud
 * @time 2015 10 24
 *
 */
public class UserProfileActivity extends AppCompatActivity implements RevealBackgroundView.OnStateChangeListener {

    public static final String ARG_REVEAL_START_LOCATION = "reveal_start_location";

    @Bind(R.id.vRevealBackground)
    RevealBackgroundView vRevealBackground; // 展开动画背景

    @Bind(R.id.rvUserProfile)
    RecyclerView rvUserProfile; // 图集
    private UserProfileAdapter userPhotosAdapter; // 图集adapter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        ButterKnife.bind(UserProfileActivity.this);
        setupUserProfileGrid();
        setupRevealBackground(savedInstanceState);
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
    public static void startUserProfileFromLocation(int[] startingLocation, Activity startingActivity) {
        Intent intent = new Intent(startingActivity, UserProfileActivity.class);
        intent.putExtra(ARG_REVEAL_START_LOCATION, startingLocation);
        startingActivity.startActivity(intent);
    }

}
