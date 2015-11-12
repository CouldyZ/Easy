package com.thh.easy.entity;

/**
 * Created by cloud on 2015/11/9.
 */
public class Activities {

    String id;
    int uid;                // 发起人id
    String userName;        // 发起名字
    String userImage;       // 发起人头像
    int userRP;             // 发起人rp值

    String theme;           // 主题
    int account;            // 发起活动人数

    String startDate;       // 开始日期
    String endDate;         // 结束日期

    String pay;             // 经费
    String content;         // 活动内容

    int particiCount;       // 已参加的人数
    int reportCount;        // 举报人数

    public Activities() {
    }


    public Activities(String id, String theme,
                          String userName, String userImage,int userRP, String endDate, String startDate) {
        this.id = id;
        this.endDate = endDate;
        this.startDate = startDate;
        this.userImage = userImage;
        this.userName = userName;
        this.userRP = userRP;
        this.theme = theme;
    }

    public Activities(int account, String content, String endDate, String id, int particiCount, String pay, int reportCount, String startDate,
                      String theme, String userImage, String userName, int userRP) {
        this.account = account;
        this.content = content;
        this.endDate = endDate;
        this.id = id;
        this.particiCount = particiCount;
        this.pay = pay;
        this.reportCount = reportCount;
        this.startDate = startDate;
        this.theme = theme;
        this.userImage = userImage;
        this.userName = userName;
        this.userRP = userRP;
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

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getParticiCount() {
        return particiCount;
    }

    public void setParticiCount(int particiCount) {
        this.particiCount = particiCount;
    }

    public String getPay() {
        return pay;
    }

    public void setPay(String pay) {
        this.pay = pay;
    }

    public int getReportCount() {
        return reportCount;
    }

    public void setReportCount(int reportCount) {
        this.reportCount = reportCount;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserRP() {
        return userRP;
    }

    public void setUserRP(int userRP) {
        this.userRP = userRP;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
