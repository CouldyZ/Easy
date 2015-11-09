package com.thhh.easy.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MyUtil {
	
	/**
	 * 向客户端发送json对象
	 * @param obj	需发送的数据
	 * @return true 发送成功 , false 发送失败
	 */
//	public static boolean sendJSONObject(Object obj) {
//		JSONObject jsonObject = JSONObject.fromObject(obj) ;
//		HttpServletResponse response = ServletActionContext.getResponse();
//		response.setContentType("text/plain;charset=UTF-8");
//		try {
//			response.getWriter().write(jsonObject.toString());
//			System.out.println("发送:" + jsonObject.toString());
//			return true;
//		} catch (IOException e) {
//			e.printStackTrace();
//			return false;
//		}
//	}
	/**
	 * 向客户端发送json数组
	 * @param obj	需发送的数组
	 * @return true 发送成功 , false 发送失败
	 */
//	public static boolean sendJSONOArray(Object[] obj) {
//		JSONArray jsonArray = JSONArray.fromObject(obj) ;
//		HttpServletResponse response = ServletActionContext.getResponse();
//		response.setContentType("text/plain;charset=UTF-8");
//		try {
//			response.getWriter().write(jsonArray.toString());
//			System.out.println("发送:" + jsonArray.toString());
//			return true;
//		} catch (IOException e) {
//			e.printStackTrace();
//			return false;
//		}
//	}
	/**
	 * 向客户端发送一个字符串
	 * @param str 需发送的字符串
	 */
	public static void sendString(Object obj){
		String str = "" ;
		Gson g = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create() ;
		str = g.toJson(obj) ;
		if(obj instanceof String){
			str = (String) obj ;
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/plain;charset=UTF-8");
		try {
			response.getWriter().write(str) ;
			System.out.println("发送:" + str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 格式化日期
	 * @param date 需格式化的日期,formatType 格式化样式(为null，则默认格式化成yyyy-MM-dd HH:mm:ss)
	 * @return 格式化后的时间
	 */
	public static String formatDate(Date date , String formatType) {
		SimpleDateFormat simpleDateFormat ;
		if(formatType != null){
			simpleDateFormat = new SimpleDateFormat(formatType);
		}else{
			simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
		return simpleDateFormat.format(date);
	}
	
	/**
	 * 保存图片
	 * @return
	 */
	public static boolean saveImage(File upFile , String uploadPath , String upFileFileName){
		
		if(upFile != null){
		File saveFile = new File(new File(uploadPath), upFileFileName);
		if (!saveFile.getParentFile().exists()){
			saveFile.getParentFile().mkdirs();
		}
		 try {
			FileUtils.copyFile(upFile, saveFile);
		} catch (IOException e) {
			e.printStackTrace();
			return false ;
		}
		}else{
			return false ;
		}
		return true ;
	}
	/**
	 * 获取图片保存的绝对路径
	 * @param relative 图片的相对路径
	 * @return
	 */
	public static String imagePath(String relative){
		String path = null ;
		if(relative != null || ! "".equals(relative)){
			String projectPath = ServletActionContext.getRequest().getSession().getServletContext().getRealPath("/");
			path = new File(projectPath).getParentFile().getAbsolutePath()+relative ;
			path = path.replaceAll("\\\\", "/") ;
//			System.out.println(path);
		}
		return path ;
	}
	
	/**
	 * 将对象中的值映射到map中并返回
	 * @param Posts 需映射的对象  Map<String, Object> 自定义需添加到封装map中的值,可为空
	 * @return Map 返回映射后的map
	 */
//	public static Map<String, Object> postsToMap(Posts posts ,Map<String, Object> m ){
//		Map<String, Object> map = new HashMap<String, Object>() ;
//		if(m != null){
//			map = m ;
//		}
//		map.put("id", posts.getId()) ;
//		map.put("user.id", posts.getUsers().getId()) ;
//		map.put("user.name", posts.getUsers().getName()) ;
//		Image ima = posts.getUsers().getImage() ;
//		if(ima != null){
//			map.put("avatar", ima.getUrls()) ;
//		}else{
//			map.put("avatar", null) ;
//		}
//		map.put("dates", MyUtil.formatDate(posts.getDates(), "yyyy-MM-dd")) ;
//		Image image = posts.getImage() ;
//		if(image != null){
//			map.put("image", image.getUrls()) ;
//		}else{
//			map.put("image", null) ;
//		}
//		map.put("contents", posts.getContents()) ;
//		return map ;
//	}
	
	/**
	 * 将对象中的值映射到map中并返回
	 * @param Comments 需映射的对象  Map<String, Object> 自定义需添加到封装map中的值,可为空
	 * @return Map 返回映射后的map
	 */
//	public static Map<String, Object> commentsToMap(Comments comments ,Map<String, Object> m ){
//		Map<String, Object> map = new HashMap<String, Object>() ;
//		if(m != null){
//			map = m ;
//		}
//		map.put("id", comments.getId()) ;
//		map.put("user.id", comments.getUsers().getId()) ;
//		map.put("user.name", comments.getUsers().getName()) ;
//		Image ima = comments.getUsers().getImage() ;
//		if(ima != null){
//			map.put("avatar", ima.getUrls()) ;
//		}else{
//			map.put("avatar", null) ;
//		}
//		map.put("dates", MyUtil.formatDate(comments.getDates(), "yyyy-MM-dd")) ;
//		map.put("contents", comments.getContents()) ;
//		return map ;
//	}
	
	/**
	 * 将对象中的值映射到map中并返回
	 * @param Comments 需映射的对象  Map<String, Object> 自定义需添加到封装map中的值,可为空
	 * @return Map 返回映射后的map
	 */
//	public static Map<String, Object> collectsToMap(Collects collects ,Map<String, Object> m ){
//		Map<String, Object> map = new HashMap<String, Object>() ;
//		if(m != null){
//			map = m ;
//		}
//		map.put("id", collects.getId()) ;
//		map.put("user.id", collects.getUsers().getId()) ;
//		map.put("user.name", collects.getUsers().getName()) ;
//		Image ima = collects.getUsers().getImage() ;
//		if(ima != null){
//			map.put("avatar", ima.getUrls()) ;
//		}else{
//			map.put("avatar", null) ;
//		}
//		map.put("dates", MyUtil.formatDate(collects.getDates(), "yyyy-MM-dd")) ;
//		return map ;
//	}
}