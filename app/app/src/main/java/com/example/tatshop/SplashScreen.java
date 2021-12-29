package com.example.tatshop;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tatshop.controller.CartController;
import com.example.tatshop.controller.ProductController;
import com.example.tatshop.controller.UserController;
import com.example.tatshop.model.Product;
import com.example.tatshop.storage.DataLocalManger;

import java.util.ArrayList;

public class SplashScreen extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        getSupportActionBar().hide();
        new ProductController().getLatestUpdate(this);
        new ProductController().getAllProduct(this);

        CartController.setListCartItem(DataLocalManger.getListCartItem());
        UserController.isLogin();
        if (CartController.getListCartItem() == null)
            CartController.setListCartItem(new ArrayList<Product>());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this, MainActivity.class));
                finish();
            }
        }, 3000);
    }
}
