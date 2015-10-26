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
import com.thh.easy.util.Utils;
import com.thh.easy.view.SquaredImageView;

/**
 * 帖子
 * Created by cloud on 2015/10/25.
 */
public class PostRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{

    private static final int ANIMATED_ITEMS_COUNT = 2;

    private Context context;
    private int lastAnimatedPosition = -1;
    private int itemsCount ;

    private OnFeedItemClickListener onPostItemClickListener;
    public PostRVAdapter(Context context) {
            this.context = context;
            }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final View view = LayoutInflater.from(context)
                    .inflate(R.layout.item_post, parent, false);
            return new CellPostViewHolder(view);
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
            view.setTranslationY(Utils.getScreenHeight(context));
            view.animate()
                    .translationY(0)
                    .setInterpolator(new DecelerateInterpolator(3.f))
                    .setDuration(700)
                    .start();
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
            runEnterAnimation(viewHolder.itemView, position);

            CellPostViewHolder holder = (CellPostViewHolder) viewHolder;

            if (position % 2 == 0) {
                holder.ivPostCenter.setImageResource(R.mipmap.img_feed_center_1);
                holder.ivPostBottom.setImageResource(R.mipmap.img_feed_bottom_1);
            } else {
                holder.ivPostCenter.setImageResource(R.mipmap.img_feed_center_2);
                holder.ivPostBottom.setImageResource(R.mipmap.img_feed_bottom_2);
            }

            // 点击头像跳转到个人展示页面
            holder.ibUserProtrait.setOnClickListener(this);
            holder.ibUserProtrait.setTag(position);

            holder.ivPostBottom.setOnClickListener(this);
            holder.ivPostBottom.setTag(position);
            }

    @Override
    public int getItemCount() {
            return itemsCount;
            }

    public static class CellPostViewHolder extends RecyclerView.ViewHolder {

        SquaredImageView ivPostCenter; // 中间的图片内容
        ImageView ivPostBottom;        // 底部信息
        TextView tvPostTime;           // 发帖时间
        ImageButton ibUserProtrait;    // 发帖人头像

        public CellPostViewHolder(View view) {
            super(view);
            ivPostCenter = (SquaredImageView) view.findViewById(R.id.iv_post_center);
            ivPostBottom = (ImageView) view.findViewById(R.id.iv_post_bottom);
            ibUserProtrait = (ImageButton) view.findViewById(R.id.iv_post_user_image);
            tvPostTime = (TextView) view.findViewById(R.id.item_post_time);
        }
    }

    public void updateItems() {
        itemsCount = 10;
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.iv_post_bottom) {
            if (onPostItemClickListener != null) {
                onPostItemClickListener.onCommentsClick(view, (Integer) view.getTag());
            }
        }

        if (view.getId() == R.id.iv_post_user_image) {
            if (onPostItemClickListener != null) {
                onPostItemClickListener.onProfileClick(view,(Integer) view.getTag());
            }
        }
    }

    public interface OnFeedItemClickListener {
        void onCommentsClick(View v, int position);
        void onProfileClick(View v, int position);
    }

    public void setOnPostItemClickListener(OnFeedItemClickListener onPostItemClickListener) {
        this.onPostItemClickListener = onPostItemClickListener;
    }
}
