package com.wangyou.qqEmail.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.wangyou.qqEmail.R;
import com.wangyou.qqEmail.entity.Email;
import com.wangyou.qqEmail.interf.onMoveAndSwipedListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 参考：https://www.material.io/的官方app
 */
public class EmailRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements onMoveAndSwipedListener {

    private Context context;
    private List<Email> mItems;
    private final int TYPE_NORMAL = 1;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private OnItemClickListener onItemClickListener;

    public EmailRecyclerViewAdapter(Context context) {
        this.context = context;
        mItems = new ArrayList();
    }

    public void setItems(List<Email> data) {
        this.mItems = data;
        notifyDataSetChanged();
    }

    public void addItem(int position, Email insertData) {
        mItems.add(position, insertData);
        notifyItemInserted(position);
    }

    public void addItems(List<Email> data) {
        mItems.addAll(data);
        notifyItemInserted(mItems.size() - 1);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_email, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof RecyclerViewHolder) {
            final RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;
            Email email = mItems.get(position);
            recyclerViewHolder.email = email;
            recyclerViewHolder.tvHeader.setText(email.getSender().substring(0,1));
            recyclerViewHolder.ivIsRead.setVisibility(email.isRead()?View.GONE:View.VISIBLE);
            recyclerViewHolder.ivIsDraft.setVisibility(email.isDraft()?View.VISIBLE:View.GONE);
            recyclerViewHolder.tvSender.setText(email.getSender());
            recyclerViewHolder.ivIsStar.setVisibility(email.isStar()?View.VISIBLE:View.GONE);
            recyclerViewHolder.tvDate.setText(email.getDate());
            recyclerViewHolder.tvTheme.setText(email.getTheme());
            recyclerViewHolder.tvContent.setText(email.getContent());
            // 新ViewHolder的显示动画
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.anim_recycler_item_show);
            recyclerViewHolder.mView.startAnimation(animation);
        }
    }


    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_NORMAL;
    }

    /**
     * 交换位置
     * @param fromPosition
     * @param toPosition
     * @return
     */
    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mItems, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    /**
     * 滑动删除
     * @param position
     */
    @Override
    public void onItemDismiss(final int position) {
        mItems.remove(position);
        notifyItemRemoved(position);
        /*Snackbar.make(parentView, context.getString(R.string.item_swipe_dismissed), Snackbar.LENGTH_SHORT)
                .setAction(context.getString(R.string.item_swipe_undo), v -> addItem(position, mItems.get(position))).show();*/
    }

    private class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private View mView;
        private Email email;
        private TextView tvHeader;
        private ImageView ivIsRead;
        private ImageView ivIsDraft;
        private TextView tvSender;
        private ImageView ivIsStar;
        private TextView tvDate;
        private TextView tvTheme;
        private TextView tvContent;
        private RecyclerViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            tvHeader = itemView.findViewById(R.id.tv_header);
            ivIsRead = itemView.findViewById(R.id.iv_is_read);
            ivIsDraft = itemView.findViewById(R.id.iv_is_draft);
            tvSender = itemView.findViewById(R.id.tv_send);
            ivIsStar = itemView.findViewById(R.id.iv_is_star);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvTheme = itemView.findViewById(R.id.tv_theme);
            tvContent = itemView.findViewById(R.id.tv_content);
            mView.setOnClickListener(v -> {
                onItemClickListener.onItemClick(email);
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Serializable obj);
    }

}
