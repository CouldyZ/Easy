package com.thh.easy.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.ext.HttpCallback;
import com.android.volley.ext.RequestInfo;
import com.android.volley.ext.tools.HttpTools;
import com.squareup.picasso.Picasso;
import com.thh.easy.Constant.StringConstant;
import com.thh.easy.R;
import com.thh.easy.entity.User;
import com.thh.easy.util.StringUtil;
import com.thh.easy.util.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 用户登录Activity
 *
 * @author cky
 */
public class LoginActivity extends AppCompatActivity {

    private final static String TAG = "LoginActivity";

    private int[] biliNormal = {R.mipmap.ic_22, R.mipmap.ic_33};
    private int[] biliHide = {R.mipmap.ic_22_hide, R.mipmap.ic_33_hide};

    private int[] usernameDrawable = {R.mipmap.ic_login_username_hover,
            R.mipmap.ic_login_username_default};
    private int[] passwordDrawable = {R.mipmap.ic_login_password_hover,
            R.mipmap.ic_login_password_default};

    private int lineColorPink = R.color.easy_pink_light;
    private int lineColorDark = R.color.easy_dark;

    @Bind(R.id.iv_login_22)
    ImageView ivBili22;

    @Bind(R.id.iv_login_33)
    ImageView ivBili33;

    @Bind(R.id.view_login_login_username_line)
    View viewUsername;

    @Bind(R.id.view_login_login_password_line)
    View viewPassword;

    @Bind(R.id.iv_login_username)
    ImageView ivUsername;

    @Bind(R.id.iv_login_password)
    ImageView ivPassword;

    @Bind(R.id.et_login_username)
    EditText etUsername;

    @Bind(R.id.et_login_password)
    EditText etPassword;

    @Bind(R.id.iv_back)
    ImageView ivBack;

    @Bind(R.id.btn_login_reg)
    Button btnReg;

    @Bind(R.id.btn_login)
    Button btnLogin;

    @Bind(R.id.cl_login_container)
    CoordinatorLayout clContainer;

    @Bind(R.id.tv_back_text)
    TextView tvTitle;

    @Bind(R.id.iv_back_logo)
    ImageView ivLogo;

    HttpTools httpTools;

    SharedPreferences sharedPreferences;

    final int REQUEST_CODE = 666;

    String filePath = null;

    String avatarFileName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        tvTitle.setText("登陆");
        ivLogo.setVisibility(View.GONE);
        sharedPreferences = getSharedPreferences("user_sp", Context.MODE_PRIVATE);

        etPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 22 33 娘
                    ivBili22.setBackground(getResources().getDrawable(biliHide[0], null));
                    ivBili33.setBackground(getResources().getDrawable(biliHide[1], null));

                    // 用户名部分UI
                    ivUsername.setBackground(getResources().getDrawable(usernameDrawable[1], null));
                    viewUsername.setBackgroundColor(getResources().getColor(lineColorDark));

                    // 密码部分UI
                    ivPassword.setBackground(getResources().getDrawable(passwordDrawable[0], null));
                    viewPassword.setBackgroundColor(getResources().getColor(lineColorPink));
                } else {
                    // 22 33 娘
                    ivBili22.setBackground(getResources().getDrawable(biliNormal[0], null));
                    ivBili33.setBackground(getResources().getDrawable(biliNormal[1], null));

                    // 用户名部分UI
                    ivUsername.setBackground(getResources().getDrawable(usernameDrawable[0], null));
                    viewUsername.setBackgroundColor(getResources().getColor(lineColorPink));

                    // 密码部分UI
                    ivPassword.setBackground(getResources().getDrawable(passwordDrawable[1], null));
                    viewPassword.setBackgroundColor(getResources().getColor(lineColorDark));
                }
            }
        });

        if (!Utils.checkNetConnection(getApplicationContext())) {
            Snackbar.make(clContainer, "少年呦 你联网了嘛?", Snackbar.LENGTH_LONG).show();
        }

        // 初始化Volley
        HttpTools.init(getApplicationContext());
        httpTools = new HttpTools(getApplicationContext());

        // 创建头像缓存文件夹
        filePath = getExternalFilesDir(null).getPath();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            etUsername.setText(data.getStringExtra("username"));
            Snackbar.make(clContainer, "注册成功 和我们签订契约吧", Snackbar.LENGTH_LONG).show();
        }
    }

    @OnClick(R.id.iv_back)
    void onBackClick() {
        // 返回主界面
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

    @OnClick(R.id.btn_login_reg)
    void onReg() {
        startActivityForResult(new Intent(LoginActivity.this, RegActivity.class), REQUEST_CODE);
    }

    /**
     * 登陆到主界面
     */
    @OnClick(R.id.btn_login)
    void onLogin() {
        // 隐藏软键盘
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        imm.hideSoftInputFromWindow(etUsername.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(etPassword.getWindowToken(), 0);

        if (TextUtils.isEmpty(etUsername.getText().toString()) || TextUtils.isEmpty(etPassword.getText().toString())) {
            Snackbar.make(clContainer, "少年呦 你是忘记写账号了还是密码呢?", Snackbar.LENGTH_LONG).show();
        } else {
            String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();

            // 检测账号密码是否合法
            if (StringUtil.checkString(username) && StringUtil.checkString(password)) {
                Map<String, String> params = new HashMap<String, String>(2);
                params.put(StringConstant.USER_NAME, username);
                params.put(StringConstant.USER_PWD, password);
                RequestInfo info = new RequestInfo(StringConstant.SERVER_LOGIN_URL, params);
                httpTools.post(info, new HttpCallback() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onFinish() {

                    }

                    @Override
                    public void onResult(String s) {
                        // 服务端返回Json
                        Log.i(TAG, s);
                        if ("null".equals(s)) {
                            Snackbar.make(clContainer, "少年呦 账号或密码不对", Snackbar.LENGTH_LONG).show();
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

            } else {
                Snackbar.make(clContainer, "少年呦 账号和密码要符合长度要求呦", Snackbar.LENGTH_SHORT).show();
            }
        }

//        startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }

    private void onReadJson(String jsonResult) {
        try {


            String iconUrl = null;

            JSONObject jsonObject = new JSONObject(jsonResult);
            if (!jsonObject.isNull("image"))
                iconUrl = jsonObject.getString("image");

            String email = null;
            if (!jsonObject.isNull("email"))
                email = jsonObject.getString("email");

            String gender = null;
            if (!jsonObject.isNull("gender"))
                gender = jsonObject.getString("gender");

            String unmber = null;
            if (!jsonObject.isNull("numbers"))
                unmber = jsonObject.getString("numbers");

            String depart = null;
            if (!jsonObject.isNull("depart"))
                depart = jsonObject.getString("depart");

            String tname = null;
            if (!jsonObject.isNull("tname"))
                tname = jsonObject.getString("tname");

            String avatar = null;
            if (!jsonObject.isNull("image")) {
                avatar = jsonObject.getString("image");
                avatarFileName = jsonObject.getString("name") + jsonObject.getInt("id") + ".png";
                System.out.println("头像路径 - " + filePath + "/" + avatarFileName);
                // TODO 存储用户头像
                httpTools.download(avatar, filePath + "/" + avatarFileName, false, new HttpCallback() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onFinish() {
                        httpTools.quitDownloadQueue();
                    }

                    @Override
                    public void onResult(String s) {

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

            User user = new User (jsonObject.getInt("id"),
                    jsonObject.getString("name"),
                    jsonObject.getString("pwd"),
                    iconUrl,
                    email,
                    unmber,
                    depart,
                    tname,
                    jsonObject.getString("nickname"),
                    gender,
                    jsonObject.getInt("rp"),
                    avatarFileName);

            user.writeToCache(getApplicationContext());

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("user_login", true);
            editor.commit();

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("login_success", true);
            Bundle bundle = new Bundle();
            bundle.putSerializable("user", user);
            intent.putExtra("user", bundle);
            startActivity(intent);

            finish();

        } catch (JSONException e) {
            Log.e(TAG, "Json解析失败");
            e.printStackTrace();
        }
    }
}
