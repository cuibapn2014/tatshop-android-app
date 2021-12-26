package com.example.tatshop.api;

import com.example.tatshop.model.Bill;
import com.example.tatshop.model.CodeDiscount;
import com.example.tatshop.model.Comment;
import com.example.tatshop.model.Metadata;
import com.example.tatshop.model.Product;
import com.example.tatshop.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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

    @GET("api/all-product")
    Call<List<Product>> getListProduct(@Query("tokenAccess") String token);

    @GET("api/bill")
    Call<List<Bill>> getListBill(@Query("tokenAccess") String token);

    @GET("api/reply")
    Call<List<Comment>> getListTotalComment(@Query("tokenAccess") String token);

    @GET("api/reply/{id}")
    Call<List<Comment>> getListComment(@Path("id") int id, @Query("tokenAccess") String token);

    @POST("api/bill")
    Call<Bill> createBill(@Body Bill bill, @Query("tokenAccess") String token);

    @FormUrlEncoded
    @POST("api/login")
    Call<User> getLogin(@Field("email") String email, @Field("password") String password, @Query("tokenAccess") String token);

    @GET("api/codeDiscount/{code}")
    Call<CodeDiscount> getCode(@Path("code") String code, @Query("tokenAccess") String token);

    @GET("api/search/{keyword}")
    Call<List<Product>> getResultSearch(@Path("keyword") String code, @Query("tokenAccess") String token);

    @GET("api/login-fb/{email}")
    Call<User> getMatchesEmail(@Path("email") String email, @Query("tokenAccess") String token);

    @POST("api/user")
    Call<User> signupWithFacebook(@Body User user, @Query("tokenAccess") String token);

    @FormUrlEncoded
    @POST("api/user")
    Call<User> signupAccount(@Field("email") String email, @Field("password") String password, @Query("tokenAccess") String token);

    @PUT("api/user/{id}")
    Call<User> updateUser(@Body User user, @Path("id") int id, @Query("tokenAccess") String token);

    @GET("api/follow-bill/{email}")
    Call<List<Bill>> getBills(@Path("email") String email, @Query("tokenAccess") String token);

    @POST("api/reply")
    Call<Comment> createComment(@Body Comment comment, @Query("tokenAccess") String token);
}
