package com.example.tatshop.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.tatshop.CategoryActivity;
import com.example.tatshop.DetailProductActivity;
import com.example.tatshop.R;
import com.example.tatshop.adapter.PhotoAdapter;
import com.example.tatshop.adapter.ProductAdapter;
import com.example.tatshop.model.Photo;
import com.example.tatshop.model.Product;
import com.example.tatshop.module.Helper;
import com.example.tatshop.storage.DataLocalManger;
import com.google.android.material.card.MaterialCardView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class HomeFragment extends Fragment {
    private PhotoAdapter photoAdapter;
    private ViewPager viewPager;
    private List<Photo> listPhoto;
    private CircleIndicator indicator;
    private Timer timer;
    private List<Product> listProduct;
    private GridView gridView;
    private MaterialCardView fsMen, fsWomen;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapping(view);
        loadData(view);
        listPhoto = getListPhoto();
        photoAdapter = new PhotoAdapter(listPhoto);
        viewPager.setAdapter(photoAdapter);
        indicator.setViewPager(viewPager);
        autoSlideImage();

        fsMen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CategoryActivity.class);
                intent.putExtra("IDCategory",1);
                startActivity(intent);
            }
        });

        fsWomen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CategoryActivity.class);
                intent.putExtra("IDCategory",0);
                startActivity(intent);
            }
        });
    }

    private List<Photo> getListPhoto() {
        List<Photo> listImage = new ArrayList<>();
        listImage.add(new Photo(R.drawable.top_background));
        listImage.add(new Photo(R.drawable.banner));
        return listImage;
    }

    private void autoSlideImage() {
        if (listPhoto.isEmpty() || listPhoto == null || viewPager == null)
            return;

        if (timer == null)
            timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        int currentItem = viewPager.getCurrentItem();
                        int totalItem = listPhoto.size() - 1;
                        if (currentItem < totalItem) {
                            currentItem++;
                            viewPager.setCurrentItem(currentItem);
                        } else {
                            viewPager.setCurrentItem(0);
                        }
                    }
                });
            }
        }, 1000, 3000);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (this.timer != null) {
            this.timer.cancel();
        }
    }

    public void mapping(View v) {
        gridView = (GridView) v.findViewById(R.id.gridview_product);
        viewPager = v.findViewById(R.id.view_pager_photo);
        indicator = v.findViewById(R.id.indicator);
        fsMen = v.findViewById(R.id.male);
        fsWomen = v.findViewById(R.id.female);
    }

    public void loadData(View v) {
        listProduct = DataLocalManger.getListLatestUpdate();
        ProductAdapter productAdapter = new ProductAdapter(getActivity(), listProduct, R.layout.item_custom_grid);
        gridView.setAdapter(productAdapter);
        Helper.setGridViewHeight(gridView);
        Gson gson = new Gson();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), DetailProductActivity.class);
                intent.putExtra("Product", gson.toJson(listProduct.get(position)));
                startActivity(intent);
            }
        });
    }
}
