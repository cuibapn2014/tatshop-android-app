package com.example.tatshop;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.tatshop.controller.UserController;
import com.example.tatshop.model.User;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private AppCompatButton btnLogin, btnLoginFb;
    private EditText mUsername, mPassword;
    private CallbackManager callbackManager;
    private TextView tvCreatAccount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        getSupportActionBar().hide();
        mapping();
        checkUser();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mUsername.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                UserController.login(email, password, LoginActivity.this);

                btnLogin.setEnabled(false);
                btnLogin.setBackground(getResources().getDrawable(R.color.teal_700));
                btnLogin.setText("Waiting for..");
            }
        });

        tvCreatAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });

        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(
                                            JSONObject object,
                                            GraphResponse response) {
                                        // Application code
                                        try {
                                            User user = new User();
                                            user.setEmail(object.getString("email"));
                                            user.setName(object.getString("name"));
                                            UserController.loginFacebook(user, LoginActivity.this);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id, name, email");
                        request.setParameters(parameters);
                        request.executeAsync();
                        Log.e("Tag", "Success");
                    }

                    @Override
                    public void onCancel() {
                        // App code
                        Log.e("Tag", "Cancel");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        Log.e("Tag", "Error");
                    }
                });
    }

    private void mapping() {
        btnLogin = (AppCompatButton) findViewById(R.id.btn_login);
        //btnLoginFb = (AppCompatButton) findViewById(R.id.btn_login_fb);
        mUsername = (EditText) findViewById(R.id.username);
        mPassword = (EditText) findViewById(R.id.password);
        tvCreatAccount = (TextView) findViewById(R.id.create_account);
    }

    public void checkUser() {
        if (UserController.isLogin()) {
            btnLogin.setEnabled(true);
            btnLogin.setBackground(getResources().getDrawable(R.color.teal));
            btnLogin.setText(R.string.login_title);
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

}
