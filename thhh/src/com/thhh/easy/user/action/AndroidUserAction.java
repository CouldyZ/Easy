package com.thhh.easy.user.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import com.thhh.easy.act.service.IActService;
import com.thhh.easy.entity.Act;
import com.thhh.easy.entity.Collects;
import com.thhh.easy.entity.Image;
import com.thhh.easy.entity.Partici;
import com.thhh.easy.entity.Posts;
import com.thhh.easy.entity.Users;
import com.thhh.easy.user.service.IUserService;
import com.thhh.easy.util.Constant;
import com.thhh.easy.util.MyUtil;

public class AndroidUserAction {

	private Users user; // 用户对象
	private Image image; // 图片对象
	private Act act;
	private Partici partici;
	private IActService actService;
	private IUserService userService;
	private Posts post; // 帖子对象
	private Collects collect; // 收藏帖子对象
	private String uploadFileName;
	private File upload; // 上传对象

	/**
	 * 用户登录检测
	 */
	public void loginCheck() {
//		user = new Users();
//		 user.setName("王四") ;
//		 user.setPwd("123456") ;
		if (user == null || user.getName() == null || "".equals(user.getName())) {
			MyUtil.sendString(null);
			user = null;
			return;
		}
		Users u = userService.findUserByName(user.getName());
		if (u != null && user.getPwd().equals(u.getPwd())) {
			// 用户合法，则向客户端发送用户对象
			MyUtil.sendString(u);
		} else {
			// 用户不合法
			MyUtil.sendString(null);
		}
		user = null;
	}

	/**
	 * 用户注册
	 */
	public void register() {
//		user = new Users();
//		 user.setName("张三") ;
//		 user.setPwd("123456") ;
		 user.setRp(0);
		if (user.getName() != null && user.getPwd() != null) {
			Users u = userService.findUserByName(user.getName());
			if (u != null) {
				// 此用户名已存在
//				Map<String, Object> map=new HashMap<String, Object>();
//				map.put("state", Constant.STRING_0);
				MyUtil.sendString(Constant.STRING_0);
				return;
			} else {
				userService.save(user);
				MyUtil.sendString(Constant.STRING_1);
			}
		} else {
			MyUtil.sendString(null);
		}
		user = null;
	}
	/**
	 * 查询用户是否存在
	 */
	public void queryUser(){
//		user=new Users();
//		user.setName("王五");
//		user.setPwd("123456");
		Users u=userService.findUserByName(user.getName());
		if (u != null) {
			//查询成功，返回用户对象
			MyUtil.sendString(u);

		}else {
			MyUtil.sendString(null);
		}
	}

	/**
	 * 查询用户的详细信息
	 */
	@SuppressWarnings("unchecked")
	public void userInfor() {
//		user = new Users();
//		user.setId(1);
//		user.setName("王五");
//		user.setPwd("123456");
		// 从客户端接收到用户名，在数据库中查询是否存在该用户
		Users u = userService.findById(user.getId());
		if (u != null) {
			int postCount = 0;
			int collectCount = 0;
			List<Posts> listPosts = userService.findUserPosts(user.getId());
			List<Collects> listCollects = userService.findUserCollects(user
					.getId());
			postCount = listPosts.size();
			collectCount = listCollects.size();
			List<Users> list = userService.findUserById(u.getId());
			List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
			for (Users users : list) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("post", postCount);
				map.put("collect", collectCount);
				map.put("users", users);
				listMap.add(map);
			}
			MyUtil.sendString(listMap);

		} else {
			MyUtil.sendString(null);
		}

	}

	/**
	 * 修改用户的个人信息
	 */
	public void update() {
//		user = new Users();
//		user.setId(7);
//		user.setName("hi");
//		user.setPwd("123456");
		Users u = userService.findUserByName(user.getName());
		if (u != null) {
//			user.setName("张霖");
//			user.setPwd("123456");
//			user.setNickname("小小");
//			user.setDepart("英语");
//			user.setRp(80);
//			user.setEmail("xiaoxiao@163.com");
//			user.setRp(69);
			image = new Image();
//			image.setUrls("thhh/resource/image/photo/photo4.png");
			user.setImage(image);
			userService.updateUsers(user);
			System.out.println("修改后的用户信息：" + user.getName() + ","
					+ user.getPwd() + "," + user.getNickname());
			MyUtil.sendString(1);
		} else {
			MyUtil.sendString(0);
		}
	}

	/**
	 * 查看我的图片
	 */
	public void myImage() {
//		user = new Users();
//		user.setId(3);
//		user.setName("张霖");
//		user.setPwd("123456");
		Users u = userService.findUserByName(user.getName());
		if (u != null) {
			List<Image> listImage = userService.findUserImage(user.getId());
			MyUtil.sendString(listImage);
			
		} else {
			MyUtil.sendString(null);
		}
	}

	/**
	 * 图片的上传
	 */
	public void upload() {
//		user = new Users();
//		user.setId(2);
//		user.setName("aaaaaa");
//		user.setPwd("123456");
		Users u = userService.findUserByName(user.getName());
		if (u != null) {
			upload = new File("D:\\tupian\\gg.jpg");
			if (upload != null) {
				// 获得文件上传的磁盘相对路径
				String realPath = ServletActionContext.getServletContext()
						.getRealPath("/image/imagePerson/");
				// 创建一个文件
				File diskFile = new File(realPath + "//" + uploadFileName);

				// 文件上传
				try {
					FileUtils.copyFile(upload, diskFile);// upload是源文件
				} catch (IOException e) {
					e.printStackTrace();
				}
				image.setUrls("imagePerson/" + uploadFileName);// setUrls中存放：路径加文件名
				userService.save(u);
				MyUtil.sendString(Constant.STRING_1);
			}else {
				MyUtil.sendString(Constant.STRING_0);
			}
		}
	}

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public Posts getPost() {
		return post;
	}

	public void setPost(Posts post) {
		this.post = post;
	}

	public Collects getCollect() {
		return collect;
	}

	public void setCollect(Collects collect) {
		this.collect = collect;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public Act getAct() {
		return act;
	}

	public void setAct(Act act) {
		this.act = act;
	}

	public Partici getPartici() {
		return partici;
	}

	public void setPartici(Partici partici) {
		this.partici = partici;
	}

	public IActService getActService() {
		return actService;
	}

	public void setActService(IActService actService) {
		this.actService = actService;
	}
}
