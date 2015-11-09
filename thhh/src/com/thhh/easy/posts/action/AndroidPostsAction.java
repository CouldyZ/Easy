package com.thhh.easy.posts.action;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.thhh.easy.entity.Collects;
import com.thhh.easy.entity.Comments;
import com.thhh.easy.entity.Image;
import com.thhh.easy.entity.Likes;
import com.thhh.easy.entity.Posts;
import com.thhh.easy.entity.Users;
import com.thhh.easy.posts.service.IPostsService;
import com.thhh.easy.util.Constant;
import com.thhh.easy.util.MyUtil;

public class AndroidPostsAction {

	private IPostsService postsService;
	private int pageIndex; // 当前页数
	private int rowCount; // 每页显示条数
	
	private Likes likes ;	//点赞对象
	private Collects collects ;	//收藏对象
	private Posts posts ;	//贴子对象
	private Users user ;	//用户对象
	
	//上传资料
	private File postsImg;			//头像
	private String postsImgFileName;	//头像文件名
	
	
	// 查找最新的贴子(按照发帖时间降序)
	public void newPosts() {
		if (pageIndex == 0 || rowCount == 0) {
			setPageIndex(Constant.DEFAULT_PAGE);
			setRowCount(Constant.DEFAULT_PAGE_SIZE);
		}
		List<Posts> listPosts = postsService.findNewPosts(pageIndex,rowCount);
		List<Map<String, Object>> listMap = new ArrayList<Map<String,Object>>() ;
		for (Posts posts : listPosts) {
			Map<String, Object> map = new HashMap<String, Object>() ;
			map.put("likes", postsService.findPostsLikesCount(posts.getId())) ;
			map.put("isLike", Constant.STRING_0) ;
			if(user != null && user.getId() != null){
				if(postsService.userIsLikes(user.getId(), posts.getId())){
					map.put("isLike", Constant.STRING_1) ;
				}
			}
			map.put("posts", posts) ;
			listMap.add(map) ;
		}
		MyUtil.sendString(listMap) ;
		user = null ;
	}
	// 查找最热的贴子(距离当前时间一定天数<默认7天>内按照点赞数排序)
	public void hotPosts() {
		if (getPageIndex() == 0 || getRowCount() == 0) {
			setPageIndex(Constant.DEFAULT_PAGE);
			setRowCount(Constant.DEFAULT_PAGE_SIZE);
		}
		List<Map> listMap = new ArrayList<Map>();
		List<int[]> listPostsInfo = postsService.findHotPosts(getPageIndex(),
				getRowCount());
		for (int[] info : listPostsInfo) {
			Posts posts = postsService.findPostsById(info[0]) ;
			Map<String, Object> map = new HashMap<String, Object>() ;
			map.put("posts", posts) ;
			map.put("likes", info[1]) ;
			map.put("isLike", Constant.STRING_0) ;
			if(user != null && user.getId() != null){
				if(postsService.userIsLikes(user.getId(), posts.getId())){
					map.put("isLike", Constant.STRING_1) ;
				}
			}
			listMap.add(map) ;
		}
		MyUtil.sendString(listMap) ;
		user = null ;
	}
	
	/**
	 * 点赞方法
	 */
	public void addLikes(){
		if(likes == null || likes.getUsers() == null || likes.getPosts() == null ||
				likes.getUsers().getId() == null || likes.getPosts().getId() == null){
			MyUtil.sendString(Constant.STRING_0) ;
			likes = null ;
			return ;
		}
		//判断用户是否已经点赞过此贴
		if(postsService.userIsLikes(likes.getUsers().getId() ,likes.getPosts().getId())){
			//用户已点赞过此贴，则删除点赞
			postsService.deleteLikes(likes.getUsers().getId() ,likes.getPosts().getId());
			MyUtil.sendString(Constant.STRING_1) ;
			likes = null ;
			return ;
		}
		likes.setUsers(postsService.findUserById(likes.getUsers().getId())) ;
		likes.setPosts(postsService.findPostsById(likes.getPosts().getId())) ;
		likes.setId(null) ;
		postsService.saveLikes(likes) ;
		MyUtil.sendString(Constant.STRING_1) ;
		likes = null ;
	}
	
	/**
	 * 添加收藏
	 */
	public void addCollects(){
		if(collects == null || collects.getUsers() == null || collects.getPosts() == null ||
				collects.getUsers().getId() == null || collects.getPosts().getId() == null){
			MyUtil.sendString(Constant.STRING_0) ;
			collects = null ;
			return ;
		}
		//判断用户是否已经收藏过此贴
		if(postsService.userIsCollects(collects.getUsers().getId() ,collects.getPosts().getId())){
			//用户已收藏过此贴，则删除点赞
			postsService.deleteCollects(collects.getUsers().getId() ,collects.getPosts().getId());
			MyUtil.sendString(Constant.STRING_1) ;
			likes = null ;
			return ;
		}
		collects.setUsers(postsService.findUserById(collects.getUsers().getId())) ;
		collects.setPosts(postsService.findPostsById(collects.getPosts().getId())) ;
		collects.setDates(new Date()) ;
		collects.setId(null) ;
		postsService.saveCollects(collects) ;
		MyUtil.sendString(Constant.STRING_1) ;
		collects = null ;
	}
	
	//查看我的收藏
	public void seeCollects(){
		if(collects == null || collects.getUsers() == null  ||
				collects.getUsers().getId() == null){
			MyUtil.sendString(Constant.STRING_0) ;
			collects = null ;
			return ;
		}
		if (pageIndex == 0 || rowCount == 0) {
			setPageIndex(Constant.DEFAULT_PAGE);
			setRowCount(Constant.DEFAULT_PAGE_SIZE);
		}
		List<Collects> listCollects = postsService.findCollects(pageIndex,rowCount,collects.getUsers().getId()) ;
		MyUtil.sendString(listCollects) ;
		collects = null ;
	}
	
	/**
	 * 查看自己发的帖
	 */
	public void seePosts(){
		if(posts == null || posts.getUsers() == null ||
				posts.getUsers().getId() == null ){
			MyUtil.sendString(Constant.STRING_0) ;
			posts = null ;
			return ;
		}
		if (pageIndex == 0 || rowCount == 0) {
			setPageIndex(Constant.DEFAULT_PAGE);
			setRowCount(Constant.DEFAULT_PAGE_SIZE);
		}
		List<Posts> listPosts = postsService.findUserPosts(pageIndex,rowCount,posts.getUsers().getId()) ;
		MyUtil.sendString(listPosts) ;
		posts = null ;
	}
	/**
	 * 保存用户发帖
	 */
	public void addPosts(){
		if(posts == null || posts.getUsers() == null ||
				posts.getUsers().getId() == null ){
			MyUtil.sendString(Constant.STRING_0) ;
			posts = null ;
			return ;
		}
		posts.setUsers(postsService.findUserById(posts.getUsers().getId())) ;
		posts.setDates(new Date()) ;
		
		Image image = new Image() ;
		
		if(postsImg != null && postsImgFileName != null){
			image.setUrls(Constant.IMAGE_POSTS+"/"+postsImgFileName) ;
			image.setUsers(null) ;
			//保存图片到指定路径
			MyUtil.saveImage(postsImg, MyUtil.imagePath(Constant.IMAGE_POSTS), postsImgFileName) ;
			postsImg = null ;
			postsImgFileName = null ;
		}
		posts.setImage(image) ;
		//级联保存图片
		postsService.savePosts(posts) ;
		MyUtil.sendString(Constant.STRING_1) ;
	}
	
	
	public IPostsService getPostsService() {
		return postsService;
	}

	public void setPostsService(IPostsService postsService) {
		this.postsService = postsService;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}
	public Likes getLikes() {
		return likes;
	}
	public void setLikes(Likes likes) {
		this.likes = likes;
	}
	public Collects getCollects() {
		return collects;
	}
	public void setCollects(Collects collects) {
		this.collects = collects;
	}
	public Posts getPosts() {
		return posts;
	}
	public void setPosts(Posts posts) {
		this.posts = posts;
	}
	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
	}
	public File getPostsImg() {
		return postsImg;
	}
	public void setPostsImg(File postsImg) {
		this.postsImg = postsImg;
	}
	public String getPostsImgFileName() {
		return postsImgFileName;
	}
	public void setPostsImgFileName(String postsImgFileName) {
		this.postsImgFileName = postsImgFileName;
	}

}
