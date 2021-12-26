package com.example.tatshop;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tatshop.adapter.BillAdapter;
import com.example.tatshop.api.ApiService;
import com.example.tatshop.controller.UserController;
import com.example.tatshop.model.Bill;
import com.example.tatshop.module.Helper;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FollowOrderActivity extends AppCompatActivity {
    private ListView listBill;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.follow_order_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Theo dõi đơn hàng");
        mapping();
        callApi();
    }

    public void mapping() {
        listBill = (ListView) findViewById(R.id.list_bill);
    }

    private void callApi() {
        String email = null;
        final String token = "f0ecce8d027f556c369a1d24d237b18b272a010df5896f8abe05e98accabd261";
        if (UserController.getUSER() != null)
            email = UserController.getUSER().getEmail();
        ApiService.apiService.getBills(email, token).enqueue(new Callback<List<Bill>>() {
            @Override
            public void onResponse(Call<List<Bill>> call, Response<List<Bill>> response) {
                Log.e("TAG", new Gson().toJson(response.body()));
                BillAdapter adapter = new BillAdapter(FollowOrderActivity.this, response.body(), R.layout.list_item_bill);
                listBill.setAdapter(adapter);
                Helper.setListViewHeight(listBill);
            }

            @Override
            public void onFailure(Call<List<Bill>> call, Throwable t) {
                Log.e("TAG", "Error");
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            this.finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
