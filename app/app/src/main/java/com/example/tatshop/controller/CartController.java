package com.example.tatshop.controller;

import com.example.tatshop.DataLocalManger;
import com.example.tatshop.model.Product;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class CartController {
    private static List<Product> LIST_CART_ITEM = new ArrayList<>();
    public static Locale lc = Locale.getDefault();
    public static NumberFormat nf = NumberFormat.getCurrencyInstance(lc);

    public CartController() {
    }

    public static List<Product> getListCartItem() {
        return LIST_CART_ITEM;
    }

    public static void setListCartItem(List<Product> listCartItem) {
        LIST_CART_ITEM = listCartItem;
    }

    public int addCartItem(Product product){
        if(product != null) {
            LIST_CART_ITEM.add(product);
            return 1;
        }
        return 0;
    }

    public int editCartItem(List<Product> list){
        if(!list.isEmpty()){
            LIST_CART_ITEM = list;
            return list.size();
        }
        return 0;
    }

    public int removeCartItem(List<Product> list){
        if(!list.isEmpty()){
            for(Product product : list){
                LIST_CART_ITEM.remove(product);
            }
            return list.size();
        }
        return 0;
    }

    public static void clearCart(){
        LIST_CART_ITEM.clear();
    }

    public void saveDataLocal(){
        DataLocalManger.setListCartItem(LIST_CART_ITEM);
    }

    public static int getTotal(){
        int TOTAL = 0;
        for(Iterator<Product> i = getListCartItem().iterator();i.hasNext();){
            Product product = (Product) i.next();
            TOTAL += product.getQty() * product.getPrice() * (1 - product.getDiscount()/100);
        }
        return TOTAL;
    }
}
