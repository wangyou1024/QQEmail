package com.wangyou.qqEmail.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.wangyou.qqEmail.R;

public class CalenderActivity extends BaseActivity {

    private ImageView ivReturnPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_calender;
    }

    @Override
    protected void initView() {
        ivReturnPage = findViewById(R.id.iv_return_page);
        ivReturnPage.setOnClickListener(v -> this.finish());
    }

    @Override
    protected void initData() {

    }
}