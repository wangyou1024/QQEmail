package com.wangyou.qqEmail.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initLayout());
        initData();
        initView();
    }

    protected abstract int initLayout();
    protected abstract void initView();
    protected abstract void initData();

    protected void methodStart(String methodName){
        Log.i(this.getClass().getSimpleName(),methodName+" start");
    }

    protected void methodEnd(String methodName){
        Log.i(this.getClass().getSimpleName(),methodName+" end");
    }

    protected void toastShow(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

}
