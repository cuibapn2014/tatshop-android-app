package com.example.tatshop.storage;

import android.content.Context;
import android.content.SharedPreferences;

public class SaveData {
    private static final String MY_PREF_NAME = "MY_PREF_NAME";
    private Context context;

    public SaveData(Context context) {
        this.context = context;
    }

    public void putString(String key, String list) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, list);
        editor.apply();
    }

    public String getString(String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }

    public void putStrUser(String key, String user) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("USER_PREF", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, user);
        editor.apply();
    }

    public String getStrUser(String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("USER_PREF", Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }

    public void putStrLatestUpdate(String key, String list) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LATEST_UPDATE_PREF", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, list);
        editor.apply();
    }

    public String getStrLatestUpdate(String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LATEST_UPDATE_PREF", Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }

    public void putStrListSeen(String key, String list) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("SEEN_PREF", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, list);
        editor.apply();
    }

    public String getStrListSeen(String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("SEEN_PREF", Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }

    public void putStrListProduct(String key, String list) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("PRO_PREF", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, list);
        editor.apply();
    }

    public String getStrListProduct(String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("PRO_PREF", Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }
}
