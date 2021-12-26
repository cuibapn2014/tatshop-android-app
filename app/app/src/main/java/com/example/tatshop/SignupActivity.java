package com.example.tatshop;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.tatshop.controller.UserController;

import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {
    private AppCompatButton btnSignup;
    private EditText mEmail, mPassword, mConfirm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);
        getSupportActionBar().hide();
        mapping();

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString();
                if (checkInfo()) {
                    UserController.signup(email, password, SignupActivity.this);
                    btnSignup.setEnabled(false);
                    btnSignup.setBackground(getResources().getDrawable(R.color.teal_700));
                    btnSignup.setText("Waiting for..");
                }
            }
        });

    }

    private void mapping() {
        btnSignup = (AppCompatButton) findViewById(R.id.btn_signup);
        mEmail = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);
        mConfirm = (EditText) findViewById(R.id.confirm_password);
    }

    private boolean checkInfo() {
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();
        String confirm = mConfirm.getText().toString();
        String regexPattern = "^(.+)@(\\S+)$";
        if (email == "" || password == "" || confirm == "") {
            Toast.makeText(SignupActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return false;
        } else if (password.length() < 6) {
            Toast.makeText(SignupActivity.this, "Mật khẩu ít nhất phải 6 ký tự", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!Pattern.compile(regexPattern).matcher(email).matches()) {
            Toast.makeText(SignupActivity.this, "Email không hợp lệ", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!confirm.equals(password)) {
            Toast.makeText(SignupActivity.this, "Xác nhận mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}
