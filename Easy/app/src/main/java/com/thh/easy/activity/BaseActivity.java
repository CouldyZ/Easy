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
    Toolbar mainToolbar;        // 顶部toolbar

    @Bind(R.id.iv_logo)
    ImageView ivLogo;          // 顶部toolbar上的Logo

    private MenuItem inboxMenuItem;   // toolbar 最右边的menu项

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        injectViews();
    }

    /**
     * 绑定view
     */
    protected void injectViews(){
        ButterKnife.bind(this);
        setupToolbar();
    }

    public void setContentViewWithoutInject(int layoutResId) {
        super.setContentView(layoutResId);
    }

    /**
     * 初始化toolbar
     * 设置标题，打开侧滑栏的按钮
     */
    protected void setupToolbar() {
        if (mainToolbar != null) {
            mainToolbar.setTitle("");
            setSupportActionBar(mainToolbar);
            mainToolbar.setNavigationIcon(R.mipmap.ic_menu_white);
        }
    }


    /**
     * 设置toolbar上最右的menu按钮
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        inboxMenuItem = menu.findItem(R.id.action_search);  // 设置一个搜索
        inboxMenuItem.setActionView(R.layout.menu_item_view);
        return true;
    }

    public Toolbar getToolbar() {
        return mainToolbar;
    }

    public MenuItem getInboxMenuItem() {
        return inboxMenuItem;
    }

    /**
     * 设置toolbar标题
     * @param resId
     */
    public void setTitle(int resId) {
        mainToolbar.setTitle(resId);
    }

    /**
     * 设置toolbar标题
     * @param text
     */
    public void setTitle(CharSequence text) {
        mainToolbar.setTitle(text);
    }

    /**
     * 处理返回键
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public ImageView getIvLogo() {
        return ivLogo;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

}
