package com.wangyou.qqEmail.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.wangyou.qqEmail.R;
import com.wangyou.qqEmail.activity.BaseActivity;

public class MainActivity extends BaseActivity {

    private PopupMenu popupMenu;
    private ImageView iv_more;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        methodStart("onCreate");
        super.onCreate(savedInstanceState);
        methodEnd("onCreate");
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        methodStart("initView");
        iv_more = findViewById(R.id.iv_more);
        iv_more.setOnClickListener((view)->{
            showPopupMenu(view);
        });
        methodEnd("initView");
    }

    @Override
    protected void initData() {

    }

    private void showPopupMenu(View view) {
        methodStart("showPopupMenu");
        popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_main, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener((menuItem) -> {
            switch (menuItem.getItemId()) {
                case R.id.menu_item_write_email:
                    toastShow("写邮件");
                    break;
                case R.id.menu_item_note:
                    toastShow("写记事");
                    break;
                case R.id.menu_item_scan_file:
                    toastShow("扫描文件");
                    break;
                case R.id.menu_item_setting:
                    toastShow("设置");
                    break;
                case R.id.menu_item_time_poster:
                    toastShow("时光使者");
                    break;
            }
            return true;
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            popupMenu.setForceShowIcon(true);
        }
        popupMenu.show();
        methodEnd("showPopupMenu");
    }
}