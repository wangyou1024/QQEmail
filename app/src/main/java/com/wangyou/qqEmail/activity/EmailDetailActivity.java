package com.wangyou.qqEmail.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wangyou.qqEmail.R;
import com.wangyou.qqEmail.entity.Email;

public class EmailDetailActivity extends BaseActivity {

    private ImageView ivReturnPage;
    private TextView tvTheme;
    private LinearLayout llDetail;
    private TextView tvSenderEmail;
    private TextView tvDetail;
    private LinearLayout llHidden;
    private TextView tvDetailSenderName;
    private TextView tvDetailSenderEmail;
    private TextView tvHidden;
    private TextView tvDetailReceiveName;
    private TextView tvDetailReceiveEmail;
    private TextView tvDate;
    private TextView tvContent;
    private Email email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_email_detail;
    }

    @Override
    protected void initView() {
        ivReturnPage = findViewById(R.id.iv_return_page);
        ivReturnPage.setOnClickListener(v -> {
            this.finish();
        });

        tvTheme = findViewById(R.id.tv_theme);
        tvTheme.setText(email.getTheme());

        llDetail = findViewById(R.id.ll_detail);

        tvSenderEmail = findViewById(R.id.tv_sender_email);
        tvSenderEmail.setText("123456789@qq.com");

        tvDetail = findViewById(R.id.tv_detail);
        tvDetail.setOnClickListener(v -> {
            llDetail.setVisibility(View.GONE);
            llHidden.setVisibility(View.VISIBLE);
        });

        llHidden = findViewById(R.id.ll_hidden);
        llHidden.setVisibility(View.GONE);

        tvDetailSenderName = findViewById(R.id.tv_detail_sender_name);
        tvDetailSenderName.setText(email.getSender());

        tvHidden = findViewById(R.id.tv_hidden);
        tvHidden.setOnClickListener(v -> {
            llHidden.setVisibility(View.GONE);
            llDetail.setVisibility(View.VISIBLE);
        });
        tvDetailSenderEmail = findViewById(R.id.tv_detail_sender_email);
        tvDetailSenderEmail.setText("123456789@qq.com");

        tvDetailReceiveName = findViewById(R.id.tv_detail_receive_name);
        tvDetailReceiveName.setText("百忧");

        tvDetailReceiveEmail = findViewById(R.id.tv_detail_receive_email);
        tvDetailReceiveEmail.setText("baiyou1024@qq.com");

        tvDate = findViewById(R.id.tv_date);
        tvDate.setText(email.getDate());

        tvContent = findViewById(R.id.tv_content);
        tvContent.setText(email.getContent());
    }

    @Override
    protected void initData() {
        this.email = ((Email) getIntent().getSerializableExtra("email"));
    }
}