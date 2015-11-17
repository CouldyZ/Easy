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
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.android.volley.ext.HttpCallback;
import com.android.volley.ext.RequestInfo;
import com.android.volley.ext.tools.HttpTools;
import com.thh.easy.R;
import com.thh.easy.adapter.CommentRVAdapter;
import com.thh.easy.constant.StringConstant;
import com.thh.easy.entity.Comment;
import com.thh.easy.entity.User;
import com.thh.easy.util.FileUtil;
import com.thh.easy.util.LogUtil;
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
 *  // TODO 添加评论有BUG， 添加的位置间有间隙啊
 *
 */
public class CommentsActivity extends AppCompatActivity implements SendCommentButton.OnSendClickListener{

    private static final String TAG = "CommentsActivity";

    public static final String COMMENT_DRAWING_START_LOCATION = "arg_drawing_start_location";

    @Bind(R.id.et_add_comment)
    public EditText etAddComment;                  // 评论编辑框

    @Bind(R.id.btn_send_comments)
    public SendCommentButton mybtnSendComment;     // 评论的发送按钮

    @Bind(R.id.comm_content_root)
    LinearLayout contentRoot;                      // 评论列表的根布局

    @Bind(R.id.rv_comments)
    RecyclerView rvComments;                       // 评论列表

    @Bind(R.id.ll_add_comment)
    LinearLayout llAddComment;                     // 发表评论的根布局


    private CommentRVAdapter commentsAdapter;      // rvComments的adapter

    private LinearLayoutManager linearLayoutManager;

    private List<Comment> commentList = new ArrayList<Comment>();


    private HttpTools httpTools;
    private int postID = -1;
    private int currentPage = 1;
    private boolean isLoading = false;
    private int drawingStartLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        setUpToolbar();
        HttpTools.init(getApplicationContext());
        httpTools = new HttpTools(getApplicationContext());

        postID = getIntent().getIntExtra("postID", -1);
        LogUtil.d(CommentsActivity.this, "进入留言界面啦  ~~~   postID:" + postID);

        getComments();
        setupComments();
        setupSendCommentButton();

        // 获得原帖子界面的点击评论的位置
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
        }

        isLoading = true;


        // pageIndex=1&rowCount=6&posts.id=1
        Map<String, String> params = new HashMap<>(3);
        params.put(StringConstant.POSTID, postID + "");
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

                LogUtil.d(CommentsActivity.this, "获得留言数据：" + s);

                if ("0".equals(s)) {
                    Snackbar.make(rvComments, "还没有留言 你要抢个沙发嘛", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if ("[]".equals(s)){
                    Snackbar.make(rvComments, "还没有留言 你要抢个沙发嘛", Snackbar.LENGTH_SHORT).show();
                    return;
                }


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

    /**
     * 读取json数据
     *
     * @param json
     */
    private void onReadJson(String json) {

        int insertPos = commentsAdapter.getItemCount();

        if(commentsAdapter== null){
            commentsAdapter = new CommentRVAdapter(CommentsActivity.this, commentList);
        }
        try {
            JSONArray jsonArray = new JSONArray(json);

            if (jsonArray.length() < 1)
                return;

            for (int i = 0 ; i < jsonArray.length() ; i ++) {
                JSONObject jsonItem = jsonArray.getJSONObject(i);
                Comment comment = null;

                String avatar = "";

                avatar =  StringConstant.SERVER_IP + jsonItem.getJSONObject("users").getJSONObject("image").getString("urls");

                LogUtil.d(CommentsActivity.this, "留言头像：" + avatar);

                comment = new Comment(
                        avatar,
                        jsonItem.getString("contents"),
                        jsonItem.getString("dates"),
                        jsonItem.getJSONObject("users").getString("name"),
                        jsonItem.getJSONObject("users").getInt("id"));

                commentList.add(comment);
            }

            commentsAdapter.notifyItemRangeInserted(insertPos, commentList.size() - insertPos);
            commentsAdapter.notifyItemRangeChanged(insertPos, commentList.size() - insertPos);

            currentPage ++;
        } catch (JSONException e) {
            LogUtil.e(CommentsActivity.this, "解析json 出错 " + e.getMessage());
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

        rvComments.setAdapter(commentsAdapter);
        rvComments.setOverScrollMode(View.OVER_SCROLL_NEVER);
        rvComments.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int lastVisibleItems = linearLayoutManager.findLastVisibleItemPosition();
                int totalItemCount = linearLayoutManager.getItemCount();

                if (lastVisibleItems == totalItemCount - 1 && dy > 0) {
                    if (isLoading) {
                        LogUtil.d(CommentsActivity.this, "ignore manually update!");
                    } else {
                        getComments();
                        LogUtil.d(CommentsActivity.this, "new comment");
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

        commentsAdapter.notifyItemRangeChanged(0, commentList.size());
        LogUtil.d(CommentsActivity.this, commentList.size() + " 1");
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
            commentsAdapter.setAnimationsLocked(false);
            commentsAdapter.setDelayEnterAnimation(false);
            rvComments.smoothScrollBy(0, rvComments.getChildAt(0).getHeight() * commentsAdapter.getItemCount());


            final  User u = (User) FileUtil.readObject(getApplicationContext(), "user");
            final int userId = Utils.getUserId(getApplicationContext());
            if (u == null)
                Snackbar.make(rvComments, "岂可修!要先登陆啊", Snackbar.LENGTH_SHORT).show();
            if ("".equals(etAddComment.getEditableText().toString()))
                Snackbar.make(rvComments, "不写点啥嘛", Snackbar.LENGTH_SHORT).show();

            final String content = etAddComment.getEditableText().toString();

            Map<String, String> params = new HashMap<>(3);
            params.put(StringConstant.COMMENT_SEND_ID, "" + userId);
            params.put(StringConstant.COMMENT_POST_ID, postID + "");
            params.put(StringConstant.COMMENT_CONTENTS, content);

            LogUtil.d(CommentsActivity.this, "");

            RequestInfo info = new RequestInfo(StringConstant.SERVER_ADDCOMMENT_URL, params);
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

                        Snackbar.make(rvComments, "评论成功", Snackbar.LENGTH_SHORT).show();

                        Comment comment = new Comment(
                                u.getAvatarFilePath(),
                                content,
                                "",
                                u.getUsername(),
                                userId);

                        commentList.add(comment);

                        commentsAdapter.notifyItemRangeInserted(0, 1);
                        commentsAdapter.notifyItemRangeChanged(0, 1);

                    }else{
                        Snackbar.make(rvComments, "评论失败", Snackbar.LENGTH_SHORT).show();
                    }
                    mybtnSendComment.setCurrentState(SendCommentButton.STATE_DONE);
                }

                @Override
                public void onError(Exception e) {
                    Snackbar.make(rvComments, "骚年 你评论失败啦", Snackbar.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelled() {
                }

                @Override
                public void onLoading(long l, long l1) {
                }
            });

            etAddComment.setText(null);

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
