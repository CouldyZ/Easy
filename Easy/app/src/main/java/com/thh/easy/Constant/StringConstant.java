package com.thh.easy.Constant;

/**
 * Created by taCi on 2015/10/26.
 *
 * 字符串常量
 */
public class StringConstant {

    public final static String NULL_VALUE = "null";

    public final static String SERVER_IP = "http://172.27.35.3:8080/thhh/";

    // 用户登陆Action
    public final static String SERVER_LOGIN_URL = SERVER_IP + "user/android_loginCheck.action";

    public final static String USER_NAME = "user.name";

    public final static String USER_PWD = "user.pwd";

    // 用户注册 Action
    public final static String SERVER_REG_URL = SERVER_IP + "user/android_register.action";

    public final static String USER_NICKNAME = "user.nickname";

    public final static String USER_TNAME = "user.tname";

    public final static String SUCCESS = "1";

    public final static String FAIL = "0";

    // 最新帖子
    public final static String SERVER_NEWPOST_URL = SERVER_IP + "posts/android_newPosts.action";

    public final static String CURRENT_PAGE_KEY = "pageIndex";

    public final static String PER_PAGE_KEY = "rowCount";

    public final static int PER_PAGE_COUNT = 6;
}
