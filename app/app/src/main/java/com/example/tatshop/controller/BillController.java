package com.example.tatshop.controller;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;

import com.example.tatshop.CheckoutActivity;
import com.example.tatshop.R;
import com.example.tatshop.api.ApiService;
import com.example.tatshop.model.Bill;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BillController {
    private final String token = "f0ecce8d027f556c369a1d24d237b18b272a010df5896f8abe05e98accabd261";

    public void addBill(Bill bill, Activity context) {
        AppCompatButton btnOrder = (AppCompatButton) context.findViewById(R.id.btn_order_now);
        ApiService.apiService.createBill(bill, this.token).enqueue(new Callback<Bill>() {
            @Override
            public void onResponse(Call<Bill> call, Response<Bill> response) {
                Log.e("TAG", "success");
                CheckoutActivity.setCheck(true);
                btnOrder.setEnabled(true);
                btnOrder.setText("Đặt hàng ngay");
                context.finish();
            }

            @Override
            public void onFailure(Call<Bill> call, Throwable t) {
                btnOrder.setEnabled(true);
                btnOrder.setText("Đặt hàng ngay");
                Log.e("TAG", "Error");
            }
        });
    }
}
