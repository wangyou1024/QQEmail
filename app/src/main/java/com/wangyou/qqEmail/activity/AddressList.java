package com.wangyou.qqEmail.activity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.wangyou.qqEmail.R;
import com.wangyou.qqEmail.adapter.PersonRecyclerViewAdapter;
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
public class AddressList extends BaseActivity {

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
        String[] names = {"{}","艾克a","爸爸b","裁判c","电科d","环境h","就j","额e","i",
                "款爷k","李明l","张三z","王五w","杏杏x","可可k","皮皮p","格格g","快快k",
                "夫夫f","看k","老板l","妈妈m","奶奶n","欧布o","婆婆p","强强q","荣荣r",
                "水水s","天天t","u","v","我w","喜喜x","幺幺y","智障z","哥g","阿布a"};
        data = new ArrayList<>();
        for (int i = 0; i < names.length; i++){
            Person person = new Person();
            person.setName(names[i]);
            person.setEmail("baiyou1024@qq.com");
            String pinyin = characterParser.getSelling(person.getName()).substring(0,1).toUpperCase();
            // 字母正常排序，其他标点统一为#
            if (pinyin.matches("[A-Z]")){
                person.setSortLetters(pinyin);
            }else{
                person.setSortLetters("#");
            }
            data.add(person);
        }
        Collections.sort(data, new PinyinComparator());
        methodEnd("initData");
    }
}