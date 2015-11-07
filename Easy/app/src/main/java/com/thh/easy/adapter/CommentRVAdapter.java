package com.thh.easy.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.thh.easy.R;
import com.thh.easy.entity.Comment;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 评论列表
 * Created by cloud on 2015/10/30.
 */
public class CommentRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private int itemsCount = 0;                     // 数据总数
    private int lastAnimatedPosition = -1;          // 最后动画的位置
    private int avatarSize;                          // 头像大小

    private boolean animationsLocked = false;        // 是否锁定
    private boolean delayEnterAnimation = true;       // 是否延迟进入动画

    private List<Comment> commentList;

    public CommentRVAdapter(Context context) {
        this.context = context;
        avatarSize = context.getResources().getDimensionPixelSize(R.dimen.comment_avatar_size);
    }

    public CommentRVAdapter(Context context, List<Comment> commentList) {
        this.context = context;
        this.commentList = commentList;
        avatarSize = context.getResources().getDimensionPixelSize(R.dimen.comment_avatar_size);

        System.out.println(">>>>>>>>>>>>");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        animationsLocked = true;
        CommentViewHolder holder = (CommentViewHolder) viewHolder;

        // 填充评论内容数据
        holder.tvComment.setText(commentList.get(position).getContent());
        System.out.println(commentList.get(position).getContent() + ">>>>>>>>>>>>");

        if (commentList.get(position).getAvatar() != null && commentList.get(position).getAvatar().contains("http://"))
            Picasso.with(context)
                    .load(commentList.get(position).getAvatar())
                    .placeholder(R.mipmap.bili_default_avatar)
                    .into(holder.ivUserAvatar);

        animationsLocked = false;
        runEnterAnimation(viewHolder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    /**
     * recylerview 进入动画
     * @param view
     * @param position
     */
    private void runEnterAnimation(View view, int position) {
        if (animationsLocked) return;

        if (position > lastAnimatedPosition) {
            lastAnimatedPosition = position;
            view.setTranslationY(100f);
            view.setAlpha(0.f);
            view.animate()
                    .translationY(0).alpha(1.f)
                    .setStartDelay(delayEnterAnimation ? 20 * (position) : 0)
                    .setInterpolator(new DecelerateInterpolator(2.f))
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            animationsLocked = true;
                        }
                    })
                    .start();
        }
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    /**
     * 设置动画是否锁定
     * @param animationsLocked
     */
    public void setAnimationsLocked(boolean animationsLocked) {
        this.animationsLocked = animationsLocked;
    }

    /**
     * 设置是否延迟进入动画
     * @param delayEnterAnimation
     */
    public void setDelayEnterAnimation(boolean delayEnterAnimation) {
        this.delayEnterAnimation = delayEnterAnimation;
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_user_avatar)
        ImageView ivUserAvatar;
        @Bind(R.id.tv_comment)
        TextView tvComment;

        public CommentViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}