package com.thh.easy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.thh.easy.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by cloud on 2015/10/31.
 */
public class OrderRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{

    private static final int ANIMATED_ITEMS_COUNT = 2;

    private Context context;
    private int lastAnimatedPosition = -1;

    private int avatarSize;

    private int size;
    public OrderRVAdapter(Context context) {
        this.context = context;
        avatarSize = context.getResources().getDimensionPixelSize(R.dimen.comment_avatar_size);
        size = 8;
    }

    @Override
    public int getItemCount() {
        return size;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context)
                .inflate(R.layout.item_order, parent, false);
        return new CellPostViewHolder(view);
    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        CellPostViewHolder holder = (CellPostViewHolder) viewHolder;
        holder.ibOrderDelete.setOnClickListener(this);
        holder.ibOrderDelete.setTag(position);
        runEnterAnimation(viewHolder.itemView, position);
    }

    /**
     * 进入动画
     * @param view
     * @param position
     */
    private void runEnterAnimation(View view, int position) {
        if (position >= ANIMATED_ITEMS_COUNT - 1) {
            return;
        }

        if (position > lastAnimatedPosition) {
            lastAnimatedPosition = position;
            view.setTranslationY(100);
            view.animate()
                    .translationY(0)
                    .setInterpolator(new DecelerateInterpolator(3.f))
                    .setDuration(300)
                    .start();
        }
    }

    public static class CellPostViewHolder extends RecyclerView.ViewHolder {

        // 订单状态， 订单完成时间，商店图片，商店名， 删除按钮， 总计份数， 总计金额


        @Bind(R.id.tv_order_state)
        TextView tvOrderState;                  // 订单状态

        @Bind(R.id.tv_order_comm_time)
        TextView tvOrderCommTime;                // 订单完成时间 order complete time

        @Bind(R.id.ib_order_delete)
        ImageButton ibOrderDelete;                // 订单删除按钮

        @Bind(R.id.iv_order_shop_image)
        ImageView iv_order_shop_image;             // 商店图片

        @Bind(R.id.tv_order_shop_name)
        TextView tvOrderShopName;                  // 商店名

        @Bind(R.id.tv_order_num)
        TextView tvOrderNum;                      // 总计份数

        @Bind(R.id.order_sum_price)
        TextView orderSumPrice;                   // 总计价格

        public CellPostViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    /**
     * item 点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        switch (viewId){
            // 删除订单项
            case R.id.ib_order_delete:
                size -= 1;
                notifyItemRemoved((int)v.getTag()-1);
                // TODO 从服务器中删除数据
                break;
            default:
                break;
        }
    }
}

