package com.thh.easy.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.ext.HttpCallback;
import com.android.volley.ext.RequestInfo;
import com.android.volley.ext.tools.HttpTools;
import com.thh.easy.R;
import com.thh.easy.adapter.OrderItemRVAdapter;
import com.thh.easy.constant.StringConstant;
import com.thh.easy.entity.OrderItem;
import com.thh.easy.util.SerialzableList;
import com.thh.easy.util.Utils;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 下订单界面
 */
public class OrderActivity extends BaseDrawerActivity {

    @Bind(R.id.tv_order_take_time)
    TextView tvOrderTakeTime;                             // 拿货时间

    @Bind(R.id.rv_order)
    RecyclerView rvOrder;                                 // 订单项

    @Bind(R.id.tv_order_sum)
    TextView tvOrderSum;                                  // 订单小结

    @Bind(R.id.ll_order_bottom)
    LinearLayout ll_bottom;

    private OrderItemRVAdapter orderItemRVAdapter;        // 订单项列表的适配器
    private List<OrderItem> itemList = new ArrayList<>(); // 订单项数据

    // 向服务器发送的数据
    private float sumPrice = 0f;                          // 总计价格
    private int userId = 1;                               // 提交订单的用户id
    private String takeTime = "";                         // 取货时间
    private int shopId = 1;                               // 商店id
    private HttpTools httpTools;                          // 网络操作工具
    List<Map<String, Object>> listOrderItem
            = new ArrayList<>();                          // 订单详情列表


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        init();
        userId = Utils.getUserId(OrderActivity.this);


        getItemData();  // 获得数据
        setUpAdapter(); // 设置数据到list

        tvOrderSum.setText("" + sumPrice); // 设置总价


    }

    /**
     * 初始化网络，toolbar
     */
    private void init() {
        // 初始化网络操作工具
        HttpTools.init(this);
        httpTools = new HttpTools(this);

        // 设置成返回
        getToolbar().setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    /**
     * 获得订单项数据
     */
    private void getItemData(){

        // 获得商品界面的商品详情数据
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        shopId = bundle.getInt(StringConstant.SHOP_ID);           // 获得商店id
        sumPrice = bundle.getFloat(StringConstant.KEY_ORDER_SUM); // 获得订单的小计
        SerialzableList serialzableList =
                (SerialzableList)bundle.get(StringConstant.KEY_ORDER_ITEM_MAP);
        itemList = serialzableList.getList();                     // 获得订单项列表

        // 将订单项数据封装成发送给服务器数据所需要的格式
        Map<String, Object> detailItem;
        for(OrderItem item: itemList) {
            detailItem = new HashMap<>();
            detailItem.put("goodsId", item.getGoodsId());
            detailItem.put("goodsNum", item.getGoodsNum());
            listOrderItem.add(detailItem);
        }
    }


    /**
     * 初始化订单项列表
     */
    private void setUpAdapter(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvOrder.setLayoutManager(linearLayoutManager);
        rvOrder.setHasFixedSize(true);
        orderItemRVAdapter = new OrderItemRVAdapter(this, itemList);
        rvOrder.setAdapter(orderItemRVAdapter);
    }


    /**
     * 点击选择界面
     */
    @OnClick({R.id.tv_order_take_time, R.id.iv_order_take_time_icon})
    void onStartChoosen(){

        // 弹出对话框，提示填取货时间
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("请选择取货时间");
        final String[] times =
                new String[]{"10:00 - 12:00", "12:00 - 14:00", "14:00 - 17:00", "17:00 - 22:00"};
        builder.setSingleChoiceItems(times, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 设置选择后的时间
                tvOrderTakeTime.setText(times[which]);
                takeTime = times[which];
            }
        });

        builder.setPositiveButton("确定", null);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tvOrderTakeTime.setText("请选择拿货时间");
            }
        });

        builder.create().show();
    }



    /**
     * 点击确认预定商品
     * 返回首页
     */
    @OnClick(R.id.btn_order_ensure)
    void onClickEnsureOrder(){

        if (userId == 0) {
            Snackbar.make(ll_bottom, "先登陆呗", Snackbar.LENGTH_SHORT).show();
        }

        if(TextUtils.isEmpty(takeTime)){
            Snackbar.make(ll_bottom, "请选择取货时间", Snackbar.LENGTH_SHORT).show();
            return;
        }

        postItem();              // 向数据库插入数据



    }

    /**
     * 请求商品数据
     */
    private void postItem() {

        // 获得订单详情列表的json字符串数据
        JSONArray jsonArray = new JSONArray(listOrderItem);
        String jsonSender = jsonArray.toString();

        // 设置向服务器发送的数据
        Map<String, String> params = new HashMap<>();
        params.put(StringConstant.ORDER_SHOP_ID, "" + shopId);
        params.put(StringConstant.ORDER_USER_ID, "" + userId);
        params.put(StringConstant.ORDER_TAKE_TIME, takeTime);
        params.put(StringConstant.KEY_ORDER_ITEM, jsonSender);

        RequestInfo info = new RequestInfo(StringConstant.SERVER_CONFIRM_ORDER, params);
        httpTools.post(info, new HttpCallback() {
                @Override
                public void onStart() {
                }

                 @Override
                  public void onFinish() {
                  }

                                @Override
                                public void onResult(String s) {

                                    if (StringConstant.NULL_VALUE.equals(s)) {
                                        Snackbar.make(ll_bottom, "服务器出错", Snackbar.LENGTH_SHORT).show();
                                        return;
                                    }

                                    if (StringConstant.SUCCESS.equals(s)) {
                                        Snackbar.make(ll_bottom, "预定成功", Snackbar.LENGTH_SHORT).show();
                                            // 如果成功就返回商店界面
                                        startActivity(new Intent(OrderActivity.this, ShopActivity.class));
                                        finish();
                                        return;
                                    }

                                    if (StringConstant.FAIL.equals(s)) {
                                        Snackbar.make(ll_bottom, "预定失败", Snackbar.LENGTH_SHORT).show();
                                        return;
                                    }
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
                            }

            );
    }

}
