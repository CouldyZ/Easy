package com.thh.easy.activity;

import android.os.Bundle;
import android.view.View;

import com.thh.easy.R;

/**
 * 活动详情界面
 */
public class ActDetailActivity extends BaseDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_detail);

        // 将打开侧滑栏的按钮设置成返回按钮
        getToolbar().setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

}
