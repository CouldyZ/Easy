package com.thhh.easy.dao;

import java.util.List;

import com.thhh.easy.entity.Image;
import com.thhh.easy.entity.Users;

public interface IUsersDao {

	public abstract Users findById(java.lang.Integer id);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract List findAll();

	public abstract Users merge(Users detachedInstance);
	
	public void save(Users transientInstance) ;

	public abstract Users findByName(String name);
	
	public abstract List<Users> findUserById(Integer id);
	

}