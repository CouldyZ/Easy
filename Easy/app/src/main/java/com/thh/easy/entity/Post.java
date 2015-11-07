package com.thh.easy.entity;

/**
 * Created by taCi on 2015/10/26.
 *
 * 帖子
 */
public class Post {

    private int id;

    private int uid;

    private String username;

    private String content;

    private String date;

    private String imageUrl;

    private String avatar;

    private int like;

    private int likeflag;

    public Post() {
    }

    public Post(int id, int uid, String username, String content, String date, String imageUrl, String avatar) {
        this.id = id;
        this.uid = uid;
        this.username = username;
        this.content = content;
        this.date = date;
        this.imageUrl = imageUrl;
        this.avatar = avatar;
    }

    public Post(int id, int uid, String username, String content, String date, String imageUrl, String avatar, int like) {
        this.id = id;
        this.uid = uid;
        this.username = username;
        this.content = content;
        this.date = date;
        this.imageUrl = imageUrl;
        this.avatar = avatar;
        this.like = like;
    }

    public Post(int id, int uid, String username, String content, String date, String imageUrl, String avatar, int like, int likeflag) {
        this.id = id;
        this.uid = uid;
        this.username = username;
        this.content = content;
        this.date = date;
        this.imageUrl = imageUrl;
        this.avatar = avatar;
        this.like = like;
        this.likeflag = likeflag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getLikeflag() {
        return likeflag;
    }

    public void setLikeflag(int likeflag) {
        this.likeflag = likeflag;
    }
}
