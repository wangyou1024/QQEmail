package com.wangyou.qqEmail.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wangyou.qqEmail.R;
import com.wangyou.qqEmail.entity.Person;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PersonRecyclerViewAdapter extends BaseRecyclerViewAdapter {

    private OnItemClickListener onItemClickListener;
    private List<Person> mItems;

    public PersonRecyclerViewAdapter(Context context) {
       this.mContext = context;
       mItems = new ArrayList<>();
    }

    public void setMItems(List<Person> persons) {
        this.mItems = persons;
        notifyDataSetChanged();
    }

    public void addItem(Person person){
        this.mItems.add(person);
        notifyDataSetChanged();
    }

    public void addItems(List<Person> persons){
        this.mItems.addAll(persons);
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_person, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        methodStart("onBindViewHolder");
        if (holder instanceof RecyclerViewHolder){
            RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;
            Person person = mItems.get(position);
            recyclerViewHolder.person = person;
            recyclerViewHolder.tvSection.setText(person.getSortLetters().substring(0,1));
            recyclerViewHolder.tvHeader.setText(person.getName().substring(0,1));
            recyclerViewHolder.tvName.setText(person.getName());
            recyclerViewHolder.tvEmail.setText(person.getEmail());

            // 新ViewHolder的显示动画
            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.anim_recycler_item_show);
            recyclerViewHolder.mView.startAnimation(animation);

            int section = getSectionForPosition(position);
            // 如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
            if (position == getPositionForSection(section)){
                recyclerViewHolder.tvSection.setVisibility(View.VISIBLE);
            }else{
                recyclerViewHolder.tvSection.setVisibility(View.GONE);
            }
        }
        methodEnd("onBindViewHolder");
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    /**
     * 根据ListView的当前位置获取分类的首字母的char ascii值
     * @param position
     * @return
     */
    public int getSectionForPosition(int position) {
        return mItems.get(position).getSortLetters().charAt(0);
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     * @param section
     * @return
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < getItemCount(); i++) {
            String sortStr = mItems.get(i).getSortLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }

        return getItemCount();
    }


    private class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private View mView;
        private Person person;
        private TextView tvSection;
        private TextView tvHeader;
        private TextView tvName;
        private TextView tvEmail;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            tvSection = itemView.findViewById(R.id.tv_section);
            tvHeader = itemView.findViewById(R.id.tv_header);
            tvName = itemView.findViewById(R.id.tv_name);
            tvEmail = itemView.findViewById(R.id.tv_email);
            itemView.setOnClickListener((view)->{
                onItemClickListener.onItemClick(person);
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Serializable obj);
    }

}
