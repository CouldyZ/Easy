package com.thh.easy.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.thh.easy.R;
import com.thh.easy.constant.StringConstant;
import com.thh.easy.entity.Shop;
import com.thh.easy.util.RoundedTransformation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by cloud on 2015/10/30.
 */
public class ShopRVAdpter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{

    private static final int ANIMATED_ITEMS_COUNT = 2;

    private Context context;
    private int lastAnimatedPosition = -1;
    private List<Shop> shopList = new ArrayList<>();

    private int avatarSize;


    public ShopRVAdpter(Context context, List<Shop> shops) {
        this.context = context;
        this.shopList = shops;
        avatarSize = context.getResources().getDimensionPixelSize(R.dimen.comment_avatar_size);
    }

    public ShopRVAdpter(Context context) {
       this(context,null);
    }

    @Override
    public int getItemCount() {
        return shopList.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context)
                .inflate(R.layout.item_shop, parent, false);
        return new CellPostViewHolder(view);
    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        System.out.println("ShopRVAdapter-----> onBindViewHolder: start---" + Calendar.getInstance().getTime().toString());
        CellPostViewHolder holder = (CellPostViewHolder) viewHolder;
        Shop shop = shopList.get(position);

        System.out.println("adapter ---> shop item :" +
                shop.getUrl() + shop.getAddress() + shop.getPhone()
                + shop.getId() + shop.getShortcut());

        holder.tvShopAdress.setText(shop.getAddress());
        holder.tvShopIntro.setText(shop.getShortcut());
        holder.tvShopName.setText(shop.getName());

        // 加载头像
        if (shop.getUrl() != null)
        {
            Picasso.with(context)
                    .load(StringConstant.SERVER_IP+shop.getUrl())
                    .centerCrop()
                    .resize(avatarSize, avatarSize)
                    .transform(new RoundedTransformation())
                    .placeholder(R.mipmap.bili_default_avatar)
                    .into(((CellPostViewHolder) viewHolder).ivShopImage);
        }
        System.out.println("ShopAdapter -----> url:"+shop.getUrl());
        // 根布局设置监听
        holder.viewRoot.setOnClickListener(this);
        holder.viewRoot.setTag(position);


       // runEnterAnimation(viewHolder.itemView, position);
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

        @Bind(R.id.iv_shop_image)
        ImageView ivShopImage;         // 商店图片

        @Bind(R.id.tv_shop_name)
        TextView tvShopName;           // 商店名

        @Bind(R.id.tv_shop_shortcut)
        TextView tvShopIntro;          // 商店简介

        @Bind(R.id.tv_shop_address)
        TextView tvShopAdress;         // 商店地址

        @Bind(R.id.cv_shop_item_view)
        CardView viewRoot;             // 根布局

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
        if(R.id.cv_shop_item_view == v.getId()){
            onShopItemClickListener.onItemClick(v,(int)v.getTag());
        }
    }

    OnShopItemClickListener onShopItemClickListener;

    public interface OnShopItemClickListener{
        void onItemClick(View view, int position);
    }

    public void setOnPostItemClickListener(OnShopItemClickListener onShopItemClickListener) {
        this.onShopItemClickListener = onShopItemClickListener;
    }
}
