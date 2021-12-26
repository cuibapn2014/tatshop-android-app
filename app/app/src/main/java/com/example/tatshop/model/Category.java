package com.example.tatshop.model;

import com.google.gson.annotations.SerializedName;

public class Category {

    @SerializedName("category")
    private String title;

    private int type;

    public Category(String title) {
        this.title = title;
    }

    public Category(String title, int type) {
        this.title = title;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
