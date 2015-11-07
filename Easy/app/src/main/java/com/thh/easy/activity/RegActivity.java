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
import com.thh.easy.constant.StringConstant;
import com.thh.easy.R;
import com.thh.easy.util.StringUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by taCi on 2015/10/26.
 */
public class RegActivity extends AppCompatActivity implements TextWatcher{

    @Bind(R.id.et_reg_username)
    EditText etUsername;

    @Bind(R.id.et_reg_pwd)
    EditText etPwd;

    @Bind(R.id.et_reg_repwd)
    EditText etRepwd;

    @Bind(R.id.et_reg_nickname)
    EditText etNickname;

    @Bind(R.id.et_reg_tname)
    EditText etTname;

    @Bind(R.id.btn_reg_comfirm)
    Button btnComfirm;

    @Bind(R.id.iv_back)
    ImageView ivBack;

    @Bind(R.id.cl_reg_container)
    CoordinatorLayout clContainer;

    @Bind(R.id.tv_back_text)
    TextView tvTitle;

    @Bind(R.id.iv_back_logo)
    ImageView ivLogo;

    HttpTools httpTools;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        ButterKnife.bind(RegActivity.this);

        tvTitle.setText("注册");
        ivLogo.setVisibility(View.GONE);

        HttpTools.init(this);
        httpTools = new HttpTools(this);

        etUsername.addTextChangedListener(this);
        etPwd.addTextChangedListener(this);
        etRepwd.addTextChangedListener(this);
        etNickname.addTextChangedListener(this);
        etTname.addTextChangedListener(this);
    }

    @OnClick(R.id.iv_back)
    void onBack() {
        finish();
    }

    @OnClick(R.id.btn_reg_comfirm)
    void onReg() {
        final String username = etUsername.getText().toString();
        String pwd = etPwd.getText().toString();
        String repwd = etRepwd.getText().toString();
        String nickname = etNickname.getText().toString();
        String tname = etTname.getText().toString();

        if (!pwd.equals(repwd)) {
            Snackbar.make(clContainer, "少年呦 两次密码不一样呦", Snackbar.LENGTH_SHORT).show();
            return;
        }

        if (StringUtil.checkString(username) && StringUtil.checkString(pwd)) {
            Map<String, String> params = new HashMap<>(4);
            params.put(StringConstant.USER_NAME, username);
            params.put(StringConstant.USER_PWD, pwd);
            params.put(StringConstant.USER_NICKNAME, nickname);
            params.put(StringConstant.USER_TNAME, tname);
            RequestInfo info = new RequestInfo(StringConstant.SERVER_REG_URL, params);
            httpTools.post(info, new HttpCallback() {
                @Override
                public void onStart() {

                }

                @Override
                public void onFinish() {

                }

                @Override
                public void onResult(String s) {
                    if (StringConstant.FAIL.equals(s))
                        Snackbar.make(clContainer, "少年呦 注册失败了呢", Snackbar.LENGTH_SHORT).show();
                    else if (StringConstant.SUCCESS.equals(s)) {
                        // 将账号通过广播传给LoginActivity 并finish当前Activity
                        Intent intent = new Intent("REG_SUCCESS");
                        intent.putExtra("username", username);
                        setResult(RESULT_OK, intent);
                        finish();
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
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        String username = etUsername.getText().toString();
        String pwd = etPwd.getText().toString();
        String repwd = etRepwd.getText().toString();
        String nickname = etNickname.getText().toString();
        String tname = etTname.getText().toString();

        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(pwd) && !TextUtils.isEmpty(repwd) && !TextUtils.isEmpty(nickname) && !TextUtils.isEmpty(tname)) {
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
