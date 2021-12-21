package com.example.tatshop.api;

import com.example.tatshop.model.Bill;
import com.example.tatshop.model.Comment;
import com.example.tatshop.model.Metadata;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    Gson gson = new GsonBuilder()
            .setDateFormat("HH:mm:ss dd-MM-yyyy")
            .create();

    ApiService apiService = new Retrofit.Builder()
            .baseUrl("https://tatshop.tech/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

    @GET("api/products")
    Call<Metadata> getMetaData(@Query("page") int page,
                               @Query("tokenAccess") String token);

    @GET("api/bill")
    Call<List<Bill>> getListBill(@Query("tokenAccess") String token);

    @GET("api/reply")
    Call<List<Comment>> getListTotalComment(@Query("tokenAccess") String token);

    @GET("api/reply/{id}")
    Call<List<Comment>> getListComment(@Path("id") int id, @Query("tokenAccess") String token);

    @POST("api/bill")
    Call<Bill> createBill(@Body Bill bill, @Query("tokenAccess") String token);
}
