package com.example.tatshop;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.tatshop.model.Product;
import com.google.gson.Gson;

import java.util.List;

public class SaveData {
    private static final String MY_PREF_NAME = "MY_PREF_NAME";
    private Context context;

    public SaveData(Context context) {
        this.context = context;
    }

    public void putString(String key, String list){
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, list);
        editor.apply();
    }

    public String getString(String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key,"");
    }
}
