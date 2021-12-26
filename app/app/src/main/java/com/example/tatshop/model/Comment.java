package com.example.tatshop.model;

import com.google.gson.annotations.SerializedName;

public class Comment {
    private int id;
    private String content;
    private int vote;
    @SerializedName("reply")
    private int idProduct;
    private String created_at;
    private User user;

    public Comment() {
    }

    public Comment(int id, String content, int vote, int idProduct, String created_at, User user) {
        this.id = id;
        this.content = content;
        this.vote = vote;
        this.idProduct = idProduct;
        this.created_at = created_at;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
