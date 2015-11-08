package com.thh.easy.entity;

/**
 * Created by cloud on 2015/11/9.
 */
public class OrderItem {
    private int count;
    private int goodsId;

    public OrderItem(int count, int goodsId) {
        this.count = count;
        this.goodsId = goodsId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }
}
