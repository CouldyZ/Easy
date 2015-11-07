package com.thh.easy.entity;

import java.io.Serializable;

/**
 * Created by taCi on 2015/11/1.
 */
public class Shop implements Serializable {

    private int id;

    private String url;

    private String name;

    private String address;

    private String phone;

    private String shortcut;

    public Shop() {
    }

    public Shop(int id, String url, String name, String address, String phone, String shortcut) {
        this.id = id;
        this.url = url;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.shortcut = shortcut;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getShortcut() {
        return shortcut;
    }

    public void setShortcut(String shortcut) {
        this.shortcut = shortcut;
    }
}
