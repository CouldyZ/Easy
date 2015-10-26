package com.thh.easy.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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

    @Override
    public void setContentView(int layoutResID) {
        super.setContentViewWithoutInject(R.layout.drawer_view);
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.flContentRoot);
        LayoutInflater.from(this).inflate(layoutResID, viewGroup, true);
        injectViews();

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
        drawerLayout.closeDrawer(Gravity.LEFT);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                int[] startingLocation = new int[2];
                v.getLocationOnScreen(startingLocation);
                startingLocation[0] += v.getWidth() / 2;
                UserProfileActivity.startUserProfileFromLocation(startingLocation, BaseDrawerActivity.this);
                overridePendingTransition(0, 0);
            }
        }, 200);
    }

    @OnClick(R.id.ivMenuUserProfilePhoto)
    void onPhotoClick() {
        startActivity(new Intent(BaseDrawerActivity.this, LoginActivity.class));
    }
}
