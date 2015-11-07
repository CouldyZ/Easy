package com.thh.easy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.thh.easy.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 活动列表的适配器
 * Created by cloud on 2015/10/31.
 */
public class ActRVAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

        private static final int ANIMATED_ITEMS_COUNT = 2;

        private Context context;
        private int lastAnimatedPosition = -1;

        private int avatarSize;

        public ActRVAdapter(Context context) {
                this.context = context;
                avatarSize = context.getResources().getDimensionPixelSize(R.dimen.comment_avatar_size);
        }

        @Override
        public int getItemCount() {
                return 5;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                final View view = LayoutInflater.from(context)
                        .inflate(R.layout.item_activity, parent, false);
                return new CellActViewHolder(view);
        }



        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

                CellActViewHolder holder = (CellActViewHolder) viewHolder;
                holder.btnCheckAct.setOnClickListener(this);
                holder.btnCheckAct.setTag(position);
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

        public static class CellActViewHolder extends RecyclerView.ViewHolder {


                @Bind(R.id.tv_act_org_theme)
                TextView tvActOrgTheme;               // 活动主题

                @Bind(R.id.iv_act_org_user_avatar)
                ImageView ivActOrgUserAvatar;         // 发起人头像

                @Bind(R.id.tv_org_user_rp)
                TextView tvOrgOserRP;                 // 发起人RP值

                @Bind(R.id.tv_org_user_name)
                TextView tvOrgUserName;               // 发起人名字

                @Bind(R.id.tv_act_org_time)
                TextView tvActOrgTime;                // 活动时间

                @Bind(R.id.btn_check_act)
                Button btnCheckAct;                   // 进入看看

                public CellActViewHolder(View view) {
                        super(view);
                        ButterKnife.bind(this, view);
                }
        }

        @Override
        public void onClick(View v) {
                int viewId = v.getId();
                switch (viewId){
                        case R.id.btn_check_act:      // 点击进去看看
                                onActItemClickListener.onCheckDetail(v, (int)v.getTag());
                                break;
                        default:
                                break;
                }
        }

        OnActItemClickListener onActItemClickListener;

        public void setOnActItemClickListener(OnActItemClickListener onActItemClickListener){
                this.onActItemClickListener = onActItemClickListener;
        }

        public interface OnActItemClickListener{
                void onCheckDetail(View view, int position);
        }

}