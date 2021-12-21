package com.example.tatshop;

import android.content.Context;
import android.util.Log;

import com.example.tatshop.model.Product;
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
    private static String CART_KEY = "CART_KEY";

    public static void init(Context context){
        instance = new DataLocalManger();
        instance.saveData = new SaveData(context);
    }

    public static DataLocalManger getInstance(){
        if(instance == null){
            instance = new DataLocalManger();
        }
        return instance;
    }

    public static void setListCartItem(List<Product> list){
        Gson gson = new Gson();
        JsonArray jsonElements = gson.toJsonTree(list).getAsJsonArray();
        String strJsonArray = jsonElements.toString();
        Log.e("TAG",strJsonArray);
        DataLocalManger.getInstance().saveData.putString(CART_KEY, strJsonArray);
    }

    public static List<Product> getListCartItem(){
        String jsonArray =  DataLocalManger.getInstance().saveData.getString(CART_KEY);
        List<Product> productList = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(jsonArray);
            JSONObject jsonObject;
            Product product;
            Gson gson = new Gson();
            for(int i = 0; i < array.length();i++){
                jsonObject = array.getJSONObject(i);
                product = gson.fromJson(jsonObject.toString(), Product.class);
                productList.add(product);
            }
        }catch (JSONException e){
            Log.e("Error", e.getMessage());
        }
        return productList;
    }

    public static String getCartKey() {
        return CART_KEY;
    }

    public static void setCartKey(String cartKey) {
        CART_KEY = cartKey;
    }
}
