package com.thh.easy.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.thh.easy.R;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * Created by Miroslaw Stanek on 15.07.15.
 */
public class BaseDrawerActivity extends BaseActivity {

    @Bind(R.id.drawerLayout)
    DrawerLayout drawerLayout;

    @Bind(R.id.ivMenuUserProfilePhoto)
    ImageView ivMenuUserProfilePhoto;

    @Bind(R.id.tv_drawer_name)
    TextView username;

    @Bind(R.id.iv_drawer_sex)
    ImageView gender;

    int avatarSize;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentViewWithoutInject(R.layout.drawer_view);
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.flContentRoot);
        LayoutInflater.from(this).inflate(layoutResID, viewGroup, true);
        injectViews();
        avatarSize = getResources().getDimensionPixelSize(R.dimen.user_profile_avatar_size);
       // setupHeader();
    }

    @Override
    protected void setupToolbar() {
        super.setupToolbar();
        if (getToolbar() != null) {
            getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawerLayout.openDrawer(Gravity.LEFT);
                }
            });
        }
    }

    @OnClick(R.id.vGlobalMenuHeader)
    public void onGlobalMenuHeaderClick(final View v) {
    }

    // 点击用户头像
    @OnClick(R.id.ivMenuUserProfilePhoto)
    void onPhotoClick(final View v) {
        SharedPreferences sp = getSharedPreferences("user_sp", Context.MODE_PRIVATE);
        if (sp.getBoolean("user_login", false)) {
            drawerLayout.closeDrawer(Gravity.LEFT);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    int[] pos = new int[2];
                    v.getLocationOnScreen(pos);
                    pos[0] += v.getWidth() / 2;
                    UserProfileActivity.startUserProfileFromLocation(pos, BaseDrawerActivity.this);
                    overridePendingTransition(0, 0);
                }
            }, 200);
        }
        else
            startActivity(new Intent(BaseDrawerActivity.this, LoginActivity.class));
    }


}
