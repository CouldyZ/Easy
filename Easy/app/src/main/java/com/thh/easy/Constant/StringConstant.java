package com.thh.easy.constant;

/**
 * Created by taCi on 2015/10/26.
 *
 * 字符串常量
 */
public class StringConstant {

    //public final static String SERVER_IP = "http://172.27.35.1:8080";
    public final static String SERVER_IP = "http://192.168.1.112:8080";  // 服务器地址

    public final static String NULL_VALUE = "null";                      // 服务器返回为null
    public final static String SUCCESS = "1";                            // 服务器返回1
    public final static String FAIL = "0";                               // 服务器返回0

    public final static String CURRENT_PAGE_KEY = "pageIndex";           // 当前页
    public final static String PER_PAGE_KEY = "rowCount";                // 每页多少条数据
    public final static int PER_PAGE_COUNT = 6;                          // 分页，每页默认6条数据



    // 用户登陆Action
    public final static String SERVER_LOGIN_URL = SERVER_IP + "/thhh/user/android_loginCheck.action";

    // 用户注册 Action
    public final static String SERVER_REG_URL = SERVER_IP + "/thhh/user/android_register.action";

    // 查看用户信息
    public final static String SERVER_PROFILE_URL = SERVER_IP + "/thhh/user/android_userInfor.action";

    public final static String USER_ID = "user.id";
    public final static String USER_NAME = "user.name";
    public final static String USER_PWD = "user.pwd";
    public final static String USER_NICKNAME = "user.nickname";
    public final static String USER_TNAME = "user.tname";



    // 发帖
    public final static String SERVER_POSTS_URL = SERVER_IP + "/thhh/posts/android_posts_addPosts.action";

    // 最新帖子
    public final static String SERVER_NEWPOST_URL = SERVER_IP + "/thhh/posts/android_posts_newPosts.action";

    // 最热帖子
    public final static String SERVER_HOTPOST_URL = SERVER_IP + "/thhh/posts/android_posts_hotPosts.action";

    // 查看评论
    public final static String SERVER_COMMENT_URL = SERVER_IP + "/thhh/posts/android_comments_seeComment.action";

    // 发送评论
    public final static String SERVER_ADDCOMMENT_URL = SERVER_IP + "/thhh/posts/android_comments_addComments.action";

    // 点赞
    public final static String SERVER_LIKE_URL = SERVER_IP + "/thhh/posts/android_posts_addLikes.action";

    // 收藏
    public final static String SERVER_STORE_URL = SERVER_IP + "/thhh/posts/android_comments_addCollects.action";


    public final static String POSTID = "posts.id";
    public final static String COMMENT_UID = "users.id";
    public final static String COMMENT_POST_ID = "comments.posts.id";
    public final static String COMMENT_CONTENTS = "comments.contents";
    public final static String LIKE_UID = "likes.user.id";
    public final static String LIKE_POST_ID = "likes.posts.id";



    // 查看商店
    public final static String SERVER_SHOP_URL = SERVER_IP + "/thhh/goods/android_goods_seekShop.action";

    // 查看商品
    public final static String SERVER_GOODS_URL = SERVER_IP + "/thhh/goods/android_goods_seekGoods.action?";

    // 下订单
    public final static String SERVER_CONFIRM_ORDER = SERVER_IP + "/thhh/goods/android_order_buy.action";

    // 查看订单
    public final static String SERVER_ORDER_URL = SERVER_IP + "/thhh/goods/android_order_seeOrders.action";

    public final static String KEY_ORDER_ITEM_MAP ="ITEM_MAP";     //由选商品界面到订单界面Intent的KEY值
    public final static String KEY_ORDER_SUM ="ORDER_SUM_PRICE";
    public final static String KEY_ORDER_ITEM = "orderItemJson";

    public final static String GOODS_ID = "goods.shop.id";
    public final static String SHOP_ID = "shop.id";
    public final static String ORDER_USER_ID = "orders.users.id";
    public final static String ORDER_SHOP_ID = "orders.shop.id";
    public final static String ORDER_TAKE_TIME = "orders.take";



    // 查看正在组织的活动
    public final static String SERVER_ORGING_ACT_URL = SERVER_IP + "/thhh/act/act_findAct.action?";

    // 参加活动
    public final static String SERVER_JOIN_ACT_URL = SERVER_IP + "/thhh/act/act_addAct.action?";

    // 举报活动
    public final static String SERVER_REPORT_ACT_URL = SERVER_IP + "/thhh/act/act_reportAct.action?";

    // 取消活动
    public final static String SERVER_CANCLE_ACT_URL = SERVER_IP + "/thhh/act/act_cancelAct.action?";

    // 查看我的活动
    public final static String SERVER_MY_All_ACT_URL = SERVER_IP + "/thhh/act/act_findAllAct.action?";

    public final static String ACT_ID = "act.id";

}
