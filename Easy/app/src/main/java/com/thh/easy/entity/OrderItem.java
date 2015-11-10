package com.thh.easy.entity;

import java.io.Serializable;

/**
 * 订单项
 * Created by cloud on 2015/11/10.
 */
public class OrderItem implements Serializable {
    String goodsName; // 商品名
    int goodsId;   // 商品id
    float goodsPrice; // 商品价格
    int goodsNum;   // 商品数量

    public OrderItem(int goodsId, String goodsName, int goodsNum, float goodsPrice) {
        this.goodsId = goodsId;
        this.goodsName = goodsName;
        this.goodsNum = goodsNum;
        this.goodsPrice = goodsPrice;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public int getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(int goodsNum) {
        this.goodsNum = goodsNum;
    }

    public float getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(float goodsPrice) {
        this.goodsPrice = goodsPrice;
    }
}
