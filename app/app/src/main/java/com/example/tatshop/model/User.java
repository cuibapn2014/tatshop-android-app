package com.example.tatshop.model;

import com.google.gson.annotations.SerializedName;

public class User {
    private int id;
    private String name;
    private String image;
    private int level;
    @SerializedName("sex")
    private String gender;
    private String birthday;
    private String address;
    private String phone;

    public User(int id, String name, String image, int level, String gender, String birthday, String address, String phone) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.level = level;
        this.gender = gender;
        this.birthday = birthday;
        this.address = address;
        this.phone = phone;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
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
}
