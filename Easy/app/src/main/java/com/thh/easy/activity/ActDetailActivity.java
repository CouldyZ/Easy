package com.thh.easy.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.ext.HttpCallback;
import com.android.volley.ext.RequestInfo;
import com.android.volley.ext.tools.HttpTools;
import com.squareup.picasso.Picasso;
import com.thh.easy.R;
import com.thh.easy.constant.StringConstant;
import com.thh.easy.entity.Activities;
import com.thh.easy.util.LogUtil;
import com.thh.easy.util.RoundedTransformation;
import com.thh.easy.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 活动详情界面
 * 1. 请求活动详情
 * 2. 参加活动
 * // TODO sankerbar 位置不对
 */
public class ActDetailActivity extends BaseDrawerActivity {

    @Bind(R.id.tv_act_detail_theme)
    TextView actTheme;               // 活动主题

    @Bind(R.id.tv_act_detail_content)
    TextView actContent;             // 活动内容

    @Bind(R.id.iv_org_user_image)
    ImageView ivUserAvater;          // 用户头像

    @Bind(R.id.iv_act_detail_user_name)
    TextView tvUserName;             // 用户名字

    @Bind(R.id.tv_act_detail_date)
    TextView tvActDate;              // 活动日期

    @Bind(R.id.tv_act_detail_count)
    TextView tvActCount;             // 预计参加人数

    @Bind(R.id.tv_act_detail_joined)
    TextView tvActJoined;            // 已参加人数

    @Bind(R.id.tv_act_detail_pay)
    TextView tvActPay;               // 活动费用

    @Bind(R.id.ll_container)
    LinearLayout ll_container;       // snakerbar


    /**
     * 设置数据
     */
    private void setViewData(Activities activities){

        LogUtil.d(ActDetailActivity.this, " 设置数据开始啦： " + activities.getContent());
        actTheme.setText(activities.getTheme());
        actContent.setText(activities.getContent());
        tvUserName.setText(activities.getUserName());
        tvActDate.setText(activities.getStartDate()+"~"+activities.getEndDate());
        tvActCount.setText(""+activities.getAccount());
        tvActJoined.setText(""+activities.getParticiCount());
        tvActPay.setText(" "+activities.getPay()+"/ 人");

        // 加载头像
        if (activities.getUserImage() != null) {

            Picasso.with(ActDetailActivity.this)
                    .load(StringConstant.SERVER_IP + activities.getUserImage())
                    .centerCrop()
                    .resize(avatarSize, avatarSize)
                    .transform(new RoundedTransformation())
                    .placeholder(R.mipmap.bili_default_avatar)
                    .into(ivUserAvater);
        }


    }
    HttpTools httpTools;
    int userId = 0;
    String actId = "0";
    int orgUserId = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_detail);
        loadInit();
        loadActivity();

    }



    private void loadInit() {

        userId = Utils.getUserId(ActDetailActivity.this);
        actId = getIntent().getStringExtra(StringConstant.ACT_ID);

        // 初始化Volley
        HttpTools.init(getApplicationContext());
        httpTools = new HttpTools(getApplicationContext());

        // 将打开侧滑栏的按钮设置成返回按钮
        getToolbar().setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }


    /**
     * 查看活动详情
     */
    private void loadActivity() {

           // 向服务器发送数据 act.id=1
            Map<String, String> params = new HashMap<String, String>(1);
            params.put(StringConstant.ACT_ID, "" + actId);

            RequestInfo info = new RequestInfo(StringConstant.SERVER__ACT_DETAIL_URL, params);
            httpTools.post(info, new HttpCallback() {
                @Override
                public void onStart() {
                }

                @Override
                public void onFinish() {
                }

                @Override
                public void onResult(String s) {

                    LogUtil.i(ActDetailActivity.this, "查看活动详情：服务器返回数据啦：　ｓ：" + s);

                    if (StringConstant.NULL_VALUE.equals(s)) {
                        Snackbar.make(ll_container, "网络貌似粗错了", Snackbar.LENGTH_SHORT).show();
                        return;
                    }

                    readActJson(s);


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
     * 读取返回的单个活动json详情
     * @param s
     */
    private void readActJson(String s){
        try {
            JSONObject jsonObject = new JSONObject(s);
            Activities activities = new Activities();
            activities.setTheme(
                    jsonObject.getString("theme"));
            activities.setUid(jsonObject.getInt("user.id"));
            activities.setUserRP(jsonObject.getInt("user.rp"));
            activities.setContent(jsonObject.getString("contents"));
            activities.setUserName(jsonObject.getString("user.name"));
            activities.setAccount(jsonObject.getInt("account"));
            activities.setParticiCount(jsonObject.getInt("particiCount"));
            activities.setReportCount(jsonObject.getInt("reportCount"));
            activities.setStartDate(jsonObject.getString("start_date"));
            activities.setEndDate(jsonObject.getString("end_date"));
            activities.setUserImage(jsonObject.getString("user_img"));
            activities.setPay("" + jsonObject.getDouble("pay"));
            setViewData(activities);
        }catch (JSONException e) {
            e.printStackTrace();
            LogUtil.e(ActDetailActivity.this, " 解析onReadORGJson出错");
        }
    }

    @OnClick(R.id.btn_join_act)
    void onJoinClick(){
        if(userId == 0) {
            Snackbar.make(ll_container, "请登录后，再参加活动", Snackbar.LENGTH_SHORT).show();
            return;
        }

        postJoin();
    }

    /**
     * 参加活动
     */
    private void postJoin() {


        // 向服务器发送数据 act.id=6 &users.id=1
        Map<String, String> params = new HashMap<String, String>(1);
        params.put(StringConstant.ACT_ID, "" + actId);
        params.put(StringConstant.COMMENT_UID, ""+
                // + 3);
                userId);

        RequestInfo info = new RequestInfo(StringConstant.SERVER_JOIN_ACT_URL, params);
        httpTools.post(info, new HttpCallback() {
            @Override
            public void onStart() {
            }

            @Override
            public void onFinish() {
            }

            @Override
            public void onResult(String s) {

                LogUtil.i(ActDetailActivity.this, "服务器返回数据啦：　ｓ：" + s);

                if (StringConstant.NULL_VALUE.equals(s)) {
                    Snackbar.make(ll_container, "网络貌似粗错了", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if ("1".equals(s)) {
                    Snackbar.make(ll_container, "参加成功", Snackbar.LENGTH_SHORT).show();
                    onBackPressed();
                } else {
                    Snackbar.make(ll_container, "参加失败", Snackbar.LENGTH_SHORT).show();
                }


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

    @OnClick(R.id.ll_report)
    void onReportAct(){
        if(userId == 0) {
            Snackbar.make(ll_container, "请登录后，再举报活动", Snackbar.LENGTH_SHORT).show();
            return;
        }

        postReport();
    }

    int orgUderRP = 0;

    /**
     * 举报活动
     */
    private void postReport() {

        // 向服务器发送数据 act.id=6&users.id=1&act.users.rp=70
        Map<String, String> params = new HashMap<String, String>(1);
        params.put(StringConstant.ACT_ID, "" + actId);
        params.put(StringConstant.COMMENT_UID, "" + userId);
        params.put(StringConstant.ACT_USER_RP, "" + orgUderRP);

        RequestInfo info = new RequestInfo(StringConstant.SERVER_REPORT_ACT_URL, params);
        httpTools.post(info, new HttpCallback() {
            @Override
            public void onStart() {
            }

            @Override
            public void onFinish() {
            }

            @Override
            public void onResult(String s) {

                LogUtil.i(ActDetailActivity.this, "服务器返回数据啦：　ｓ：" + s);

                if (StringConstant.NULL_VALUE.equals(s)) {
                    Snackbar.make(ll_container, "网络貌似粗错了", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if ("1".equals(s)) {
                    Snackbar.make(ll_container, "举报成功", Snackbar.LENGTH_SHORT).show();
                } else {
                    Snackbar.make(ll_container, "举报失败", Snackbar.LENGTH_SHORT).show();
                }


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
     * 点击头像，进入个人信息界面
     */
    @OnClick(R.id.iv_org_user_image)
    void onUserAvaterClick(View view){
        // 检查联网
        if (!Utils.checkNetConnection(getApplicationContext())) {
            Snackbar.make(ll_container, "少年呦 你联网了嘛?", Snackbar.LENGTH_SHORT).show();
        }

        // 获得点击头像时的位置
        int[] startingLocation = new int[2];
        view.getLocationOnScreen(startingLocation);
        startingLocation[0] += view.getWidth() / 2;

        // 进入用户信息界面时，设置进入动画
        UserProfileActivity.startUserProfileFromLocation(orgUserId,
                startingLocation, this);

        LogUtil.d(ActDetailActivity.this, "orgUserId: " + orgUserId);
        overridePendingTransition(0, 0);
    }




}
