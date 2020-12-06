package com.wangyou.qqEmail.adapter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected Context mContext;
    protected void methodStart(String methodName){
        Log.i(this.getClass().getSimpleName(),methodName+" start");
    }

    protected void methodEnd(String methodName){
        Log.i(this.getClass().getSimpleName(),methodName+" end");
    }

    protected void toastShow(String msg){
        Toast.makeText(mContext,msg,Toast.LENGTH_SHORT).show();
    }

}
