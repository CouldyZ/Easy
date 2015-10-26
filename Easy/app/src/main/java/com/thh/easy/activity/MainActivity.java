package com.thh.easy.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.thh.easy.R;
import com.thh.easy.adapter.PostRVAdapter;
import com.thh.easy.util.Utils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 *   主界面:
 *         显示帖子
 *  @author cloud
 *  @time 2015 10 24
 */
public class MainActivity extends BaseDrawerActivity implements PostRVAdapter.OnFeedItemClickListener{

    private boolean pendingIntroAnimation;// 是否开始进入动画
    private MenuItem inboxMenuItem;
    private PostRVAdapter postRVAdapter;

    @Bind(R.id.rv_post)
    public RecyclerView rvPost;     // 主界面帖子列表

    @Bind(R.id.iv_logo)
    public ImageView ivLogo;        // toolbar的logo

    @Bind(R.id.ib_new_post)
    public ImageButton btnCreate;   // floating action button

  /*  @Bind(R.id.tb_main_post)
    public Toolbar toolbar;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(MainActivity.this);

        // 设置进入动画
        if (savedInstanceState == null) {
            pendingIntroAnimation = true;
        }
       // setupToolbar();
        setupPost();

        if (!Utils.checkNetConnection(getApplicationContext())) {
            Toast.makeText(MainActivity.this, "少年呦 你联网了嘛", Toast.LENGTH_SHORT).show();
        }
    }
/*
    private void setupToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_menu_white);
    }*/


    /**
     * 设置帖子recyclerview的相应初始数据
     */
    private void setupPost() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvPost.setLayoutManager(linearLayoutManager);
        postRVAdapter = new PostRVAdapter(this);
        rvPost.setAdapter(postRVAdapter);
        postRVAdapter.setOnPostItemClickListener(this);
    }


    /**
     * 设置menu上的进入动画
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        inboxMenuItem = menu.findItem(R.id.action_search);
        inboxMenuItem.setActionView(R.layout.menu_item_view);
        if (pendingIntroAnimation) {
            // 开始动画~\(≧▽≦)/~啦啦啦
            pendingIntroAnimation = false;
            startIntroAnimation();
        }
        return true;
    }


    /**
     * toolbar 动画
     */
    private static final int ANIM_DURATION_TOOLBAR = 300;
    private void startIntroAnimation() {
        btnCreate.setTranslationY(2 * getResources().getDimensionPixelOffset(R.dimen.btn_fab_size));
        int actionbarSize = Utils.dpToPx(56);
        getToolbar().setTranslationY(-actionbarSize);
        ivLogo.setTranslationY(-actionbarSize);
        inboxMenuItem.getActionView().setTranslationY(-actionbarSize);

        getToolbar().animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(300);
        ivLogo.animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(400);
        inboxMenuItem.getActionView().animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(500)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        startContentAnimation();
                    }
                })
                .start();
    }


    /**
     *  floatingActionButton 进入动画
     */
    private static final int ANIM_DURATION_FAB = 400;
    private void startContentAnimation() {
        btnCreate.animate()
                .translationY(0)
                .setInterpolator(new OvershootInterpolator(1.f))
                .setStartDelay(300)
                .setDuration(ANIM_DURATION_FAB)
                .start();
        // 动画结束再设置帖子内容
        postRVAdapter.updateItems();
    }


    /**
     * 点击帖子中的头像后，进入个人信息界面
     * @param v
     * @param position
     */
    @Override
    public void onProfileClick(View v, int position) {
        // 获得点击头像时的位置
        int[] startingLocation = new int[2];
        v.getLocationOnScreen(startingLocation);
        startingLocation[0] += v.getWidth() / 2;

        // 进入用户信息界面时，设置进入动画
        UserProfileActivity.startUserProfileFromLocation(startingLocation, this);
        overridePendingTransition(0, 0);
    }


    /**
     * 回复点击事件
     * @param v
     * @param position
     */
    @Override
    public void onCommentsClick(View v, int position) {
        //final Intent intent = new Intent(this, CommentsActivity.class);

        //Get location on screen for tapped view
        int[] startingLocation = new int[2];
        v.getLocationOnScreen(startingLocation);
        // intent.putExtra(CommentsActivity.ARG_DRAWING_START_LOCATION, startingLocation[1]);
        // startActivity(intent);

        //Disable enter transition for new Acitvity
        overridePendingTransition(0, 0);
    }


}
