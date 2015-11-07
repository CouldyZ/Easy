package com.thh.easy.activity;

import android.content.Intent;
import android.os.Bundle;

import com.thh.easy.R;

import butterknife.OnClick;

public class SetActivity extends BaseDrawerActivity implements BaseDrawerActivity.OnStartActivityListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        setOnStartActivityListener(this);
    }

    /**
     * 控制返回跳到首页
     * @param targetActivity
     */
    @Override
    public void onStartActivity(Class<?> targetActivity) {

        if (SetActivity.class == targetActivity)
            return;

        if (targetActivity == MainActivity.class) {
            finish();
            overridePendingTransition(0, 0);
            return;
        }

        Intent intent = new Intent(SetActivity.this, targetActivity);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
        overridePendingTransition(0, 0);

    }

    /**
     * 退出应用
     */
    @OnClick(R.id.btn_set_comfirm)
    void onClickExit(){
       // System.exit(0);
      // BaseActivity.exit();
        BaseDrawerActivity.exit();
    }
}
