package com.wangyou.qqEmail.util;

import com.wangyou.qqEmail.entity.Person;

import java.util.Comparator;

public class PinyinComparator implements Comparator<Person> {
    @Override
    public int compare(Person o1, Person o2) {
        if (o2.getSortLetters().equals("#")) {
            return -1;
        } else if (o1.getSortLetters().equals("#")) {
            return 1;
        } else {
            return o1.getSortLetters().compareTo(o2.getSortLetters());
        }
    }
}
