package com.wangyou.qqEmail.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.wangyou.qqEmail.R;

public class MyEmail extends BaseActivity {

    private TextView tvReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_my_email;
    }

    @Override
    protected void initView() {
        methodStart("initView");
        tvReturn = findViewById(R.id.tv_return);
        tvReturn.setOnClickListener((view)->{
            methodStart("tvReturn:Click");
            finish();
            methodEnd("tvReturn:Click");
        });
        methodEnd("initView");
    }

    @Override
    protected void initData() {

    }
}