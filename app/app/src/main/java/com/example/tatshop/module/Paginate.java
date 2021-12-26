package com.example.tatshop.module;

import com.example.tatshop.model.Product;

import java.util.ArrayList;
import java.util.List;

public class Paginate {
    private static List<Product> listProduct;
    private final int totalItemPage = 20;
    private int lastItem;
    private int totalPage = 1;
    private int currentPage = 1;
    private int start = 0;

    public Paginate(){}

    public Paginate(List<Product> list) {
        this.listProduct = list;
    }

    public int paginate(int totalItem) {
        totalPage = (int) Math.ceil(totalItem / totalItemPage);
        return totalPage;
    }

    public List<Product> getItemCurrentPage(int currentPage){
        List<Product> list = new ArrayList<>();
        start = (currentPage  - 1) * totalItemPage;
        lastItem = start + totalItemPage;
        for(int i = start; i < lastItem; i++){
            if(i >= listProduct.size())
                break;
            list.add(listProduct.get(i));
        }
        return list;
    }

    public static List<Product> getListProduct() {
        return listProduct;
    }

    public static void setListProduct(List<Product> listProduct) {
        Paginate.listProduct = listProduct;
    }
}
