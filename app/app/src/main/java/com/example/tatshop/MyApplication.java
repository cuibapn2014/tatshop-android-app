package com.example.tatshop;

import android.app.Application;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DataLocalManger.init(getApplicationContext());
    }
}
