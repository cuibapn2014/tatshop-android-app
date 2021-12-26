package com.example.tatshop.storage;

import android.content.Context;
import android.util.Log;

import com.example.tatshop.model.Product;
import com.example.tatshop.model.User;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DataLocalManger {
    private static DataLocalManger instance;
    private SaveData saveData;
    private static String DATA_KEY = "CART_KEY";

    public static void init(Context context) {
        instance = new DataLocalManger();
        instance.saveData = new SaveData(context);
    }

    public static DataLocalManger getInstance() {
        if (instance == null) {
            instance = new DataLocalManger();
        }
        return instance;
    }

    public static void setListCartItem(List<Product> list) {
        Gson gson = new Gson();
        JsonArray jsonElements = gson.toJsonTree(list).getAsJsonArray();
        String strJsonArray = jsonElements.toString();
        DataLocalManger.getInstance().saveData.putString(DATA_KEY, strJsonArray);
    }

    public static List<Product> getListCartItem() {
        String jsonArray = DataLocalManger.getInstance().saveData.getString(DATA_KEY);
        List<Product> productList = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(jsonArray);
            JSONObject jsonObject;
            Product product;
            Gson gson = new Gson();
            for (int i = 0; i < array.length(); i++) {
                jsonObject = array.getJSONObject(i);
                product = gson.fromJson(jsonObject.toString(), Product.class);
                productList.add(product);
            }
        } catch (JSONException e) {
            Log.e("Error", e.getMessage());
        }
        return productList;
    }

    public static String getDataKey() {
        return DATA_KEY;
    }

    public static void setDataKey(String cartKey) {
        DATA_KEY = cartKey;
    }

    public static void setUser(User user, String key) {
        Gson gson = new Gson();
        String strJsonUser = gson.toJson(user);
        DataLocalManger.getInstance().saveData.putStrUser(key, strJsonUser);
    }

    public static User getUser(String key) {
        String strJsonUser = DataLocalManger.getInstance().saveData.getStrUser(key);
        Gson gson = new Gson();
        User user = gson.fromJson(strJsonUser, User.class);
        return user;
    }

    public static void setListLatestUpdate(List<Product> list) {
        Gson gson = new Gson();
        JsonArray jsonElements = gson.toJsonTree(list).getAsJsonArray();
        String strJsonArray = jsonElements.toString();
        DataLocalManger.getInstance().saveData.putString("LATEST_UPDATE_KEY", strJsonArray);
    }

    public static List<Product> getListLatestUpdate() {
        String jsonArray = DataLocalManger.getInstance().saveData.getString("LATEST_UPDATE_KEY");
        List<Product> productList = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(jsonArray);
            JSONObject jsonObject;
            Product product;
            Gson gson = new Gson();
            for (int i = 0; i < array.length(); i++) {
                jsonObject = array.getJSONObject(i);
                product = gson.fromJson(jsonObject.toString(), Product.class);
                productList.add(product);
            }
        } catch (JSONException e) {
            Log.e("Error", e.getMessage());
        }
        return productList;
    }

    public static void setListProduct(List<Product> list) {
        Gson gson = new Gson();
        JsonArray jsonElements = gson.toJsonTree(list).getAsJsonArray();
        String strJsonArray = jsonElements.toString();
        DataLocalManger.getInstance().saveData.putString("LIST_PRODUCT_KEY", strJsonArray);
    }

    public static List<Product> getListProduct() {
        String jsonArray = DataLocalManger.getInstance().saveData.getString("LIST_PRODUCT_KEY");
        List<Product> productList = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(jsonArray);
            JSONObject jsonObject;
            Product product;
            Gson gson = new Gson();
            for (int i = 0; i < array.length(); i++) {
                jsonObject = array.getJSONObject(i);
                product = gson.fromJson(jsonObject.toString(), Product.class);
                productList.add(product);
            }
        } catch (JSONException e) {
            Log.e("Error", e.getMessage());
        }
        return productList;
    }
}
