package com.thh.easy.activity;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.ext.HttpCallback;
import com.android.volley.ext.RequestInfo;
import com.android.volley.ext.tools.HttpTools;
import com.thh.easy.Constant.StringConstant;
import com.thh.easy.R;
import com.thh.easy.adapter.PostRVAdapter;
import com.thh.easy.entity.Post;
import com.thh.easy.util.Utils;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NewPostActivity extends AppCompatActivity implements PostRVAdapter.OnPostItemClickListener {

    private static final String TAG = "MainActivity";

    private PostRVAdapter postRVAdapter;

    @Bind(R.id.rv_back_post)
    public RecyclerView rvPost;     // 主界面帖子列表

    @Bind(R.id.ib_back_post)
    public FloatingActionButton btnCreate;   // floating action button

    @Bind(R.id.cl_main_more_container)
    CoordinatorLayout clContainer;

    List<Post> postList = new ArrayList<Post>();

    LinearLayoutManager linearLayoutManager;

    boolean isLoading = false;

    int currentPage = 1;   // 当前页

    HttpTools httpTools;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        ButterKnife.bind(NewPostActivity.this);

        HttpTools.init(this);
        httpTools = new HttpTools(this);

        loadPosts();
        // 设置RecycleView
        setupPost();

        startContentAnimation();
    }

    /**
     * 设置帖子recyclerview的相应初始数据
     */
    private void setupPost() {
        linearLayoutManager = new LinearLayoutManager(this);
        rvPost.setLayoutManager(linearLayoutManager);

        postRVAdapter = new PostRVAdapter(this, postList);
        postRVAdapter.setOnPostItemClickListener(this);

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

                if (lastVisibleItems >= totalItemCount - 2 && dy > 0) {
                    if (isLoading) {
                        Log.d(TAG, "ignore manually update!");
                    } else {
                        // loadPosts中控制isLoading
                        // loadPosts();
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
        Map<String, String> params = new HashMap<String, String>(2);
        params.put(StringConstant.CURRENT_PAGE_KEY, currentPage + "");
        params.put(StringConstant.PER_PAGE_KEY, StringConstant.PER_PAGE_COUNT + "");
        RequestInfo info = new RequestInfo(StringConstant.SERVER_NEWPOST_URL, params);
        httpTools.post(info, new HttpCallback() {
            @Override
            public void onStart() {
                isLoading = true;
                currentPage++;
            }

            @Override
            public void onFinish() {
                // 一共加载多少条
            }

            @Override
            public void onResult(String s) {
                System.out.println("Post Result >> " + s);

                if (StringConstant.NULL_VALUE.equals(s)) {
//                    Snackbar.make()
                    return;
                }

                onReadJson(s);
                isLoading = false;
            }

            @Override
            public void onError(Exception e) {
                currentPage--;
            }

            @Override
            public void onCancelled() {
                currentPage--;
            }

            @Override
            public void onLoading(long l, long l1) {

            }
        });
    }

    private void onReadJson(String json) {
        try {
            Log.i(TAG, json);
            JSONArray jsonArray = new JSONArray(json);

            postRVAdapter.notifyItemChanged(postList.size());

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "解析Json出错");
        }
    }




    /**
     * floatingActionButton 进入动画
     */
    private static final int ANIM_DURATION_FAB = 400;

    private void startContentAnimation() {

        // 网络检查
        if (!Utils.checkNetConnection(getApplicationContext())) {
            Snackbar.make(clContainer, "少年呦 你联网了嘛", Snackbar.LENGTH_SHORT).show();
        }

        //loadPosts();
        rvPost.setAdapter(postRVAdapter);
        isLoading = false;
    }


    /**
     * 点击帖子中的头像后，进入个人信息界面
     *
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
     *
     * @param v
     * @param position
     */
    @Override
    public void onCommentsClick(View v, int position) {

        Toast.makeText(NewPostActivity.this, "点击了评论", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onLikeClick(View v, int position) {
        Toast.makeText(NewPostActivity.this, "赞一下~(≧▽≦)~啦啦啦", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onMoreClick(View v, int position) {
        Toast.makeText(NewPostActivity.this, "更多", Toast.LENGTH_LONG).show();
    }

}