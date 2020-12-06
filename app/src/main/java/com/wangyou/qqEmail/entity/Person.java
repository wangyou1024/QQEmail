package com.wangyou.qqEmail.entity;

import java.io.Serializable;

public class Person implements Serializable {
    private String name; // 姓名
    private String email;
    private String sortLetters; // 姓名的字母

    public Person() {
    }

    public Person(String name, String email, String sortLetters) {
        this.name = name;
        this.email = email;
        this.sortLetters = sortLetters;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSortLetters() {
        return sortLetters;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }
}
