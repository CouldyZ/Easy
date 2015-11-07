package com.thh.easy.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.ext.HttpCallback;
import com.android.volley.ext.RequestInfo;
import com.android.volley.ext.tools.HttpTools;
import com.thh.easy.constant.StringConstant;
import com.thh.easy.R;
import com.thh.easy.adapter.CommentRVAdapter;
import com.thh.easy.entity.Comment;
import com.thh.easy.entity.User;
import com.thh.easy.util.FileUtil;
import com.thh.easy.util.Utils;
import com.thh.easy.view.SendCommentButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 评论界面
 */
public class CommentsActivity extends AppCompatActivity implements SendCommentButton.OnSendClickListener{

    private static final String TAG = "CommentsActivity";

    public static final String COMMENT_DRAWING_START_LOCATION = "arg_drawing_start_location";

    @Bind(R.id.et_add_comment)
    public EditText etAddComment;             // 评论编辑框

    @Bind(R.id.btn_send_comments)
    public SendCommentButton mybtnSendComment; // 评论的发送按钮


    @Bind(R.id.comm_content_root)
    LinearLayout contentRoot;                   // 评论列表的根布局

    @Bind(R.id.rv_comments)
    RecyclerView rvComments;                     // 评论列表

    @Bind(R.id.ll_add_comment)
    LinearLayout llAddComment;                   // 发表评论的根布局


    private CommentRVAdapter commentsAdapter;     // rvComments的adapter
    private int drawingStartLocation;

    private LinearLayoutManager linearLayoutManager;

    private int postID = -1;

    private List<Comment> commentList = new ArrayList<Comment>();

    private HttpTools httpTools;

    private int currentPage = 1;

    private boolean isLoading = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        postID = getIntent().getIntExtra("postID", -1);

        HttpTools.init(getApplicationContext());
        httpTools = new HttpTools(getApplicationContext());

        getComments();

        setUpToolbar();
        setupComments();

        setupSendCommentButton();

        // 获得原帖子界面的点击评论的位置
        // TODO 发送、获得位置
        drawingStartLocation =  getIntent().getIntExtra(COMMENT_DRAWING_START_LOCATION, 0);



        if (savedInstanceState == null) {
            contentRoot.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    contentRoot.getViewTreeObserver().removeOnPreDrawListener(this);
                    if (commentList.size() > 0)
                        startIntroAnimation();
                    return true;
                }
            });
        }

    }

    private void getComments () {
        if (commentsAdapter == null) {
            commentsAdapter = new CommentRVAdapter(this, commentList);
//            rvComments.setAdapter(commentsAdapter);
        }

        isLoading = true;

        Map<String, String> params = new HashMap<>(3);
        params.put(StringConstant.POSTID, postID+"");
        params.put(StringConstant.CURRENT_PAGE_KEY, currentPage+"");
        params.put(StringConstant.PER_PAGE_KEY, 10+"");
        RequestInfo info = new RequestInfo(StringConstant.SERVER_COMMENT_URL, params);
        httpTools.post(info, new HttpCallback() {
            @Override
            public void onStart() {

            }

            @Override
            public void onFinish() {
                isLoading = false;
            }

            @Override
            public void onResult(String s) {
                Log.d(TAG, s);


                if ("0".equals(s)) {
                    Snackbar.make(rvComments, "还没有留言 你要抢个沙发嘛", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if ("[]".equals(s))
                    return;

                onReadJson(s);

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

    private void onReadJson(String json) {
        int insertPos = commentsAdapter.getItemCount();
        try {
            JSONArray jsonArray = new JSONArray(json);

            if (jsonArray.length() < 1)
                return;

            for (int i = 0 ; i < jsonArray.length() ; i ++) {
                JSONObject jsonItem = jsonArray.getJSONObject(i);
                Comment comment = null;

                String avatar = null;
                if (jsonItem.getJSONObject("posts").getInt("id") == postID) {
                    if (!jsonItem.isNull(jsonItem.getJSONObject("posts").getJSONObject("users").getJSONObject("image").getString("urls"))) {
                        avatar = new StringBuffer(StringConstant.SERVER_IP).append(jsonItem.getJSONObject("posts").getJSONObject("users").getJSONObject("image").getString("urls")).toString();
                    }
                }

                comment = new Comment(jsonItem.getJSONObject("posts").getJSONObject("users").getInt("id"),
                        jsonItem.getString("contents"),
                        avatar);

                commentList.add(comment);
            }

            Log.i(TAG, "insert " + insertPos + " | size " + commentList.size());
//            commentsAdapter.notifyItemChanged(0);
            commentsAdapter.notifyItemRangeInserted(insertPos, commentList.size() - insertPos);
            commentsAdapter.notifyItemRangeChanged(insertPos, commentList.size() - insertPos);

            currentPage ++;

            Log.d(TAG, commentList.size()+" 2");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setUpToolbar() {
        ButterKnife.bind(this);
        if (comToolbar != null) {
            comToolbar.setTitle("");
            setSupportActionBar(comToolbar);
        }
    }


    /**
     * 初始化评论列表，及监听评论的滑动事件
     */
    private void setupComments() {
        linearLayoutManager = new LinearLayoutManager(this);
        rvComments.setLayoutManager(linearLayoutManager);
        rvComments.setHasFixedSize(true);

//        commentsAdapter = new CommentRVAdapter(this);
        rvComments.setAdapter(commentsAdapter);
        rvComments.setOverScrollMode(View.OVER_SCROLL_NEVER);
        rvComments.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                        getComments();
                        Log.i(TAG, "new comment");
                        isLoading = false;
                    }
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    // commentsAdapter.setAnimationsLocked(true);
                }
            }
        });

        // commentsAdapter.notifyItemRangeChanged(0, commentList.size());
        Log.d(TAG, commentList.size() + " 1");
    }


    /**
     * 绑定发送评论按钮的监听事件
     */
    private void setupSendCommentButton() {
        mybtnSendComment.setOnSendClickListener(this);
    }

    @Bind(R.id.comm_toolbar)
    Toolbar comToolbar;

    /**
     * 设置进入动画
     */
    private void startIntroAnimation() {
        ViewCompat.setElevation(comToolbar, 0);
        contentRoot.setScaleY(0.1f);
        contentRoot.setPivotY(drawingStartLocation);
        llAddComment.setTranslationY(200);

        contentRoot.animate()
                .scaleY(1)
                .setDuration(200)
                .setInterpolator(new AccelerateInterpolator())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        ViewCompat.setElevation(comToolbar, Utils.dpToPx(8));
                        animateContent();
                    }
                })
                .start();
    }

    /**
     * 设置内容动画
     */
    private void animateContent() {
//        commentsAdapter.updateItems();
        llAddComment.animate().translationY(0)
                .setInterpolator(new DecelerateInterpolator())
                .setDuration(200)
                .start();
    }

    /**
     * 按返回键，设置退出动画
     */
    @Override
    public void onBackPressed() {
        ViewCompat.setElevation(comToolbar, 0);
        contentRoot.animate()
                .translationY(Utils.getScreenHeight(this))
                .setDuration(200)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        CommentsActivity.super.onBackPressed();
                        overridePendingTransition(0, 0);
                    }
                })
                .start();
    }


    /**
     * 发送一条数据
     * @param v
     */
    @Override
    public void onSendClickListener(View v) {
        if (validateComment()) {
//            commentsAdapter.addItem();
            commentsAdapter.setAnimationsLocked(false);
            commentsAdapter.setDelayEnterAnimation(false);
            rvComments.smoothScrollBy(0, rvComments.getChildAt(0).getHeight() * commentsAdapter.getItemCount());

            // TODO 发送评论
            User u = (User) FileUtil.readObject(getApplicationContext(), "user");
            if (u == null)
                Toast.makeText(getApplicationContext(), "岂可修!要先登陆啊", Toast.LENGTH_SHORT).show();

            String content = null;
            if ("".equals(etAddComment.getEditableText().toString()))
                Toast.makeText(getApplicationContext(), "不写点啥嘛", Toast.LENGTH_SHORT).show();

            content = etAddComment.getEditableText().toString();

            Map<String, String> params = new HashMap<>(3);
            params.put(StringConstant.COMMENT_UID, u.getId()+"");
            params.put(StringConstant.COMMENT_POST_ID, postID + "");
            params.put(StringConstant.COMMENT_CONTENTS, content);
            RequestInfo info = new RequestInfo(StringConstant.SERVER_IP, params);
            httpTools.post(info, new HttpCallback() {
                @Override
                public void onStart() {
                }

                @Override
                public void onFinish() {
                }

                @Override
                public void onResult(String s) {
                    if ("1".equals(s)) {
                        Toast.makeText(CommentsActivity.this, "评论成功", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(Exception e) {
                    Toast.makeText(CommentsActivity.this, "骚年 你评论失败啦", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelled() {
                }

                @Override
                public void onLoading(long l, long l1) {
                }
            });

            etAddComment.setText(null);
            mybtnSendComment.setCurrentState(SendCommentButton.STATE_DONE);
        }
    }


    /**
     * 如果填写的评论为空，评论按钮会闪动
     * @return
     */
    private boolean validateComment() {
        if (TextUtils.isEmpty(etAddComment.getText())) {
            mybtnSendComment.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake_error));
            return false;
        }

        return true;
    }

    /**
     * toolbar返回
     */
    @OnClick(R.id.iv_back)
    void onToolbarBackPress() {
        onBackPressed();
    }

}
