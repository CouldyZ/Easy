package com.thhh.easy.util;

import java.util.Properties;

/**
 * 字符串常量
 * @author Administrator
 *
 */
public class Constant {

	static{
		initData() ;
	}
	/**
	 * 请求成功
	 */
	public static String STRING_1  ;
	/**
	 * 请求失败
	 */
	public static String STRING_0  ;
	
	/**
	 * 查询热帖是距离当前的天数
	 */
	public static Integer DIATANCE_NOW_DATY  ;
	/**
	 * 分页查询时默认当前页
	 */
	public  static Integer DEFAULT_PAGE  ;	
	/**
	 * 分页查询时默认每页个数 
	 */
	public  static Integer DEFAULT_PAGE_SIZE  ;	
	/**
	 * 用户保存头像路径
	 */
	public static String IMAGE_AVATAR ;
	/**
	 * 用户照片墙保存路径
	 */
	public static String IMAGE_USER_PHOTO ;
	/**
	 * 贴子图片保存路径
	 */
	public static String IMAGE_POSTS ;
	
	
	/**
	 * 初始化数据
	 */
	public static boolean initData() {
		Properties properties = new Properties();
		try {
			properties.load(Constant.class
					.getResourceAsStream("/thhh.properties"));
		} catch (Exception e) {
			System.out.println("no found properties");
			return false ;
		}
		STRING_1 = properties.getProperty("response.success") ;
		STRING_0 = properties.getProperty("response.fail") ;
		DEFAULT_PAGE = Integer.parseInt(properties.getProperty("default.page"));
		DEFAULT_PAGE_SIZE = Integer.parseInt(properties.getProperty("default.page.size"));
		DIATANCE_NOW_DATY = Integer.parseInt(properties.getProperty("hotposts.distance.now.day"));
		IMAGE_AVATAR = properties.getProperty("image.avatar") ;
		IMAGE_USER_PHOTO = properties.getProperty("image.user.photo") ;
		IMAGE_POSTS = properties.getProperty("image.posts") ;
		return true;
	}
	
}
