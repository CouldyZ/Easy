package com.thh.easy.entity;

/**
 * Created by cloud on 2015/11/9.
 */
public class OrderItem {
    private Goods goods;
    private int count;

    public OrderItem(int count, Goods goods) {
        this.count = count;
        this.goods = goods;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }
}
