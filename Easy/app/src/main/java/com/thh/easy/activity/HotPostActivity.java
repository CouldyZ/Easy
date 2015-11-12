package com.thh.easy.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.ext.HttpCallback;
import com.android.volley.ext.RequestInfo;
import com.android.volley.ext.tools.HttpTools;
import com.thh.easy.constant.StringConstant;
import com.thh.easy.R;
import com.thh.easy.adapter.PostRVAdapter;
import com.thh.easy.entity.Post;
import com.thh.easy.entity.User;
import com.thh.easy.util.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 *   主界面:
 *         显示帖子
 */
public class HotPostActivity extends BaseActivity implements PostRVAdapter.OnPostItemClickListener, BaseDrawerActivity.OnStartActivityListener {

    private static final String TAG = "HotActivity";

    private boolean pendingIntroAnimation;      // 是否开始进入动画
    private MenuItem inboxMenuItem;             // toolbar上的meun

    private PostRVAdapter postRVAdapter;        // rvPost的适配器

    @Bind(R.id.rv_hot_post)
    public RecyclerView rvPost;                // 主界面帖子列表

    @Bind(R.id.iv_logo)
    public ImageView ivLogo;                   // toolbar的logo

    @Bind(R.id.cl_hot_container)
    CoordinatorLayout clContainer;             // snakebar的根布局

    @Bind(R.id.sr_hot_container)
    SwipeRefreshLayout srContainer;

    List<Post> postList = new ArrayList<Post>();  // 最新帖子的数据集合

    LinearLayoutManager linearLayoutManager;    // recyclerview的布局方式-线性

    boolean isLoading = false;

    int currentPage = 1;                        // 当前页

    HttpTools httpTools;                        // 网络操作工具

    SharedPreferences sp;                       // 写入登陆信息，部分用户信息

    User u = null;                              // 进入此app的用户


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot_post);

        ButterKnife.bind(this);

        HttpTools.init(this);
        httpTools = new HttpTools(this);

        getToolbar().setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        // 设置进入动画
        if (savedInstanceState == null) {
            pendingIntroAnimation = true;
        }

        loadPosts();
        // 设置RecycleView
        setupPost();

        srContainer.setColorSchemeResources(R.color.easy_primary_light, R.color.easy_primary, R.color.easy_primary_dark);
        srContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (srContainer.isRefreshing()) {
                    Log.d(TAG, "刷新请求");
                    // 网络检查
                    if (Utils.checkNetConnection(getApplicationContext())) {
                        Log.d(TAG, "我联网啦");
                        currentPage = 1;
                        rvPost.removeAllViews();
                        postRVAdapter.notifyItemRangeRemoved(0, postList.size());
                        postList.clear();
                        loadPosts();
                        rvPost.scrollToPosition(0);
                    } else {
                        Snackbar.make(clContainer, "少年呦 你联网了嘛", Snackbar.LENGTH_SHORT).show();
                    }
                }
            }
        });

        rvPost.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        srContainer.setEnabled(false);
                        break;

                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        srContainer.setEnabled(true);
                        break;

                }
                return false;
            }
        });

    }


    /**
     * 设置帖子recyclerview的相应初始数据
     */
    private void setupPost() {
        linearLayoutManager = new LinearLayoutManager(this);
        rvPost.setLayoutManager(linearLayoutManager);

        rvPost.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int lastVisibleItems = linearLayoutManager.findLastVisibleItemPosition();
                int totalItemCount = linearLayoutManager.getItemCount();

                //Log.i (TAG, "last - " + lastVisibleItems + " totle - " + totalItemCount);

                if (lastVisibleItems == totalItemCount - 1 && dy > 0) {
                    if (isLoading) {
                        Log.d(TAG, "ignore manually update!");
                    } else {
                        // loadPosts中控制isLoading
                        loadPosts();
                        Log.i(TAG, "new data");
                        isLoading = false;
                    }
                }
            }
        });


    }


    /**
     * 请求帖子数据
     */
    private void loadPosts() {
        if (postRVAdapter == null) {
            postRVAdapter = new PostRVAdapter(HotPostActivity.this, postList);
            postRVAdapter.setOnPostItemClickListener(HotPostActivity.this);
        }

        Map<String, String> params = new HashMap<String, String>(2);
        params.put(StringConstant.CURRENT_PAGE_KEY, currentPage+"");
        params.put(StringConstant.PER_PAGE_KEY, StringConstant.PER_PAGE_COUNT + "");

        if(getIntent().getIntExtra(StringConstant.USER_ID, 1) == 1) {
            params.put(StringConstant.USER_ID, getIntent().getStringExtra(StringConstant.USER_ID));
        }
        RequestInfo info = new RequestInfo(StringConstant.SERVER_HOTPOST_URL, params);
        httpTools.post(info, new HttpCallback() {
            @Override
            public void onStart() {
                isLoading = true;
                Log.i(TAG, "当前页" + currentPage);
            }

            @Override
            public void onFinish() {
                // 一共加载多少条
                isLoading = false;
                srContainer.setRefreshing(false);
            }

            @Override
            public void onResult(String s) {
                Log.i(TAG, s);

                if (StringConstant.NULL_VALUE.equals(s)) {
                    Snackbar.make(clContainer, "网络貌似粗错了", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if ("[]".equals(s)) {
                    // Snackbar.make(clContainer, "什么帖子都没有呦", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                onReadJson(s);
                Log.d(TAG, postRVAdapter.getItemCount() + " loadPost");

                currentPage++;
            }

            @Override
            public void onError(Exception e) {
            }

            @Override
            public void onCancelled() {
                srContainer.setRefreshing(false);
            }

            @Override
            public void onLoading(long l, long l1) {

            }
        });
    }

    private void onReadJson(String json) {

        int insertPos = postRVAdapter.getItemCount();
        try {
            JSONArray jsonArray = new JSONArray(json);

            Log.i(TAG, jsonArray.length()+" onReadJson");

            for (int i = 0 ; i < jsonArray.length() ; i ++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                JSONObject postObj = jsonObject.getJSONObject("posts");
                JSONObject userObj = postObj.getJSONObject("users");

                Post post = null;

                String imageUrl = null;
                if (!postObj.isNull("image")) {
                    imageUrl = postObj.getJSONObject("image").getString("urls");
                }

                String avatar = null;
                if (!postObj.getJSONObject("users").isNull("image"))
                    avatar = postObj.getJSONObject("users").getJSONObject("image").getString("urls");

                post = new Post(postObj.getInt("id"),
                        userObj.getInt("id"),
                        userObj.getString("name"),
                        postObj.getString("contents"),
                        postObj.getString("dates"),
                        imageUrl,
                        avatar,
                        jsonObject.getInt("likes"),
                        jsonObject.getInt("isLike"));

                postList.add(post);
            }

            postRVAdapter.notifyItemRangeInserted(insertPos, postList.size() - insertPos);
            postRVAdapter.notifyItemRangeChanged(insertPos, postList.size() - insertPos);

            currentPage++;

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "解析Json出错");
        }
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
        int actionbarSize = Utils.dpToPx(56);
        getToolbar().setTranslationY(-actionbarSize);
        ivLogo.setTranslationY(-actionbarSize);

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
        // 动画结束再设置帖子内容

        // 网络检查
        if (!Utils.checkNetConnection(getApplicationContext())) {
            Snackbar.make(clContainer, "少年呦 你联网了嘛", Snackbar.LENGTH_SHORT).show();
        }

//        loadPosts();
        rvPost.setAdapter(postRVAdapter);
        rvPost.scrollToPosition(0);
        isLoading = false;
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
        UserProfileActivity.startUserProfileFromLocation(postList.get(position).getUid(),startingLocation, this);
        overridePendingTransition(0, 0);
    }


    /**
     * 回复点击事件
     * @param v
     * @param position
     */
    @Override
    public void onCommentsClick(View v, int position) {
        final Intent intent = new Intent(this, CommentsActivity.class);
        int[] startingLocation = new int[2];
        v.getLocationOnScreen(startingLocation);
        intent.putExtra(CommentsActivity.COMMENT_DRAWING_START_LOCATION, startingLocation[1]);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }


    @Override
    public void onStartActivity(Class targetActivity) {
        if (MainActivity.class == targetActivity)
            return;

        startActivity(new Intent(HotPostActivity.this, targetActivity));
        overridePendingTransition(0, 0);
    }

    @Override
    public void onLikeClick(View v, int position) {

    }

    @Override
    public void onMoreClick(View v, int position) {

    }
}
