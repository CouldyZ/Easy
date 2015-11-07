package com.thh.easy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.thh.easy.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrgActActivity extends AppCompatActivity implements TextWatcher , BaseDrawerActivity.OnStartActivityListener{

    @Bind(R.id.et_org_act_theme)
    EditText etOrgActTheme;       // 活动主题

    @Bind(R.id.et_org_act_conent)
    EditText etOrgActConent;      // 活动内容

    @Bind(R.id.et_org_act_time)
    EditText etOrgActTime;        // 活动时间

    @Bind(R.id.et_org_act_money)
    EditText etOrgActMoney;       // 活动经费

    @Bind(R.id.et_org_act_people)
    EditText etOrgActPeople;      // 活动人数

    @Bind(R.id.btn_org_comfirm)
    Button btnComfirm;            // 发起活动

    @Bind(R.id.iv_back)
    ImageView ivBack;

    @Bind(R.id.cl_reg_container)
    CoordinatorLayout clContainer;

    @Bind(R.id.tv_back_text)
    TextView tvTitle;

    @Bind(R.id.iv_back_logo)
    ImageView ivLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_org);
        ButterKnife.bind(OrgActActivity.this);

        tvTitle.setText("发起活动");
        ivLogo.setVisibility(View.GONE);

        etOrgActTheme.addTextChangedListener(this);
        etOrgActConent.addTextChangedListener(this);
        etOrgActTime.addTextChangedListener(this);
        etOrgActMoney.addTextChangedListener(this);
        etOrgActPeople.addTextChangedListener(this);

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
        // TODO 发起活动
     //   ActActivity.startMyAct(OrgActActivity.this);
        Toast.makeText(OrgActActivity.this, "发起活动了惹", Toast.LENGTH_SHORT).show();
        finish();

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

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        String orgActTheme = etOrgActTheme.getText().toString();   // 活动主题
        String orgActConent = etOrgActConent.getText().toString(); // 活动内容
        String orgActTime = etOrgActTime.getText().toString();     // 活动时间
        String orgActMoney = etOrgActMoney.getText().toString();   // 活动经费
        String orgActPeople = etOrgActPeople.getText().toString(); // 活动人数

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
