package com.thh.easy.util;

import com.thh.easy.entity.OrderItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Intent 传递 Map 工具类
 * Created by cloud on 2015/11/10.
 */
public class SerialzableList implements Serializable {

    private List<OrderItem> itemList = new ArrayList<>();

    public SerialzableList(List<OrderItem> itemList){
        this.itemList = itemList;
    }

    public List<OrderItem>  getList(){
        return itemList;
    }
}

