package com.thh.easy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.ext.HttpCallback;
import com.android.volley.ext.RequestInfo;
import com.android.volley.ext.tools.HttpTools;
import com.squareup.picasso.Picasso;
import com.thh.easy.R;
import com.thh.easy.adapter.GoodsRVAdapter;
import com.thh.easy.constant.StringConstant;
import com.thh.easy.entity.Goods;
import com.thh.easy.util.RoundedTransformation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

public class GoodsActivity extends BaseDrawerActivity {

    @Bind(R.id.rv_goods)
    RecyclerView rvGoods;

    GoodsRVAdapter goodsRVAdapter;

    boolean isLoading = false;
    private List<Goods> goodsList = new ArrayList<>();

    int currentPage = 1;                        // 当前页

    HttpTools httpTools;                        // 网络操作工具

    private int goodsId = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);

        HttpTools.init(this);
        httpTools = new HttpTools(this);
        // 设置商店信息
        goodsId = getIntent().getIntExtra("SHOP_ID",0);
        System.out.println("goodsId!!!!!!!"+ goodsId);
        setShopDetail();

        loadGoods();

        // 设置成返回
        getToolbar().setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        setupGoods();
    }


    /**
     * 请求帖子数据
     */
    private void loadGoods() {
        if (goodsRVAdapter == null) {
            goodsRVAdapter = new GoodsRVAdapter(GoodsActivity.this, goodsList);
        }

        // 向服务器发送数据
        Map<String, String> params = new HashMap<String, String>(3);
        params.put(StringConstant.CURRENT_PAGE_KEY, currentPage+"");
        params.put(StringConstant.PER_PAGE_KEY, StringConstant.PER_PAGE_COUNT + "");
        params.put(StringConstant.GOODS_ID,goodsId+"");

        RequestInfo info = new RequestInfo(StringConstant.SERVER_GOODS_URL, params);
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
                    return;
                }

                if ("[]".equals(s)) {
                    return;
                }
                onReadJson(s);
                Log.d("New - HttpCallback", goodsRVAdapter.getItemCount() + " loadPost");

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

    Goods goods= null;
    public void onReadJson(String json) {

        System.out.println("获得数据--》" + json);
        int insertPos = goodsRVAdapter.getItemCount();
        try {
            JSONArray jsonArray = new JSONArray(json);

            for (int i = 0 ; i < jsonArray.length() ; i ++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                JSONObject imageObj = jsonObject.getJSONObject("image");

                String price = ""+jsonObject.getDouble("price");
                String deposit = ""+jsonObject.getDouble("deposit");
                goods = new Goods(
                        jsonObject.getInt("id"),
                        jsonObject.getString("name"),
                        StringConstant.SERVER_IP + imageObj.getString("urls"),
                        Float.parseFloat(price),
                        Float.parseFloat(deposit)
                );
                goodsList.add(goods);
            }

            goodsRVAdapter.notifyItemRangeInserted(insertPos, goodsList.size() - insertPos);
            goodsRVAdapter.notifyItemRangeChanged(insertPos, goodsList.size() - insertPos);

            currentPage++;

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "解析Json出错");
        }
    }

    private static final String TAG = "GoodsAcitity";

    /**
     * 初始化商品列表
     */
    private void setupGoods() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvGoods.setLayoutManager(linearLayoutManager);
        rvGoods.setHasFixedSize(true);
        goodsRVAdapter = new GoodsRVAdapter(this, goodsList);
        rvGoods.setAdapter(goodsRVAdapter);
    }

    /**
     * 下订单，到订单页面
     */
    @OnClick(R.id.btn_new_order)
    void onClickToNewOrder(){
       Intent intent = new Intent(GoodsActivity.this, OrderActivity.class);
       // TODO 带数据到订单页面
       startActivity(intent);
    }

    /**
     * 获得shop信息
     */
//    String img =  getIntent().getStringExtra("SHOP_URL");
    private void setShopDetail(){
      //  System.out.println(TAG + "setShopDetail img url ---->"+img);

        View view = findViewById(R.id.cv_shop_detail);
        ImageView shopImage = (ImageView)view.findViewById(R.id.iv_shop_image);
        TextView shopName = (TextView) view.findViewById(R.id.tv_shop_name);
        TextView shopShortCut = (TextView) view.findViewById(R.id.tv_shop_shortcut);
        TextView shopAddress = (TextView) view.findViewById(R.id.tv_shop_address);


        if(getIntent().getStringExtra("SHOP_URL") != null){

            Picasso.with(GoodsActivity.this)
                    .load(""+StringConstant.SERVER_IP + getIntent().getStringExtra("SHOP_URL"))
                    .centerCrop()
                    .resize(avatarSize, avatarSize)
                    .transform(new RoundedTransformation())
                    .placeholder(R.mipmap.bili_default_avatar)
                    .into(shopImage);
        }

        shopName.setText(getIntent().getStringExtra("SHOP_NAME"));
        shopShortCut.setText(getIntent().getStringExtra("SHOP_SHORTCUT"));
        shopAddress.setText(getIntent().getStringExtra("SHOP_ADDRESS"));

    }



}
