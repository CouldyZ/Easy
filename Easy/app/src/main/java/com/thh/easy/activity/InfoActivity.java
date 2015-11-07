package com.thh.easy.activity;

import android.content.Intent;
import android.os.Bundle;

import com.thh.easy.R;

/**
 * 关于界面：
 *  icon + 项目名 + 版本号 + 小组成员
 */
public class InfoActivity extends BaseDrawerActivity implements BaseDrawerActivity.OnStartActivityListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        setOnStartActivityListener(this);
    }

    /**
     * 控制返回跳到首页
     * @param targetActivity
     */
    @Override
    public void onStartActivity(Class<?> targetActivity) {

        if (InfoActivity.class == targetActivity)
            return;

        if (targetActivity == MainActivity.class) {
            finish();
            overridePendingTransition(0, 0);
            return;
        }

        Intent intent = new Intent(InfoActivity.this, targetActivity);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
        overridePendingTransition(0, 0);

    }

}
