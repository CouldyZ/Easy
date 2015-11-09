package com.thhh.easy.user.service;

import java.util.List;

import com.thhh.easy.entity.Act;
import com.thhh.easy.entity.Collects;
import com.thhh.easy.entity.Image;
import com.thhh.easy.entity.Posts;
import com.thhh.easy.entity.Users;

public interface IUserService {

	//根据id查询用户信息
	Users findById(int id);
	
	//根据用户名查询用户信息
	Users findUserByName(String name);
	
	//查询所有用户的信息
	List findAll();
	
	//保存用户信息
	void save(Users user);
	
	//更新用户信息
	Users updateUsers(Users u);
	
	//查找用户的发帖情况
	List<Posts> findUserPosts(Integer userId);
	
	
	List<Image> findByProperty(String propertyName, Object value);
	
	//查找用户收藏帖子的情况
	List<Collects> findUserCollects(Integer userId);

	//查找用户的照片墙
	List<Image> findUserImage(Integer id);
	
	//根据用户的id查找用户，返回list集合
	List<Users> findUserById(Integer id);
}