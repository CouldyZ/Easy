package com.thhh.easy.dao;

import java.util.List;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.thhh.easy.entity.Posts;

public interface IPostsDao {

	public abstract void save(Posts transientInstance);

	public abstract Posts findById(java.lang.Integer id);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract Posts merge(Posts detachedInstance);
	
	public abstract HibernateTemplate getHibernateTemplate();

}