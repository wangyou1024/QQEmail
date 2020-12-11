package com.wangyou.qqEmail.activity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.wangyou.qqEmail.R;
import com.wangyou.qqEmail.adapter.PersonRecyclerViewAdapter;
import com.wangyou.qqEmail.data.DBOpenHelper;
import com.wangyou.qqEmail.entity.Person;
import com.wangyou.qqEmail.util.CharacterParser;
import com.wangyou.qqEmail.util.PinyinComparator;
import com.wangyou.qqEmail.view.SideBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * 主体参考：https://blog.csdn.net/xiaanming/article/details/12684155
 * RecyclerView改进：https://stackoverflow.com/questions/31235183/recyclerview-how-to-smooth-scroll-to-top-of-item-on-a-certain-position
 */
public class AddressListActivity extends BaseActivity {

    private ImageView ivReturnPage;
    private ImageView ivAddPerson;
    private TextView tvKeyChar;
    private SideBar sideBar;
    private RecyclerView recyclerView;
    private PersonRecyclerViewAdapter personRecyclerViewAdapter;
    private List<Person> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_address_list;
    }

    @Override
    protected void initView() {
        methodStart("initView");

        ivReturnPage = findViewById(R.id.iv_return_page);
        ivReturnPage.setOnClickListener(v-> finish());

        ivAddPerson = findViewById(R.id.iv_add_person);
        ivAddPerson.setOnClickListener(v->toastShow("点击了添加用户"));

        tvKeyChar = findViewById(R.id.tv_key_char);

        recyclerView = findViewById(R.id.rv_person);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        personRecyclerViewAdapter = new PersonRecyclerViewAdapter(this);
        personRecyclerViewAdapter.setOnItemClickListener(obj -> {
            methodStart("PersonRecyclerView onClick");
            Person person = (Person) obj;

            toastShow(person.getName());
            methodEnd("PersonRecyclerView onClick");
        });

        recyclerView.setAdapter(personRecyclerViewAdapter);
        personRecyclerViewAdapter.setMItems(data);

        sideBar = findViewById(R.id.sb_select);
        sideBar.setTextView(tvKeyChar);
        sideBar.setOnTouchingLetterChangedListener(s -> {
            methodStart("sideBar onTouch"+s);
            int position = personRecyclerViewAdapter.getPositionForSection(s.charAt(0));

            LinearSmoothScroller linearSmoothScroller = new LinearSmoothScroller(this) {
                @Override
                protected int getVerticalSnapPreference() {
                    return LinearSmoothScroller.SNAP_TO_START;
                }
            };
            linearSmoothScroller.setTargetPosition(position);
            recyclerView.getLayoutManager().startSmoothScroll(linearSmoothScroller);
            methodEnd("sideBar"+s);
        });
        methodStart("initView onTouch");

    }

    @Override
    protected void initData() {
        methodStart("initData");
        CharacterParser characterParser = CharacterParser.getInstance();
        DBOpenHelper dbOpenHelper = new DBOpenHelper(this);
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from person", new String[]{});
        data = new ArrayList<>();
        while (cursor.moveToNext()){
            Person person = new Person();
            person.setName(cursor.getString(cursor.getColumnIndex("name")));
            person.setEmail(cursor.getString(cursor.getColumnIndex("email")));
            String pinyin = characterParser.getSelling(person.getName()).substring(0,1).toUpperCase();
            // 字母正常排序，其他标点统一为#
            if (pinyin.matches("[A-Z]")){
                person.setSortLetters(pinyin);
            }else{
                person.setSortLetters("#");
            }
            data.add(person);
        }
        db.close();
        cursor.close();
        Collections.sort(data, new PinyinComparator());
        methodEnd("initData");
    }
}