package com.thh.easy.entity;

/**
 * Created by cloud on 2015/11/9.
 */
public class Activities {

    String id;
    User user;        // 发起人
    String state;     // 活动状态
    String theme;     // 主题
    int account;      // 发起活动人数
    String lastDay;   // 持续天数
    String startDay;  // 开始日期

    String pay;       // 经费
    String content;   // 活动内容

    public Activities(User user, int account, String content, String id, String lastDay,
                      String pay, String startDay, String state, String theme) {
        this.user = user;
        this.account = account;
        this.content = content;
        this.id = id;
        this.lastDay = lastDay;
        this.pay = pay;
        this.startDay = startDay;
        this.state = state;
        this.theme = theme;
    }

    public Activities() {

    }

    public int getAccount() {
        return account;
    }

    public void setAccount(int account) {
        this.account = account;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLastDay() {
        return lastDay;
    }

    public void setLastDay(String lastDay) {
        this.lastDay = lastDay;
    }

    public String getPay() {
        return pay;
    }

    public void setPay(String pay) {
        this.pay = pay;
    }

    public String getStartDay() {
        return startDay;
    }

    public void setStartDay(String startDay) {
        this.startDay = startDay;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
