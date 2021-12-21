package com.example.tatshop.ui;

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

import com.example.tatshop.DetailProductActivity;
import com.example.tatshop.adapter.ProductAdapter;
import com.example.tatshop.api.ApiService;
import com.example.tatshop.model.Metadata;
import com.example.tatshop.model.Photo;
import com.example.tatshop.adapter.PhotoAdapter;
import com.example.tatshop.R;
import com.example.tatshop.model.Product;
import com.example.tatshop.module.Helper;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private PhotoAdapter photoAdapter;
    private ViewPager viewPager;
    private List<Photo> listPhoto;
    private CircleIndicator indicator;
    private Timer timer;
    private List<Product> listProduct;
    private GridView gridView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager = view.findViewById(R.id.view_pager_photo);
        indicator = view.findViewById(R.id.indicator);
        listPhoto = getListPhoto();
        photoAdapter = new PhotoAdapter(listPhoto);
        viewPager.setAdapter(photoAdapter);
        indicator.setViewPager(viewPager);
        callApi(view);
        autoSlideImage();
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

    private void callApi(View view) {
        ApiService.apiService.getMetaData(1, "f0ecce8d027f556c369a1d24d237b18b272a010df5896f8abe05e98accabd261")
                .enqueue(new Callback<Metadata>() {
                    @Override
                    public void onResponse(Call<Metadata> call, Response<Metadata> response) {
                        Metadata metadata = response.body();
                        listProduct = metadata.getListProduct();
                        gridView = view.findViewById(R.id.gridview_product);
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

                    @Override
                    public void onFailure(Call<Metadata> call, Throwable t) {
                        Snackbar.make(view.findViewById(R.id.home_frag), "Không thể kết nối tới API!", Snackbar.LENGTH_LONG).show();
                    }
                });
    }
}
