package com.wangyou.qqEmail.entity;

import java.io.Serializable;

public class Email implements Serializable {
    private String eid;
    private String receivePerson;
    private String sender;
    private String theme;
    private String content;
    private String date;
    private int type;
    private boolean read;
    private boolean draft;
    private boolean star;

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

    public Email() {
        this.eid = "";
        this.receivePerson = "";
        this.sender = "";
        this.theme = "";
        this.content = "";
        this.read = false;
        this.draft = false;
        this.star = false;
        this.date = "2020/12/5";
        this.type = 0;
    }

    public Email(String eid, String receivePerson, String sender, String theme, String content, String date, int type, boolean read, boolean draft, boolean star) {
        this.eid = eid;
        this.receivePerson = receivePerson;
        this.sender = sender;
        this.theme = theme;
        this.content = content;
        this.date = date;
        this.type = type;
        this.read = read;
        this.draft = draft;
        this.star = star;
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public String getReceivePerson() {
        return receivePerson;
    }

    public void setReceivePerson(String receivePerson) {
        this.receivePerson = receivePerson;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public boolean isDraft() {
        return draft;
    }

    public void setDraft(boolean draft) {
        this.draft = draft;
    }

    public boolean isStar() {
        return star;
    }

    public void setStar(boolean star) {
        this.star = star;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
