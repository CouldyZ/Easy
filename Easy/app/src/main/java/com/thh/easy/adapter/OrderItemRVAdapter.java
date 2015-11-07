package com.thh.easy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thh.easy.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 *
 * Created by cloud on 2015/11/2.
 */
public class OrderItemRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private int size;

    public OrderItemRVAdapter(Context context) {
        this.context = context;
        size = 8;
    }

    @Override
    public int getItemCount() {
        return size;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context)
                .inflate(R.layout.item_order_detail, parent, false);
        return new CellOrderItemViewHolder(view);
    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        CellOrderItemViewHolder holder = (CellOrderItemViewHolder) viewHolder;

    }


    public static class CellOrderItemViewHolder extends RecyclerView.ViewHolder {

        // 商品名，商品份数，商品每项总价

        @Bind(R.id.tv_order_goods_name)
        TextView tvOrderGoodsName;                 // 商品名

        @Bind(R.id.tv_order_goods_num)
        TextView tvOrderGoodsNum;                  // 商品份数

        @Bind(R.id.tv_order_goods_sum)
        TextView tvOrderGoodsSum;                  // 商品每项总价

        public CellOrderItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}

