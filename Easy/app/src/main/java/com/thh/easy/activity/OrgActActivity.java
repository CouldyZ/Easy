package com.thh.easy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.ext.HttpCallback;
import com.android.volley.ext.RequestInfo;
import com.android.volley.ext.tools.HttpTools;
import com.thh.easy.R;
import com.thh.easy.constant.StringConstant;
import com.thh.easy.util.Utils;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrgActActivity extends AppCompatActivity implements TextWatcher , BaseDrawerActivity.OnStartActivityListener{

    @Bind(R.id.et_org_act_theme)
    EditText etOrgActTheme;       // 活动主题

    @Bind(R.id.et_org_act_conent)
    EditText etOrgActConent;      // 活动内容

    @Bind(R.id.et_org_act_time)
    EditText etOrgActTime;        // 活动开始时间

    @Bind(R.id.et_org_act_end_time)
    EditText etOrgActEndTime;     // 活动结束时间

    @Bind(R.id.et_org_act_money)
    EditText etOrgActMoney;       // 活动经费

    @Bind(R.id.et_org_act_people)
    EditText etOrgActPeople;      // 活动人数

    @Bind(R.id.btn_org_comfirm)
    Button btnComfirm;            // 发起活动


    @Bind(R.id.cl_reg_container)
    CoordinatorLayout clContainer;

    @Bind(R.id.tv_back_text)
    TextView tvTitle;

    @Bind(R.id.iv_back_logo)
    ImageView ivLogo;

    HttpTools httpTools;
    int userId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_org);
        ButterKnife.bind(OrgActActivity.this);

        // 初始化Volley
        HttpTools.init(getApplicationContext());
        httpTools = new HttpTools(getApplicationContext());
        userId = Utils.getUserId(OrgActActivity.this);

        tvTitle.setText("发起活动");
        ivLogo.setVisibility(View.GONE);

        etOrgActTheme.addTextChangedListener(this);
        etOrgActConent.addTextChangedListener(this);
        etOrgActTime.addTextChangedListener(this);
        etOrgActMoney.addTextChangedListener(this);
        etOrgActPeople.addTextChangedListener(this);
        etOrgActEndTime.addTextChangedListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(0, 0);
    }

    @OnClick(R.id.iv_back)
    void onBack() {
        finish();
    }



    /**
     * 点击发起活动按钮
     */
    @OnClick(R.id.btn_org_comfirm)
    void onClickOrgActivity() {

        Map<String, String> params = new HashMap<>();

        params.put("act.users.id", "" + userId);
        params.put("act.theme",  orgActTheme);
        params.put("act.contents", orgActConent);
        params.put("act.startDate", orgActTime);
        params.put("act.pay", orgActMoney);
        params.put("act.account", orgActPeople);
        params.put("act.days", orgEndTime);

        RequestInfo info = new RequestInfo(StringConstant.SERVER_ORG_URL, params);
        httpTools.post(info, new HttpCallback() {
            @Override
            public void onStart() {
            }

            @Override
            public void onFinish() {
            }

            @Override
            public void onResult(String s) {

                if (StringConstant.NULL_VALUE.equals(s)) {
                    Snackbar.make(clContainer, "网络貌似粗错了", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if("1".equals(s)){
                    Snackbar.make(clContainer, "发起活动成功", Snackbar.LENGTH_SHORT).show();
                    startActivity(new Intent(OrgActActivity.this, ActActivity.class));
                } else {
                    Snackbar.make(clContainer, "发起活动失败", Snackbar.LENGTH_SHORT).show();
                }



            }

            @Override
            public void onError(Exception e) {
                Snackbar.make(clContainer, "网络貌似粗错了", Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled() {
            }

            @Override
            public void onLoading(long l, long l1) {

            }
        });


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

        Intent intent = new Intent(OrgActActivity.this, targetActivity);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
        overridePendingTransition(0, 0);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    String orgActTheme;
    String orgActConent;
    String orgActTime;
    String orgActMoney;
    String orgActPeople;
    String orgEndTime;


    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        orgActTheme = etOrgActTheme.getText().toString();   // 活动主题
        orgActConent = etOrgActConent.getText().toString(); // 活动内容
        orgActTime = etOrgActTime.getText().toString();     // 活动时间
        orgActMoney = etOrgActMoney.getText().toString();   // 活动经费
        orgActPeople = etOrgActPeople.getText().toString(); // 活动人数
        orgEndTime = etOrgActEndTime.getText().toString();  // 活动结束时间

        if (!TextUtils.isEmpty(orgActTheme) && !TextUtils.isEmpty(orgActConent)
                && !TextUtils.isEmpty(orgActTime) && !TextUtils.isEmpty(orgActMoney)
                && !TextUtils.isEmpty(orgActPeople)) {
            btnComfirm.setEnabled(true);
        }
        else
        {
            btnComfirm.setEnabled(false);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
