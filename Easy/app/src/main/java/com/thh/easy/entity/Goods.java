package com.thh.easy.entity;

/**
 * Created by cloud on 2015/11/6.
 */
public class Goods {

    private int id;
    private String name;    // 商品名
    private float price;    // 商品价格
    private float deposit;  // 商品的定金
    private String url;     // 商品图片


    public float getDeposit() {
        return deposit;
    }

    public void setDeposit(float deposit) {
        this.deposit = deposit;
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Goods( int id, String name, String url, float price, float deposit) {
        this.deposit = deposit;
        this.id = id;
        this.name = name;
        this.price = price;
        this.url = url;
    }

    public Goods() {
    }
}
