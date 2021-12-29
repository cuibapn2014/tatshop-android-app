package com.example.tatshop.controller;

import android.app.Activity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tatshop.api.ApiService;
import com.example.tatshop.model.CodeDiscount;
import com.example.tatshop.model.Product;
import com.example.tatshop.storage.DataLocalManger;

import java.text.NumberFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartController {
    private static List<Product> LIST_CART_ITEM;
    public static Locale lc = Locale.getDefault();
    public static NumberFormat nf = NumberFormat.getCurrencyInstance(lc);
    private static CodeDiscount CODE_DISCOUNT;

    public CartController() {
    }

    public static List<Product> getListCartItem() {
        return LIST_CART_ITEM;
    }

    public static void setListCartItem(List<Product> listCartItem) {
        LIST_CART_ITEM = listCartItem;
    }

    public static void addCartItem(Product product) {
        if (product != null)
            LIST_CART_ITEM.add(product);
        saveDataLocal();
    }

    public static void updateCartItem(Product product, int position) {
        if (product != null)
            LIST_CART_ITEM.set(position, product);
        saveDataLocal();
    }

    public static void removeCartItem(Product product) {
        getListCartItem().remove(product);
        saveDataLocal();
    }

    public static void clearCart() {
        CODE_DISCOUNT = null;
        LIST_CART_ITEM.clear();
    }

    public static void saveDataLocal() {
        DataLocalManger.setListCartItem(LIST_CART_ITEM);
    }

    public static int getTotalPrice() {
        int TOTAL = getTotal();
        float discount;
        discount = 1 - (float) getTotalDiscount() / 100;
        return (int) (TOTAL * discount);
    }

    public static int getTotal() {
        int TOTAL = 0;
        if (getListCartItem() != null) {
            for (Iterator<Product> i = getListCartItem().iterator(); i.hasNext(); ) {
                Product product = (Product) i.next();
                float dc = 1 - (float) product.getDiscount() / 100;
                TOTAL += product.getQty() * product.getPrice() * dc;
            }
        }

        return TOTAL;
    }

    public static int getDiscount() {
        int total = getTotal();
        if (total >= 500000 && total < 1000000)
            return 5;
        else if (total > 1000000)
            return 10;
        else return 0;
    }

    public static int getTotalDiscount() {
        if (CODE_DISCOUNT != null)
            return getDiscount() + CODE_DISCOUNT.getDiscount();
        return getDiscount();
    }

    public static CodeDiscount getCodeDiscount() {
        return CODE_DISCOUNT;
    }

    public static void setCodeDiscount(CodeDiscount codeDiscount) {
        CODE_DISCOUNT = codeDiscount;
    }

    public static void applyCode(String code, TextView txtTotal, Activity context) {
        ApiService.apiService.getCode(code, "f0ecce8d027f556c369a1d24d237b18b272a010df5896f8abe05e98accabd261").enqueue(new Callback<CodeDiscount>() {
            @Override
            public void onResponse(Call<CodeDiscount> call, Response<CodeDiscount> response) {
                CODE_DISCOUNT = response.body();
                if (CODE_DISCOUNT != null && CODE_DISCOUNT.getId() != 0 && txtTotal.getText() != nf.format(0)) {
                    txtTotal.setText(CartController.nf.format(getTotalPrice()));
                    Toast.makeText(context, "Đã áp dụng mã giảm giá: " + code, Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(context, "Mã giảm giá không hợp lệ", Toast.LENGTH_SHORT).show();
                Log.e("TAG", "success");
            }

            @Override
            public void onFailure(Call<CodeDiscount> call, Throwable t) {
                CODE_DISCOUNT = null;
                txtTotal.setText(CartController.nf.format(getTotal()));
                Log.e("TAG", "Error");
            }
        });
    }
}
