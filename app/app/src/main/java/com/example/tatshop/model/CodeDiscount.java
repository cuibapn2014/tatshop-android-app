package com.example.tatshop.model;

public class CodeDiscount {
    private int id;
    private String code;
    private int time;
    private int min;
    private int discount;
    private String expire;

    public CodeDiscount() {
    }

    public CodeDiscount(int id, String code, int time, int min, int discount, String expire) {
        this.id = id;
        this.code = code;
        this.time = time;
        this.min = min;
        this.discount = discount;
        this.expire = expire;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getExpire() {
        return expire;
    }

    public void setExpire(String expire) {
        this.expire = expire;
    }
}
