package com.thh.easy.constant;

/**
 * Created by taCi on 2015/10/26.
 *
 * 字符串常量
 */
public class StringConstant {

    public final static String NULL_VALUE = "null";
    public final static String SUCCESS = "1";
    public final static String FAIL = "0";


    public final static String SERVER_IP = "http://172.27.35.1:8080";
   // public final static String SERVER_IP = "http://192.168.1.112:8080";

    // 发帖
    public final static String SERVER_POSTS_URL = SERVER_IP +
            "/thhh/posts/android_posts_addPosts.action";

    // 用户登陆Action
    public final static String SERVER_LOGIN_URL = SERVER_IP + "/thhh/user/android_loginCheck.action";

    public final static String USER_ID = "user.id";

    public final static String USER_NAME = "user.name";

    public final static String USER_PWD = "user.pwd";

    // 用户注册 Action
    public final static String SERVER_REG_URL = SERVER_IP + "/thhh/user/android_register.action";

    public final static String USER_NICKNAME = "user.nickname";
    public final static String USER_TNAME = "user.tname";

    // 查看用户信息
    public final static String SERVER_PROFILE_URL = SERVER_IP + "/thhh/user/android_userInfor.action";


    // 最新帖子
    public final static String SERVER_NEWPOST_URL = SERVER_IP + "/thhh/posts/android_posts_newPosts.action";

    public final static String CURRENT_PAGE_KEY = "pageIndex";
    public final static String PER_PAGE_KEY = "rowCount";
    public final static int PER_PAGE_COUNT = 6;


    // 最热帖子
    public final static String SERVER_HOTPOST_URL = SERVER_IP + "/thhh/posts/android_posts_hotPosts.action";

    // 查看评论
    public final static String SERVER_COMMENT_URL = SERVER_IP + "/thhh/posts/android_comments_seeComment.action";

    public final static String POSTID = "posts.id";

    // 发送评论
    public final static String SERVER_ADDCOMMENT_URL = SERVER_IP + "/thhh/posts/android_comments_addComments.action";

    public final static String COMMENT_UID = "users.id";
    public final static String COMMENT_POST_ID = "comments.posts.id";
    public final static String COMMENT_CONTENTS = "comments.contents";


    // 点赞
    public final static String SERVER_LIKE_URL = SERVER_IP + "/thhh/posts/android_posts_addLikes.action";

    public final static String LIKE_UID = "likes.user.id";
    public final static String LIKE_POST_ID = "likes.posts.id";


    // 收藏
    public final static String SERVER_STORE_URL = SERVER_IP + "/thhh/posts/android_comments_addCollects.action";


    // 查看商店
    public final static String SERVER_SHOP_URL = SERVER_IP + "/thhh/goods/android_goods_seekShop.action";

    public final static String SHOP_ID = "shop.id";


    // 查看商品
    public final static String SERVER_GOODS_URL = SERVER_IP + "/thhh/goods/android_goods_seekGoods.action?";

    public final static String GOODS_ID = "goods.shop.id";

    // 由选商品界面到订单界面Intent的KEY值
    public final static String KEY_ORDER_ITEM_MAP ="ITEM_MAP";

    public final static String KEY_ORDER_SUM ="ORDER_SUM_PRICE";



    public final static String ORDER_USER_ID = "orders.users.id";

    // 下订单
    public final static String SERVER_CONFIRM_ORDER = SERVER_IP + "/thhh/goods/android_order_buy.action";

    public final static String ORDER_SHOP_ID = "orders.shop.id";
    public final static String ORDER_TAKE_TIME = "orders.take";
    public final static String KEY_ORDER_ITEM = "orderItemJson";

    // 查看订单
    public final static String SERVER_ORDER_URL = SERVER_IP + "/thhh/goods/android_order_seeOrders.action";




    // 查看正在组织的活动
    public final static String SERVER_ACT_URL = SERVER_IP + "/thhh/goods/android_goods_seekShop.action";

    public final static String ACT_ID = "act.id";

}
