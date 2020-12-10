package com.wangyou.qqEmail.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.wangyou.qqEmail.R;

public class LoginActivity extends BaseActivity{

    private TextView tvOver;
    private ImageView ivEmailQQ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        tvOver = findViewById(R.id.tv_over);
        tvOver.setOnClickListener(v -> {
            this.finish();
        });
        ivEmailQQ = findViewById(R.id.iv_email_qq);
        ivEmailQQ.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            this.finish();
        });
    }

    @Override
    protected void initData() {

    }
}