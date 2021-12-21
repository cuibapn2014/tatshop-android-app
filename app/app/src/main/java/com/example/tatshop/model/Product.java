package com.example.tatshop.model;

import java.util.List;

public class Product {
    private int id;
    private String title;
    private String content;
    private int qty;
    private String thumbnail;
    private int discount;
    private int sold;
    private int price;
    private Subcategory subcategory;
    private List<Image> image;
    private List<AttributeProduct> attr;
    private List<Comment> comment;

    public Product() {
    }

    public Product(int id, String title, String content, int qty, String thumbnail, int discount,
                   int sold, int price, Subcategory subcategory, List<Image> image, List<AttributeProduct> attr, List<Comment> comment) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.qty = qty;
        this.thumbnail = thumbnail;
        this.discount = discount;
        this.sold = sold;
        this.price = price;
        this.subcategory = subcategory;
        this.image = image;
        this.attr = attr;
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }

    public Subcategory getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(Subcategory subcategory) {
        this.subcategory = subcategory;
    }

    public List<Image> getImage() {
        return image;
    }

    public void setImage(List<Image> image) {
        this.image = image;
    }

    public List<AttributeProduct> getAttr() {
        return attr;
    }

    public void setAttr(List<AttributeProduct> attr) {
        this.attr = attr;
    }

    public List<Comment> getComment() {
        return comment;
    }

    public void setComment(List<Comment> comment) {
        this.comment = comment;
    }
}
