package com.example.tatshop.storage;

import android.app.Application;

import com.example.tatshop.storage.DataLocalManger;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DataLocalManger.init(getApplicationContext());
    }
}
