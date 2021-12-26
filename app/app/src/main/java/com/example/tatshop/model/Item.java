package com.example.tatshop.model;

public class Item {
    private int id_product;
    private String name;
    private String attr;
    private String image;
    private int price;
    private int qty;
    private int status;

    public Item() {
    }

    public Item(int id_product, String attr, String image,int price, int qty, int status, String name) {
        this.id_product = id_product;
        this.attr = attr;
        this.image = image;
        this.name = name;
        this.price = price;
        this.qty = qty;
        this.status = status;
    }

    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
