package com.thh.easy.entity;

/**
 * Created by taCi on 2015/11/1.
 */
public class Comment {

    private int uid;

    private String name;

    private String content;

    private String avatar;

    public Comment() {
    }

    public Comment(int uid, String content, String avatar) {
        this.uid = uid;
        this.name = name;
        this.content = content;
        this.avatar = avatar;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
