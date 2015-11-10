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

import com.squareup.picasso.Picasso;
import com.thh.easy.R;
import com.thh.easy.constant.StringConstant;
import com.thh.easy.entity.Order;
import com.thh.easy.util.RoundedTransformation;

import java.util.ArrayList;
import java.util.List;

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

    List<Order> ordersList = new ArrayList<>();
    public OrderRVAdapter(Context context) {
        this.context = context;
        avatarSize = context.getResources().getDimensionPixelSize(R.dimen.comment_avatar_size);
    }

    public OrderRVAdapter(Context context, List<Order> ordersList) {
        this.context = context;
        this.ordersList = ordersList;
        size = ordersList.size();
        avatarSize = context.getResources().getDimensionPixelSize(R.dimen.comment_avatar_size);
    }

    @Override
    public int getItemCount() {
        return size;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context)
                .inflate(R.layout.item_order, parent, false);
        return new CellOrderViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        Order order = ordersList.get(position);
        CellOrderViewHolder holder = (CellOrderViewHolder) viewHolder;
        holder.ibOrderDelete.setOnClickListener(this);
        holder.ibOrderDelete.setTag(position);

        String stateString = order.getState() == 0? "订单已完成":"订单未成功";
        holder.tvOrderState.setText(stateString);                            // 订单状态

        holder.tvOrderCommTime.setText(order.getCompletedate());             // 订单完成时间

        // 加载商店头像
        if (order.getShopImgUrl().length() > 26)
        {
            Picasso.with(context)
                    .load(StringConstant.SERVER_IP+"/"+order.getShopImgUrl())
                    .centerCrop()
                    .resize(avatarSize, avatarSize)
                    .transform(new RoundedTransformation())
                    .placeholder(R.mipmap.bili_default_avatar)
                    .into(((CellOrderViewHolder) viewHolder).ivOrderShopImage);
        }

        holder.tvOrderShopName.setText(order.getShopName());                  // 商店名
        holder.tvOrderNum.setText(""+order.getCount());                       // 总计份数
        holder.orderSumPrice.setText(""+order.getSum());                      // 总计价格

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

    public static class CellOrderViewHolder extends RecyclerView.ViewHolder {

        // 订单状态， 订单完成时间，商店图片，商店名， 删除按钮， 总计份数， 总计金额


        @Bind(R.id.tv_order_state)
        TextView tvOrderState;                  // 订单状态

        @Bind(R.id.tv_order_comm_time)
        TextView tvOrderCommTime;                // 订单完成时间 order complete time

        @Bind(R.id.ib_order_delete)
        ImageButton ibOrderDelete;                // 订单删除按钮

        @Bind(R.id.iv_order_shop_image)
        ImageView ivOrderShopImage;             // 商店图片

        @Bind(R.id.tv_order_shop_name)
        TextView tvOrderShopName;                  // 商店名

        @Bind(R.id.tv_order_num)
        TextView tvOrderNum;                      // 总计份数

        @Bind(R.id.order_sum_price)
        TextView orderSumPrice;                   // 总计价格

        public CellOrderViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    int size = 3;
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

