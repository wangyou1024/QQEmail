package com.wangyou.qqEmail.activity;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wangyou.qqEmail.R;
import com.wangyou.qqEmail.adapter.EmailRecyclerViewAdapter;
import com.wangyou.qqEmail.data.DBOpenHelper;
import com.wangyou.qqEmail.entity.Email;
import com.wangyou.qqEmail.fragment.WriteEmailFragment;
import com.wangyou.qqEmail.view.ItemTouchHelperCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 参考：https://www.material.io/的官方app
 */
public class EmailListActivity extends BaseActivity {

    private ImageView ivReturnPage;
    private ImageView ivWriteEmail;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView tvListTitle;
    private TextView tvSpaceBox;
    private EmailRecyclerViewAdapter adapter;
    private List<Email> data = new ArrayList<>();
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_email_list;
    }

    @Override
    protected void initView() {
        methodStart("initView");
        ivReturnPage = findViewById(R.id.iv_return_page);
        ivReturnPage.setOnClickListener((view) -> {
            this.finish();
        });

        ivWriteEmail = findViewById(R.id.iv_write_email);
        ivWriteEmail.setOnClickListener((view) -> {
            WriteEmailFragment writeEmailFragment = WriteEmailFragment.newInstance();
            writeEmailFragment.setSendEmail(email->{
                // 如果当前处在已发送的列表时，更新数据
                if (this.type == Email.HAVE_SENT){
                    adapter.addItem(0, email);
                }
            });
            writeEmailFragment.show(getSupportFragmentManager(), "Email List");
        });

        tvListTitle = findViewById(R.id.tv_list_title);
        switch (type){
            case Email.RECEIVE_MESSAGE:tvListTitle.setText("收件箱");break;
            case Email.STAR_EMAIL: tvListTitle.setText("星标邮件");break;
            case Email.GROUP_EMAIL:tvListTitle.setText("群邮件");break;
            case Email.DRAFT_BOX: tvListTitle.setText("草稿箱");break;
            case Email.HAVE_SENT: tvListTitle.setText("已发送");break;
            case Email.HAVE_DELETE:tvListTitle.setText("已删除");break;
            case Email.RUBBISH:tvListTitle.setText("垃圾箱");break;
            default:break;
        }

        mRecyclerView = findViewById(R.id.recycler_view_recycler_view);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        adapter = new EmailRecyclerViewAdapter(this);
        adapter.setOnItemClickListener(obj -> {
            Email email = (Email) obj;
            toastShow(email.getSender());
        });
        mRecyclerView.setAdapter(adapter);
        adapter.setItems(data);

        // 绑定滑动删除与移动动画
        ItemTouchHelper.Callback callback = new ItemTouchHelperCallback(adapter);
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);

        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout_recycler_view);
        swipeRefreshLayout.setColorSchemeResources(R.color.blue);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            new Handler().postDelayed(() -> swipeRefreshLayout.setRefreshing(false), 2000);
            initData();
            adapter.setItems(data);
        });

        tvSpaceBox = findViewById(R.id.tv_space_box);
        if (data.size() != 0){
            tvSpaceBox.setVisibility(View.GONE);
        }
        methodEnd("initView");
    }

    @Override
    protected void initData() {
        methodStart("initData");
        Intent intent = getIntent();
        int type = intent.getIntExtra("type", Email.RECEIVE_MESSAGE);
        setType(type);
        data = new ArrayList<>();
        DBOpenHelper dbOpenHelper = new DBOpenHelper(this);
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from email where type=?", new String[]{this.type + ""});
        while (cursor.moveToNext()){
            Email email = new Email();
            email.setEid(cursor.getString(cursor.getColumnIndex("eid")));
            email.setSender(cursor.getString(cursor.getColumnIndex("sender")));
            email.setTheme(cursor.getString(cursor.getColumnIndex("theme")));
            email.setContent(cursor.getString(cursor.getColumnIndex("content")));
            email.setDate(cursor.getString(cursor.getColumnIndex("date")));
            email.setRead(cursor.getInt(cursor.getColumnIndex("read")) != 0);
            email.setDraft(cursor.getInt(cursor.getColumnIndex("draft")) != 0);
            email.setStar(cursor.getInt(cursor.getColumnIndex("star")) != 0);
            email.setType(this.type);
            data.add(email);
        }
        methodEnd("initData");
    }

    public void setType(int type){
        this.type = type;
    }
}