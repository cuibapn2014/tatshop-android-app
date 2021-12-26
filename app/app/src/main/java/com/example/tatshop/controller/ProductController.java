package com.example.tatshop.controller;

import android.app.Activity;
import android.widget.Toast;

import com.example.tatshop.api.ApiService;
import com.example.tatshop.model.Metadata;
import com.example.tatshop.model.Product;
import com.example.tatshop.storage.DataLocalManger;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductController {
    private List<Product> listProduct;
    private static List<Product> listSeen;
    private final static String token = "f0ecce8d027f556c369a1d24d237b18b272a010df5896f8abe05e98accabd261";

    public void getLatestUpdate(Activity context) {
        ApiService.apiService.getMetaData(1, token)
                .enqueue(new Callback<Metadata>() {
                    @Override
                    public void onResponse(Call<Metadata> call, Response<Metadata> response) {
                        Metadata metadata = response.body();
                        listProduct = metadata.getListProduct();
                        DataLocalManger.setListLatestUpdate(listProduct);
                    }

                    @Override
                    public void onFailure(Call<Metadata> call, Throwable t) {
                        Toast.makeText(context, "Không thể kết nối tới API!", Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void getAllProduct(Activity context){
        ApiService.apiService.getListProduct(token).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                List<Product> list = response.body();
                DataLocalManger.setListProduct(list);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(context, "Không thể kết nối tới API!", Toast.LENGTH_LONG).show();
            }
        });
    }

    public List<Product> getListProduct() {
        return listProduct;
    }

    public void setListProduct(List<Product> listProduct) {
        this.listProduct = listProduct;
    }

    public static List<Product> getListSeen() {
        return listSeen;
    }

    public static void setListSeen(List<Product> listSeen) {
        ProductController.listSeen = listSeen;
    }
}
