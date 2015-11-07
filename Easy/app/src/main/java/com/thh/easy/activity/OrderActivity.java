package com.thh.easy.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.thh.easy.R;
import com.thh.easy.adapter.OrderItemRVAdapter;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 下订单界面
 */
public class OrderActivity extends BaseDrawerActivity {

    @Bind(R.id.tv_order_take_time)
    TextView tvOrderTakeTime;       // 拿货时间

    @Bind(R.id.rv_order)
    RecyclerView rvOrder;           // 订单项

    @Bind(R.id.tv_order_sum)
    TextView tvOrderSum;             // 订单小结

    @Bind(R.id.btn_order_ensure)
    Button btnOrderEnsure;           // 确认预定按钮

    OrderItemRVAdapter orderItemRVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        // 设置成返回
        getToolbar().setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        setUpAdapter();



    }


    /**
     * 初始化订单项列表
     */
    private void setUpAdapter(){
       LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvOrder.setLayoutManager(linearLayoutManager);
        rvOrder.setHasFixedSize(true);
        orderItemRVAdapter = new OrderItemRVAdapter(this);
        rvOrder.setAdapter(orderItemRVAdapter);
    }

    /**
     * 点击选择界面
     */
    @OnClick({R.id.tv_order_take_time, R.id.iv_order_take_time_icon})
    void onStartChoosen(){

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("请选择拿货时间");
        final String[] times =
                new String[]{"10:00 - 12:00", "12:00 - 14:00", "14:00 - 17:00", "17:00 - 22:00"};
        builder.setSingleChoiceItems(times, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 设置选择后的时间
                tvOrderTakeTime.setText(times[which]);
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
        // TODO 将数据返回到服务器，我的订单页面添加一项订单
        Toast.makeText(OrderActivity.this, "预定商品成功", Toast.LENGTH_LONG).show();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

}
