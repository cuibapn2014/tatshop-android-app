package com.example.tatshop.controller;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;

import com.example.tatshop.R;
import com.example.tatshop.api.ApiService;
import com.example.tatshop.model.User;
import com.example.tatshop.storage.DataLocalManger;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserController {
    private static User USER;
    private static boolean success = false;

    public UserController() {
    }

    public static User getUSER() {
        return USER;
    }

    public static void setUSER(User USER) {
        UserController.USER = USER;
    }

    public static boolean isSuccess() {
        return success;
    }

    public static void setSuccess(boolean success) {
        UserController.success = success;
    }

    public static void login(String email, String password, Activity context) {
        final String token = "f0ecce8d027f556c369a1d24d237b18b272a010df5896f8abe05e98accabd261";
        String data_key = "USER_REMEMBER";
        Button btnLogin = (Button) context.findViewById(R.id.btn_login);
        CheckBox isAutoLogin = (CheckBox) context.findViewById(R.id.auto_login);

        ApiService.apiService.getLogin(email, password, token).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                final User user = response.body();
                USER = user;
                if (isAutoLogin.isChecked())
                    DataLocalManger.setUser(user, data_key);
                if (USER != null && USER.getId() != 0) {
                    success = true;
                    context.finish();
                } else {
                    Toast.makeText(context, "Tài khoản hoặc mật khẩu không chính xác!", Toast.LENGTH_SHORT).show();
                }
                btnLogin.setEnabled(true);
                btnLogin.setBackground(context.getResources().getDrawable(R.color.teal));
                btnLogin.setText(R.string.login_title);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                btnLogin.setEnabled(true);
                Log.e("TAG", success + "");
            }
        });
    }

    public static boolean isLogin() {
        String key = "USER_REMEMBER";
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null) {
            getFbInfo();
            return true;
        }
        User user = DataLocalManger.getUser(key);
        if (user != null) {
            USER = user;
            success = true;
            return true;
        }
        return false;
    }

    public static void logout() {
        String key = "USER_REMEMBER";
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null)
            LoginManager.getInstance().logOut();
        USER = null;
        success = false;
        DataLocalManger.setUser(USER, key);
    }

    public static void loginFacebook(User user, Activity context) {
        final String token = "f0ecce8d027f556c369a1d24d237b18b272a010df5896f8abe05e98accabd261";
        ApiService.apiService.getMatchesEmail(user.getEmail(), token).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                USER = response.body();
                success = true;
                if (USER != null)
                    context.finish();
                Log.e("TAG", new Gson().toJson(response.body()));
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("TAG", "Error");
                USER = user;
                signupWithFB(user);
                success = true;
                if (USER != null)
                    context.finish();
            }
        });

    }

    private static void signupWithFB(User user) {
        final String token = "f0ecce8d027f556c369a1d24d237b18b272a010df5896f8abe05e98accabd261";
        ApiService.apiService.signupWithFacebook(user, token).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.e("TAG", new Gson().toJson(response.body()));
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("TAG", "Error");
            }
        });
    }

    public static void signup(String email, String password, Activity context) {
        final String token = "f0ecce8d027f556c369a1d24d237b18b272a010df5896f8abe05e98accabd261";
        AppCompatButton btnSignup = (AppCompatButton) context.findViewById(R.id.btn_signup);
        ApiService.apiService.signupAccount(email, password, token).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Toast.makeText(context, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                context.finish();
                Log.e("TAG", new Gson().toJson(response.body()));
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(context, "Lỗi kết nối tới máy chủ", Toast.LENGTH_SHORT).show();
                btnSignup.setEnabled(true);
                btnSignup.setBackground(context.getResources().getDrawable(R.color.teal));
                btnSignup.setText("SIGN UP");
                Log.e("TAG", "Error");
            }

        });
    }

    private static void getFbInfo() {
        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        try {
                            User user = new User();
                            user.setEmail(object.getString("email"));
                            user.setName(object.getString("name"));
                            matchesAccount(user);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id, name, email");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private static void matchesAccount(User user) {
        final String token = "f0ecce8d027f556c369a1d24d237b18b272a010df5896f8abe05e98accabd261";
        ApiService.apiService.getMatchesEmail(user.getEmail(), token).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                USER = response.body();
                success = true;
                Log.e("TAG", new Gson().toJson(response.body()));
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("TAG", "Error");
            }
        });
    }

    public static void updateUser(User user, Activity context) {
        final String token = "f0ecce8d027f556c369a1d24d237b18b272a010df5896f8abe05e98accabd261";
        ApiService.apiService.updateUser(user, user.getId(), token).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Toast.makeText(context, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("TAG", t.toString());
                Toast.makeText(context, "Có lỗi trong lúc cập nhật!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
