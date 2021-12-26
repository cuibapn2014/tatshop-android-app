package com.example.tatshop.controller;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.example.tatshop.api.ApiService;
import com.example.tatshop.model.Comment;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentController {
    private final String token = "f0ecce8d027f556c369a1d24d237b18b272a010df5896f8abe05e98accabd261";

    public void addComment(Comment comment, Activity context) {
        ApiService.apiService.createComment(comment, token).enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(Call<Comment> call, Response<Comment> response) {
                Toast.makeText(context, "Đã thêm đánh giá", Toast.LENGTH_SHORT).show();
                Log.e("TAG", new Gson().toJson(response.body()));
            }

            @Override
            public void onFailure(Call<Comment> call, Throwable t) {
                Toast.makeText(context, "Lỗi", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
