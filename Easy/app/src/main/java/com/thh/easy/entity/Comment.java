package com.thh.easy.entity;

/**
 * Created by taCi on 2015/11/1.
 */
public class Comment {

    private int uid;
    private String name;   //用户名
    private String content; // 内容
    private String avatar; // 头像
    private String date;   // 时间

    public Comment() {
    }


    public Comment(int uid, String content, String avatar) {
        this.uid = uid;
        this.content = content;
        this.avatar = avatar;
    }

    public Comment(String avatar, String content, String date, int uid) {
        this.avatar = avatar;
        this.content = content;
        this.date = date;
        this.uid = uid;
    }

    public Comment(String avatar, String content, String date, String name, int uid) {
        this.avatar = avatar;
        this.content = content;
        this.date = date;
        this.name = name;
        this.uid = uid;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
