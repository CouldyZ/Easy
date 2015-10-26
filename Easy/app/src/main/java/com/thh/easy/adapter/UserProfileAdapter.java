package com.thh.easy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.thh.easy.R;
import com.thh.easy.util.Utils;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 用户图集
 * Created by cloud on 2015/10/25.
 */
public class UserProfileAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private static final int PHOTO_ANIMATION_DELAY = 600;
        private static final Interpolator INTERPOLATOR = new DecelerateInterpolator();

        private final Context context;
        private final int cellSize;

        private final List<String> photos; // 图片集

        private boolean lockedAnimations = false;
        private int lastAnimatedItem = -1;

        public UserProfileAdapter(Context context) {
                this.context = context;
                this.cellSize = Utils.getScreenWidth(context) / 3;
                this.photos = Arrays.asList(context.getResources()
                        .getStringArray(R.array.user_photos));   // 图片集数据
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                final View view = LayoutInflater.from(context)
                        .inflate(R.layout.item_photo, parent, false);
                StaggeredGridLayoutManager.LayoutParams layoutParams =
                        (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();

                layoutParams.height = cellSize;
                layoutParams.width = cellSize;
                layoutParams.setFullSpan(false);
                view.setLayoutParams(layoutParams);

                return new PhotoViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                bindPhoto((PhotoViewHolder) holder, position);
        }

        @Override
        public int getItemCount() {
                return photos.size();
        }

        /**
         * 绑定网络图片
         * @param holder
         * @param position
         */
        private void bindPhoto(final PhotoViewHolder holder, int position) {
                Picasso.with(context)
                .load(photos.get(position))
                .resize(cellSize, cellSize)
                .centerCrop()
                .into(holder.ivPhoto, new Callback() {
                        @Override
                        public void onSuccess() {
                                animatePhoto(holder);
                        }

                        @Override
                        public void onError() {

                        }
                });
                if (lastAnimatedItem < position) lastAnimatedItem = position;
        }

        private void animatePhoto(PhotoViewHolder viewHolder) {
            if (!lockedAnimations) {
                if (lastAnimatedItem == viewHolder.getPosition()) {
                      setLockedAnimations(true);
                }

                long animationDelay = PHOTO_ANIMATION_DELAY + viewHolder.getPosition() * 30;

                viewHolder.flRoot.setScaleY(0);
                viewHolder.flRoot.setScaleX(0);

                viewHolder.flRoot.animate().scaleY(1).scaleX(1)
                .setDuration(200).setInterpolator(INTERPOLATOR).setStartDelay(animationDelay)
                .start();
            }
        }

        public void setLockedAnimations(boolean lockedAnimations) {
                this.lockedAnimations = lockedAnimations;
        }



        static class PhotoViewHolder extends RecyclerView.ViewHolder {
            @Bind(R.id.flRoot)
            FrameLayout flRoot;   // 根布局

            @Bind(R.id.ivPhoto)
            ImageView ivPhoto;    //  图片

            public PhotoViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }
        }

}
