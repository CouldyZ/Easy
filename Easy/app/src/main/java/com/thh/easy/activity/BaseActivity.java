package com.thh.easy.activity;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.thh.easy.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BaseActivity extends AppCompatActivity {

    @Bind(R.id.main_toolbar)
    Toolbar mainToolbar;

    @Bind(R.id.iv_logo)
    ImageView ivLogo;


    private MenuItem inboxMenuItem;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        injectViews();
    }

    protected void injectViews() {
        ButterKnife.bind(this);
        setupToolbar();
    }

    public void setContentViewWithoutInject(int layoutResId) {
        super.setContentView(layoutResId);
    }

    protected void setupToolbar() {
        if (mainToolbar != null) {
            mainToolbar.setTitle("");
            setSupportActionBar(mainToolbar);
            mainToolbar.setNavigationIcon(R.mipmap.ic_menu_white);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        inboxMenuItem = menu.findItem(R.id.action_search);
        inboxMenuItem.setActionView(R.layout.menu_item_view);
        return true;
    }

    public Toolbar getToolbar() {
        return mainToolbar;
    }

    public MenuItem getInboxMenuItem() {
        return inboxMenuItem;
    }

    public ImageView getIvLogo() {
        return ivLogo;
    }
}
