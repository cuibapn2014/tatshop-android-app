package com.example.tatshop;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.tatshop.controller.BillController;
import com.example.tatshop.controller.CartController;
import com.example.tatshop.controller.UserController;
import com.example.tatshop.model.Bill;
import com.example.tatshop.model.Item;
import com.example.tatshop.model.Product;
import com.example.tatshop.model.User;

import java.util.ArrayList;
import java.util.List;

public class CheckoutActivity extends AppCompatActivity {
    private EditText name, address, phone, email;
    private RadioButton method;
    private AppCompatButton btnOrder;
    private static boolean check = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout_activity);
        getSupportActionBar().setTitle("Đặt hàng");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mapping();
        loadData();

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int total = CartController.getTotalPrice();
                Bill bill = new Bill();
                bill.setName(name.getText().toString());
                bill.setPhone(phone.getText().toString().trim());
                bill.setAddress(address.getText().toString());
                bill.setPay(1);
                bill.setStt(1);
                bill.setTotal(total);
                bill.setDiscount(CartController.getTotalDiscount());
                bill.setItem(getItemCart());
                bill.setEmail(email.getText().toString().trim());
                if (bill.getItem().size() > 0) {
                    checkInfo(bill, CheckoutActivity.this);
                } else
                    Toast.makeText(CheckoutActivity.this, "Bạn chưa có sản phẩm nào trong giỏ!", Toast.LENGTH_SHORT).show();
                phone.setEnabled(true);
                email.setEnabled(true);
            }
        });
    }

    public void mapping() {
        name = (EditText) findViewById(R.id.name);
        address = (EditText) findViewById(R.id.address);
        phone = (EditText) findViewById(R.id.phone_number);
        email = (EditText) findViewById(R.id.email);
        method = (RadioButton) findViewById(R.id.method_payment);
        btnOrder = (AppCompatButton) findViewById(R.id.btn_order_now);
    }

    public void loadData() {
        User user = UserController.getUSER();
        if (user != null) {
            name.setText(user.getName());
            address.setText(user.getAddress());
            phone.setText(user.getPhone());
            email.setText(user.getEmail());
            if (user.getEmail() != "" && user.getPhone() != "") {
                phone.setEnabled(false);
                email.setEnabled(false);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            this.finish();
        return super.onOptionsItemSelected(item);
    }

    private final List<Item> getItemCart() {
        List<Item> list = new ArrayList<>();
        for (Product product : CartController.getListCartItem()) {
            Item item = new Item();
            item.setName(product.getTitle());
            item.setId_product(product.getId());
            item.setAttr(product.getAttr().get(0).getValue() + " - " + product.getAttr().get(1).getValue());
            item.setImage(product.getThumbnail());
            item.setPrice(product.getPrice());
            item.setQty(product.getQty());
            item.setStatus(0);
            list.add(item);
        }

        return list;
    }

    private void checkInfo(Bill bill, Activity context) {
        if (bill.getName().equals(""))
            Toast.makeText(context, "Bạn chưa nhập tên", Toast.LENGTH_SHORT).show();
        else if (bill.getPhone().equals(""))
            Toast.makeText(context, "Bạn chưa nhập số điện thoại", Toast.LENGTH_SHORT).show();
        else if (bill.getAddress().equals(""))
            Toast.makeText(context, "Bạn chưa nhập địa chỉ nhận hàng", Toast.LENGTH_SHORT).show();
        else if (checkPhone(bill))
            Toast.makeText(context, "Số điện thoại không hợp lệ", Toast.LENGTH_SHORT).show();
        else {
            new BillController().addBill(bill, context);
            btnOrder.setEnabled(false);
            btnOrder.setText("Đang xác nhận..");
        }
        if (isCheck()) {
            this.finish();
        }

    }

    private boolean checkPhone(Bill bill) {
        String phone = bill.getPhone().contains("+84") ? bill.getPhone().replace("+84", "0") : bill.getPhone();
        Log.e("String", phone);
        if (phone.length() > 10 || phone.length() < 10 || phone.charAt(0) != '0')
            return true;
        else
            return false;
    }

    public static boolean isCheck() {
        return check;
    }

    public static void setCheck(boolean check) {
        CheckoutActivity.check = check;
    }
}
