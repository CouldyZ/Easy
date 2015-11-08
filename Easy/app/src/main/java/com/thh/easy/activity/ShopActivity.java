package com.thh.easy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.android.volley.ext.HttpCallback;
import com.android.volley.ext.RequestInfo;
import com.android.volley.ext.tools.HttpTools;
import com.thh.easy.R;
import com.thh.easy.adapter.MyPagerAdapter;
import com.thh.easy.adapter.OrderRVAdapter;
import com.thh.easy.adapter.ShopRVAdpter;
import com.thh.easy.constant.StringConstant;
import com.thh.easy.entity.Shop;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

/**
 * 浏览商店页面
 */
public class ShopActivity extends BaseDrawerActivity implements
        BaseDrawerActivity.OnStartActivityListener,
        ShopRVAdpter.OnShopItemClickListener{

    private static final String TAG = "ShopActivity";

    @Bind(R.id.tl_shop_tabs)
    TabLayout tlShopTabs;                        // tab指示器

    @Bind(R.id.cl_main_container)
    CoordinatorLayout clContainer;

    @Bind(R.id.vp_shop_views)
    ViewPager vpShopViewPager;                   // activity内容
    MyPagerAdapter myPagerAdapter;              // adapter

    String[] mTabTitles;                           // tab上的标题
    List<View> mViewList = new ArrayList<>();   // tab对应的view

    LayoutInflater mInflater;                   // 视图加载
    View llShopView,llOrderView;                  // viewPager中的两个view

    RecyclerView rvShop, rvOrder;                 // 商店列表，我的订单列表
    ShopRVAdpter shopRVAdpter;                    // rvShop的适配器
    OrderRVAdapter orderRVAdapter;                //  rvOrder的适配器

    boolean isLoading = false;
    private List<Shop> shopList = new ArrayList<>();

    int currentPage = 1;                        // 当前页

    HttpTools httpTools;                        // 网络操作工具

    int currentViewPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        HttpTools.init(this);
        httpTools = new HttpTools(this);

        setOnStartActivityListener(this);

        loadShops();

        setUpPagerData();
        setUpTabData();
        setUpRecyclerViews();

    }


    /**
     * 请求帖子数据
     */
    private void loadShops() {
        if (shopRVAdpter == null) {
            shopRVAdpter = new ShopRVAdpter(ShopActivity.this, shopList);
        }

        // 向服务器发送数据
        Map<String, String> params = new HashMap<String, String>(2);
        params.put(StringConstant.CURRENT_PAGE_KEY, currentPage + "");
        params.put(StringConstant.PER_PAGE_KEY, StringConstant.PER_PAGE_COUNT + "");

        RequestInfo info = new RequestInfo(StringConstant.SERVER_SHOP_URL, params);
        httpTools.post(info, new HttpCallback() {
            @Override
            public void onStart() {
                isLoading = true;
                Log.i("New - HttpCallback", "当前页" + currentPage);
            }

            @Override
            public void onFinish() {
                // 一共加载多少条
                isLoading = false;
            }

            @Override
            public void onResult(String s) {
                Log.i("New - HttpCallback", s);

                if (StringConstant.NULL_VALUE.equals(s)) {
                    Snackbar.make(clContainer, "网络貌似粗错了", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if ("[]".equals(s)) {
                    Snackbar.make(clContainer, "什么帖子都没有呦", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                onReadJson(s);
                Log.d("New - HttpCallback", shopRVAdpter.getItemCount() + " loadPost");

                currentPage++;
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

    public void onReadJson(String json) {

        int insertPos = shopRVAdpter.getItemCount();
        try {
            JSONArray jsonArray = new JSONArray(json);

            Log.i(TAG, jsonArray.length()+" onReadJson");
            Shop shop = null;
            for (int i = 0 ; i < jsonArray.length() ; i ++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                JSONObject imageObj = jsonObject.getJSONObject("image");

                Log.e(TAG, imageObj.getString("urls"));

                shop= new Shop(jsonObject.getInt("id"),
                        imageObj.getString("urls"),
                        jsonObject.getString("name"),
                        jsonObject.getString("address"),
                        jsonObject.getString("phone"),
                        jsonObject.getString("shortcut"));

                shopList.add(shop);
            }

            shopRVAdpter.notifyItemRangeInserted(insertPos, shopList.size() - insertPos);
            shopRVAdpter.notifyItemRangeChanged(insertPos, shopList.size() - insertPos);

            currentPage++;

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "解析Json出错");
        }
    }


    /**
     * 初始化viewpager数据
     */
    private void setUpPagerData() {
        mInflater = LayoutInflater.from(this);
        mTabTitles = new String[]{"商店","订单"}; // tab上的文字
        // 所有商店列表
        llShopView = mInflater.inflate(R.layout.shop_all_view, null);
        rvShop = (RecyclerView)llShopView.findViewById(R.id.rv_shop);
        mViewList.add(llShopView);

        // 订单列表
        llOrderView = mInflater.inflate(R.layout.shop_order_view, null);
        rvOrder = (RecyclerView)llOrderView.findViewById(R.id.rv_order);
        mViewList.add(llOrderView);
        myPagerAdapter = new MyPagerAdapter(mViewList, mTabTitles);
    }

    /**
     * 设置Tab数据
     */
    private void setUpTabData() {
        tlShopTabs.setTabMode(TabLayout.MODE_FIXED);//设置tab模式，当前为系统默认模式
        tlShopTabs.addTab(tlShopTabs.newTab().setText(mTabTitles[0]));//添加tab选项卡
        tlShopTabs.addTab(tlShopTabs.newTab().setText(mTabTitles[1]));

        vpShopViewPager.setAdapter(myPagerAdapter);//给ViewPager设置适配器
        tlShopTabs.setupWithViewPager(vpShopViewPager);//将TabLayout和ViewPager关联起来。
        tlShopTabs.setTabsFromPagerAdapter(myPagerAdapter);//给Tabs设置适配器

    }


    /**
     * 初始化recyleview
     *  数据见ShopRVAdapter
     */
    public void setUpRecyclerViews() {
        LinearLayoutManager shopLinearLayoutManager = new LinearLayoutManager(this);
        rvShop.setLayoutManager(shopLinearLayoutManager);
        rvShop.setHasFixedSize(true);
        rvShop.setAdapter(shopRVAdpter);

        // 添加item点击事件
        shopRVAdpter.setOnPostItemClickListener(this);

        if(rvOrder == null){
            orderRVAdapter = new OrderRVAdapter(this);
        }

        LinearLayoutManager orderLinearLayoutManager = new LinearLayoutManager(this);
        rvOrder.setLayoutManager(orderLinearLayoutManager);
        rvOrder.setHasFixedSize(true);
        orderRVAdapter = new OrderRVAdapter(this);
        rvOrder.setAdapter(orderRVAdapter);

    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(0, 0);
    }

    @Override
    public void onStartActivity(Class<?> targetActivity) {
        if (ShopActivity.class == targetActivity)
            return;

        if (targetActivity == MainActivity.class) {
            finish();
            overridePendingTransition(0, 0);
            return;
        }

        Intent intent = new Intent(ShopActivity.this, targetActivity);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
        overridePendingTransition(0, 0);
    }


    /**
     * 商店项的点击事件,跳到商品详情界面
     * @param view
     * @param position
     */
    @Override
    public void onItemClick(View view, int position) {
        // TODO 动画， 网络
        Shop shop = shopList.get(position);
        Intent intent = new Intent(this, GoodsActivity.class);
        intent.putExtra("SHOP_ID", shop.getId());

        intent.putExtra("SHOP_NAME", ""+shop.getName());
        intent.putExtra("SHOP_URL", ""+shop.getUrl());

        intent.putExtra("SHOP_SHORTCUT",shop.getShortcut());
        intent.putExtra("SHOP_ADDRESS",shop.getAddress());

        startActivity(intent);
    }


}
