package com.wangyou.qqEmail.activity;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wangyou.qqEmail.R;
import com.wangyou.qqEmail.adapter.EmailRecyclerViewAdapter;
import com.wangyou.qqEmail.entity.Email;
import com.wangyou.qqEmail.fragment.WriteEmailFragment;
import com.wangyou.qqEmail.view.ItemTouchHelperCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 参考：https://www.material.io/的官方app
 */
public class EmailList extends BaseActivity {

    private ImageView ivReturnPage;
    private ImageView ivWriteEmail;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView tvListTitle;
    private TextView tvSpaceBox;
    private EmailRecyclerViewAdapter adapter;
    private List<Email> data = new ArrayList<>();
    private int type;

    // 收件箱
    public static final int RECEIVE_MESSAGE = 0;
    // 星标邮件
    public static final int STAR_EMAIL = 1;
    // 群邮件
    public static final int GROUP_EMAIL = 2;
    // 草稿箱
    public static final int DRAFT_BOX = 3;
    // 已发送
    public static final int HAVE_SENT = 4;
    // 已删除
    public static final int HAVE_DELETE = 5;
    // 垃圾箱
    public static final int RUBBISH = 6;

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
            WriteEmailFragment.newInstance().show(getSupportFragmentManager(), "Email List");
        });

        tvListTitle = findViewById(R.id.tv_list_title);
        switch (type){
            case RECEIVE_MESSAGE:tvListTitle.setText("收件箱");break;
            case STAR_EMAIL: tvListTitle.setText("星标邮件");break;
            case GROUP_EMAIL:tvListTitle.setText("群邮件");break;
            case DRAFT_BOX: tvListTitle.setText("草稿箱");break;
            case HAVE_SENT: tvListTitle.setText("已发送");break;
            case HAVE_DELETE:tvListTitle.setText("已删除");break;
            case RUBBISH:tvListTitle.setText("垃圾箱");break;
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
        int type = intent.getIntExtra("type", RECEIVE_MESSAGE);
        setType(type);
        data = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            Email email = new Email();
            email.setSender("新华社" + i);
            email.setTheme("重庆煤矿事故致18人遇难");
            email.setContent("新华社重庆12月5日电（记者周闻韬、周凯）记者从重庆市永川区吊水洞煤矿安全事故应急救援指挥部获悉，截至5日7时，救援人员已成功救出幸存者1名、发现遇难者18名，搜救工作仍在紧张进行中。");
            email.setDate("2020/12/5");
            Random random = new Random(System.currentTimeMillis());
            boolean isRead = random.nextBoolean();
            email.setRead(isRead);
            // 已读的邮件才能star
            if (isRead){
                email.setDraft(random.nextBoolean());
                email.setStar(random.nextBoolean());
            }
            email.setType(this.type);
            switch (email.getType()){
                case RECEIVE_MESSAGE:email.setDraft(false);break;
                case STAR_EMAIL: email.setRead(true);email.setStar(true);break;
                case GROUP_EMAIL:break;
                case DRAFT_BOX: email.setRead(true);email.setDraft(true);break;
                case HAVE_SENT: email.setRead(true);email.setDraft(false);break;
                case HAVE_DELETE:
                case RUBBISH:email.setRead(true);break;
                default:email.setRead(false);break;
            }
            data.add(email);
        }
        methodEnd("initData");
    }

    public void setType(int type){
        this.type = type;
    }
}