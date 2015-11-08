package com.thh.easy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.thh.easy.entity.Goods;
import com.thh.easy.util.RoundedTransformation;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by cloud on 2015/11/2.
 */
public class GoodsRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int ANIMATED_ITEMS_COUNT = 2;

    private Context context;
    private int lastAnimatedPosition = -1;
    private int avatarSize;

    private List<Goods> goodsList = new ArrayList<>();


    public GoodsRVAdapter(Context context, List<Goods> goodsList) {
        this.context = context;
        this.goodsList = goodsList;
        avatarSize = context.getResources().getDimensionPixelSize(R.dimen.comment_avatar_size);
    }


    @Override
    public int getItemCount() {
        return goodsList.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context)
                .inflate(R.layout.item_goods, parent, false);
        return new CellGoodsViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {

        final CellGoodsViewHolder holder = (CellGoodsViewHolder) viewHolder;
        final Goods goods = goodsList.get(position);
        holder.ibGoodsMinus.setVisibility(View.INVISIBLE);
        holder.tvGoodsName.setText(goods.getName());
        holder.tvGoodsPrice.setText("" + goods.getPrice());

        // 加载头像
        if (goods.getUrl().length() > 26)
        {
            Picasso.with(context)
                    .load(StringConstant.SERVER_IP+"/"+goods.getUrl())
                    .centerCrop()
                    .resize(avatarSize, avatarSize)
                    .transform(new RoundedTransformation())
                    .placeholder(R.mipmap.bili_default_avatar)
                    .into(((CellGoodsViewHolder) viewHolder).ivGoodsImage);
        }

        int count = 0;
        holder.ibGoodsAdd.setOnClickListener(new View.OnClickListener(){
            @Override
        public void onClick(View view){
                if("0".equals(holder.tvGoodsNum.getText().toString())){
                    holder.ibGoodsMinus.setVisibility(View.VISIBLE);
                }

                Integer count = Integer.parseInt(holder.tvGoodsNum.getText().toString());
                count += 1;
                holder.tvGoodsNum.setText(""+count);

                onItemCountChanged.onChanged(view, count, position, count*goodsList.get(position).getPrice());

            }
        });

        holder.ibGoodsMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer count = Integer.parseInt(holder.tvGoodsNum.getText().toString());
                if (count != 0) {
                    count -= 1;
                    holder.tvGoodsNum.setText("" + count);
                }

                if ("0".equals(holder.tvGoodsNum.getText().toString())) {
                    holder.ibGoodsMinus.setVisibility(View.INVISIBLE);
                }
                onItemCountChanged.onChanged(view, count, position, count*goodsList.get(position).getPrice());
            }
        });
        Log.e("item inside here", holder.tvGoodsNum.getText().toString());

      //  runEnterAnimation(viewHolder.itemView, position);
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

    public static class CellGoodsViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.iv_goods_image)
        ImageView ivGoodsImage;                 // 商品图片

        @Bind(R.id.tv_goods_name)
        TextView tvGoodsName;                    // 商品名

        @Bind(R.id.tv_goods_price)
        TextView tvGoodsPrice;                   // 商品价格

        @Bind(R.id.ib_goods_minus)
        ImageButton ibGoodsMinus;                // 减少商品数量按钮

        @Bind(R.id.ib_goods_add)
        ImageButton ibGoodsAdd;                  // 增加商品数量按钮

        @Bind(R.id.tv_goods_num)
        TextView tvGoodsNum;                     // 商品数量

        int count;
        public CellGoodsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            count = 0;
        }
    }

    OnItemCountChangedListener onItemCountChanged;

    public void setChangedListener(OnItemCountChangedListener onItemCountChanged){
        this.onItemCountChanged = onItemCountChanged;
    }

    public interface OnItemCountChangedListener{
        void onChanged(View view ,int sum, int position, float price);
    }

}


