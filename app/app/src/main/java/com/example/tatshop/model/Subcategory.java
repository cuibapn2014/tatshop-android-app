package com.example.tatshop.model;

import com.google.gson.annotations.SerializedName;

public class Subcategory {
    @SerializedName("subcategory")
    private String title;
    private Category category;

    public Subcategory(String title, Category category) {
        this.title = title;
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
