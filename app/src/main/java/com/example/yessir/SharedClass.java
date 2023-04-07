package com.example.yessir;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SharedClass {


    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;
    private static final String ISLOGIN = "islogin";
    private static final String NAME = "fullname";
    private static final String DEPARTMENT = "department";
    private static final String REG_NUM = "regnum";
    private static final String PHONE_NUM = "phone";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";

    SharedClass(Context context) {
        sharedPreferences = context.getSharedPreferences("userloginsession", Context.MODE_PRIVATE);
        this.context = context;
        editor = sharedPreferences.edit();
    }

    public void createloginsession(String name, String department, String regnum, String phone, String email, String password) {
        editor.putBoolean(ISLOGIN, true);
        editor.putString(NAME, name);
        editor.putString(DEPARTMENT, department);
        editor.putString(REG_NUM, regnum);
        editor.putString(PHONE_NUM, phone);
        editor.putString(EMAIL, email);
        editor.putString(PASSWORD, password);
        editor.commit();
    }

    public HashMap<String, String> getuserdetails() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(NAME, sharedPreferences.getString(NAME, null));
        map.put(DEPARTMENT, sharedPreferences.getString(DEPARTMENT, null));
        map.put(REG_NUM, sharedPreferences.getString(REG_NUM, null));
        map.put(PHONE_NUM, sharedPreferences.getString(PHONE_NUM, null));
        map.put(EMAIL, sharedPreferences.getString(EMAIL, null));
        map.put(PASSWORD, sharedPreferences.getString(PASSWORD, null));
        return map;
    }

    public boolean getuserauth() {
        if (sharedPreferences.getBoolean(ISLOGIN, false)) {
            return true;
        } else return false;
    }
}
