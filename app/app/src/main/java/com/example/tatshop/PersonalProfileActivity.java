package com.example.tatshop;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tatshop.controller.UserController;
import com.example.tatshop.model.User;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

public class PersonalProfileActivity extends AppCompatActivity {
    private EditText fullname, phone, email, address;
    private Button btnUpdate;
    private ImageView bg;
    private ShapeableImageView avatar;
    private User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_info_activity);
        getSupportActionBar().setTitle("Thông tin cá nhân");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mapping();
        loadData();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setName(fullname.getText().toString());
                user.setPhone(phone.getText().toString().trim());
                if (!checkPhone(user)) {
                    user.setAddress(address.getText().toString());
                    UserController.updateUser(user, PersonalProfileActivity.this);
                } else
                    Toast.makeText(PersonalProfileActivity.this, "Số điện thoại không hợp lệ", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void mapping() {
        fullname = (EditText) findViewById(R.id.fullname);
        phone = (EditText) findViewById(R.id.phone_number);
        email = (EditText) findViewById(R.id.email);
        address = (EditText) findViewById(R.id.address);
        btnUpdate = (Button) findViewById(R.id.btn_update);
        bg = (ImageView) findViewById(R.id.bg_user);
        avatar = (ShapeableImageView) findViewById(R.id.img_avatar);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void loadData() {
        user = UserController.getUSER();
        fullname.setText(user.getName());
        phone.setText(user.getPhone());
        email.setText(user.getEmail());
        address.setText(user.getAddress());

        Picasso.get().load(user.getImage()).into(bg);
        Picasso.get().load(user.getImage()).into(avatar);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        this.finish();
        super.onBackPressed();
    }

    private boolean checkPhone(User user) {
        String phone = user.getPhone().contains("+84") ? user.getPhone().replace("+84", "0") : user.getPhone();
        if (phone.length() > 10 || phone.length() < 10 || phone.charAt(0) != '0')
            return true;
        else
            return false;
    }

}
