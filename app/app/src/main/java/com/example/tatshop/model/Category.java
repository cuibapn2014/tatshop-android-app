package com.example.tatshop.model;

import com.google.gson.annotations.SerializedName;

public class Category {

    @SerializedName("category")
    private String title;

    public Category(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
