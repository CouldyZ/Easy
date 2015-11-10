package com.thh.easy.entity;

/**
 * 订单
 * Created by cloud on 2015/11/10.
 */
public class Order {

    int id;
    int state;             // 订单状态
    String shopName;       // 商店名字
    String ShopImgUrl;     // 商店图片
    int count;             // 订单总份数
    float sum;             // 订单总额
    String completedate;   // 下单时间

    public Order(String completedate, int count, int id, String shopImgUrl, String shopName, int state, float sum) {
        this.completedate = completedate;
        this.count = count;
        this.id = id;
        ShopImgUrl = shopImgUrl;
        this.shopName = shopName;
        this.state = state;
        this.sum = sum;
    }

    public String getCompletedate() {
        return completedate;
    }

    public void setCompletedate(String completedate) {
        this.completedate = completedate;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShopImgUrl() {
        return ShopImgUrl;
    }

    public void setShopImgUrl(String shopImgUrl) {
        ShopImgUrl = shopImgUrl;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public float getSum() {
        return sum;
    }

    public void setSum(float sum) {
        this.sum = sum;
    }
}
