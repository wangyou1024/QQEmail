package com.wangyou.qqEmail.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;

import androidx.cardview.widget.CardView;

import com.wangyou.qqEmail.R;
import com.wangyou.qqEmail.fragment.WriteEmailFragment;

public class MainActivity extends BaseActivity implements View.OnClickListener{

    private PopupMenu popupMenu;
    private ImageView ivMore;
    private CardView cvHeader;
    private LinearLayout llReceiveMessage;
    private LinearLayout llStarEmail;
    private LinearLayout llGroupEmail;
    private LinearLayout llDraft;
    private LinearLayout llHaveSent;
    private LinearLayout llHaveDelete;
    private LinearLayout llRubbishBox;


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
        ivMore = findViewById(R.id.iv_more);
        ivMore.setOnClickListener(view-> {
            showPopupMenu(view);
        });
        cvHeader = findViewById(R.id.cv_header);
        cvHeader.setOnClickListener((view)->{
            Intent intent = new Intent(MainActivity.this, MyEmail.class);
            startActivity(intent);
        });
        llReceiveMessage = findViewById(R.id.ll_receive_message);
        llReceiveMessage.setOnClickListener(this);
        llStarEmail = findViewById(R.id.ll_star_email);
        llStarEmail.setOnClickListener(this);
        llGroupEmail = findViewById(R.id.ll_group_email);
        llGroupEmail.setOnClickListener(this);
        llDraft = findViewById(R.id.ll_draft_box);
        llDraft.setOnClickListener(this);
        llHaveSent = findViewById(R.id.ll_have_sent);
        llHaveSent.setOnClickListener(this);
        llHaveDelete = findViewById(R.id.ll_have_delete);
        llHaveDelete.setOnClickListener(this);
        llRubbishBox = findViewById(R.id.ll_rubbish_box);
        llRubbishBox.setOnClickListener(this);
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
                    WriteEmailFragment.newInstance().show(getSupportFragmentManager(),"MainActivity");
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
                default:toastShow("error");break;
            }
            return true;
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            popupMenu.setForceShowIcon(true);
        }
        popupMenu.show();
        methodEnd("showPopupMenu");
    }

    @Override
    public void onClick(View v) {
        methodStart("onClick");
        Intent intent = new Intent(this, EmailList.class);
        switch (v.getId()){
            case R.id.ll_receive_message:intent.putExtra("type", EmailList.RECEIVE_MESSAGE);break;
            case R.id.ll_star_email:intent.putExtra("type", EmailList.STAR_EMAIL);break;
            case R.id.ll_group_email:intent.putExtra("type", EmailList.GROUP_EMAIL);break;
            case R.id.ll_draft_box:intent.putExtra("type", EmailList.DRAFT_BOX);break;
            case R.id.ll_have_sent:intent.putExtra("type", EmailList.HAVE_SENT);break;
            case R.id.ll_have_delete:intent.putExtra("type", EmailList.HAVE_DELETE);break;
            case R.id.ll_rubbish_box:intent.putExtra("type", EmailList.RUBBISH);break;
        }
        startActivity(intent);
        methodEnd("onClick");
    }
}