package com.wangyou.qqEmail.util;

import android.content.ContentValues;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class CastContentValues {
    public static ContentValues getContentValues(Object obj){
        Log.i("CastContents", "getContentValues start");
        ContentValues contentValues = new ContentValues();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field: fields){
            if (Modifier.isFinal(field.getModifiers())){
                continue;
            }
            String name = field.getName();
            String type = String.valueOf(field.getType());
            String methodName = name.substring(0,1).toUpperCase()+name.substring(1);
            if (type.equals("boolean")){
                methodName = "is"+methodName;
            }else{
                methodName = "get"+methodName;
            }
            try {
                Method method = obj.getClass().getMethod(methodName);
                String result = method.invoke(obj).toString();
                if (result.equals("true")){
                    contentValues.put(name, 1);
                } else if (result.equals("false")){
                    contentValues.put(name, 0);
                } else {
                    contentValues.put(name, result);
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        Log.i("CastContents", "getContentValues end");
        return contentValues;
    }
}
