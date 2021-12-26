package com.example.tatshop.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Bill {
    private int id;
    @SerializedName("customer")
    private String name;
    private int total;
    private int discount;
    private String created_at;
    private String image;
    private String email;
    private String address;
    private String phone;
    private int stt;
    private int pay;
    @SerializedName("payment")
    private List<Item> item;

    public Bill() {
    }

    public Bill(int id, String name, int total, int discount, String created_at, String image,String email, String address, String phone, int stt, int pay, List<Item> item) {
        this.id = id;
        this.name = name;
        this.total = total;
        this.discount = discount;
        this.created_at = created_at;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.stt = stt;
        this.pay = pay;
        this.item = item;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getStt() {
        return stt;
    }

    public void setStt(int stt) {
        this.stt = stt;
    }

    public int getPay() {
        return pay;
    }

    public void setPay(int pay) {
        this.pay = pay;
    }

    public List<Item> getItem() {
        return item;
    }

    public void setItem(List<Item> item) {
        this.item = item;
    }
}
