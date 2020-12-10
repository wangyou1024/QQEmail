package com.wangyou.qqEmail.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.wangyou.qqEmail.entity.Email;
import com.wangyou.qqEmail.entity.Person;
import com.wangyou.qqEmail.util.CastContentValues;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DBOpenHelper extends SQLiteOpenHelper {

    public DBOpenHelper(@Nullable Context context) {
        super(context, "qq_email", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        methodStart("onCreate");
        db.execSQL("create table if not exists email (eid text primary key, receivePerson text," +
                "sender text, theme text, content text, date text, type integer, read integer, draft integer, star integer)");
        db.execSQL("create table if not exists person (name text, email text)");
        initEmail(db);
        initPerson(db);
        methodEnd("onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        methodStart("onUpgrade");
        methodEnd("onUpgrade");
    }

    private void initEmail(SQLiteDatabase db){
        methodStart("initEmail");
        Random random = new Random(System.currentTimeMillis());
        List<Email> data = new ArrayList<>();
        for (int i = 1; i <= 140; i++) {
            Email email = new Email();
            email.setEid(i+"");
            email.setSender("新华社" + i);
            email.setTheme("重庆煤矿事故致18人遇难");
            email.setContent("新华社重庆12月5日电（记者周闻韬、周凯）记者从重庆市永川区吊水洞煤矿安全事故应急救援指挥部获悉，截至5日7时，救援人员已成功救出幸存者1名、发现遇难者18名，搜救工作仍在紧张进行中。");
            email.setDate("2020/12/5");
            boolean isRead = random.nextBoolean();
            email.setRead(isRead);
            // 已读的邮件才能star
            if (isRead){
                email.setDraft(random.nextBoolean());
                email.setStar(random.nextBoolean());
            }
            // 邮件一共7个类
            email.setType(i%7);
            // 更正逻辑
            switch (email.getType()){
                case Email.RECEIVE_MESSAGE:email.setDraft(false);break;
                case Email.STAR_EMAIL: email.setRead(true);email.setStar(true);break;
                case Email.GROUP_EMAIL:break;
                case Email.DRAFT_BOX: email.setRead(true);email.setDraft(true);break;
                case Email.HAVE_SENT: email.setRead(true);email.setDraft(false);break;
                case Email.HAVE_DELETE:
                case Email.RUBBISH:email.setRead(true);break;
                default:email.setRead(false);break;
            }
            data.add(email);
        }
        for (int i = 0; i < data.size(); i++){
            ContentValues contentValues = CastContentValues.getContentValues(data.get(i));
            db.insert("email",null,contentValues);
        }
        methodEnd("initEmail");
    }

    private void initPerson(SQLiteDatabase db){
        methodStart("initPerson");
        String[] names = {"{}","艾克a","爸爸b","裁判c","电科d","环境h","就j","额e","i",
                "款爷k","李明l","张三z","王五w","杏杏x","可可k","皮皮p","格格g","快快k",
                "夫夫f","看k","老板l","妈妈m","奶奶n","欧布o","婆婆p","强强q","荣荣r",
                "水水s","天天t","u","v","我w","喜喜x","幺幺y","智障z","哥g","阿布a"};
        List<Person> data = new ArrayList<>();
        for (int i = 0; i < names.length; i++){
            Person person = new Person();
            person.setName(names[i]);
            person.setEmail("baiyou1024@qq.com");
            data.add(person);
        }
        for (int i = 0; i < data.size(); i++){
            ContentValues contentValues = new ContentValues();
            Person person = data.get(i);
            contentValues.put("name",person.getName());
            contentValues.put("email",person.getEmail());
            db.insert("person",null,contentValues);
        }
        methodEnd("initPerson");
    }

    public void methodStart(String method){
        Log.i(this.getClass().getSimpleName(), method+" start");
    }

    public void methodEnd(String method){
        Log.i(this.getClass().getSimpleName(),method+" end");
    }
}
