package com.example.tatshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.notification.AHNotification;

import com.example.tatshop.adapter.ViewPagerAdapter;
import com.example.tatshop.api.ApiService;
import com.example.tatshop.controller.CartController;
import com.example.tatshop.model.Bill;
import com.example.tatshop.model.ItemCart;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ViewPager2 viewPager2;
    private ViewPagerAdapter viewPagerAdapter;
    public static AHBottomNavigation bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        CartController.setListCartItem(DataLocalManger.getListCartItem());
        mapping();

        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.nav_home, R.drawable.ic_home, R.color.white);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.nav_search, R.drawable.ic_search, R.color.white);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.nav_cart, R.drawable.ic_shopping_cart, R.color.white);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem(R.string.nav_user, R.drawable.ic_account_circle, R.color.white);

        bottomNavigationView.setColored(false);
        bottomNavigationView.setAccentColor(Color.parseColor("#20c997"));

        bottomNavigationView.addItem(item1);
        bottomNavigationView.addItem(item2);
        bottomNavigationView.addItem(item3);
        bottomNavigationView.addItem(item4);

        changeData();

        bottomNavigationView.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                viewPager2.setCurrentItem(position);
                return true;
            }
        });


        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                bottomNavigationView.setCurrentItem(position);
            }
        });

    }

    public void changeData() {
        int amountCartItem = CartController.getListCartItem().size();
        AHNotification notification = new AHNotification.Builder()
                .setText(String.valueOf(amountCartItem))
                .setBackgroundColor(ContextCompat.getColor(this, R.color.colorBottomNavigationNotification))
                .setTextColor(ContextCompat.getColor(this, R.color.white))
                .build();
        bottomNavigationView.setNotification(notification, 2);
    }

    @Override
    protected void onResume() {
        super.onResume();
        changeData();
    }

    private void mapping() {
        viewPager2 = findViewById(R.id.view_pager);
        bottomNavigationView = findViewById(R.id.bottom_nav);
        viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager2.setAdapter(viewPagerAdapter);
    }

}