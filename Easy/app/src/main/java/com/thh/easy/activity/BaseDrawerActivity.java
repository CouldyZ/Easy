package com.thh.easy.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.thh.easy.R;

import butterknife.Bind;
import butterknife.OnClick;


public class BaseDrawerActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener{

    @Bind(R.id.drawerLayout)
    DrawerLayout drawerLayout;            // 侧滑栏根布局

    @Bind(R.id.ivMenuUserProfilePhoto)
    ImageView ivMenuUserProfilePhoto;     // 侧滑栏中的用户头像

    @Bind(R.id.vNavigation)
    NavigationView navigationView;        //  侧滑栏内容

    @Bind(R.id.tv_drawer_name)
    TextView username;                    // 侧滑栏头部 用户名

    @Bind(R.id.iv_drawer_sex)
    ImageView gender;                    // 侧滑栏头部 用户性别


    int avatarSize;                      // 用户头像的大小

    @Override
    public void setContentView(int layoutResID) {
        super.setContentViewWithoutInject(R.layout.drawer_view);
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.flContentRoot);
        LayoutInflater.from(this).inflate(layoutResID, viewGroup, true);

        injectViews(); // 绑定view

        avatarSize = getResources().getDimensionPixelSize(R.dimen.user_profile_avatar_size);
        navigationView.setNavigationItemSelectedListener(this);  // 设置侧滑栏菜单点击事件
    }


    /**
     *  点击toolbar最左边的menu，打开侧滑栏
     */
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


    /**
     *  点击用户头像
     */
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


    /**
     * 侧滑栏菜单点击跳转相应Activity
     * @param menuItem
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(final MenuItem menuItem) {

        int menuItemId = menuItem.getItemId();

        switch (menuItemId) {
            case R.id.menu_main:           // 跳转到首页
                drawerLayout.closeDrawer(Gravity.LEFT);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(BaseDrawerActivity.this, MainActivity.class));
                    }
                }, 10);
                break;
            case R.id.menu_order:           // 跳转到商品页面
                drawerLayout.closeDrawer(Gravity.LEFT);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(BaseDrawerActivity.this, ShopActivity.class));
                    }
                }, 10);
                break;
            case R.id.menu_act:           // 跳转到活动页面
                drawerLayout.closeDrawer(Gravity.LEFT);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(BaseDrawerActivity.this, ActActivity.class));
                    }
                }, 10);
                break;
        }

        return  true;
    }

}
