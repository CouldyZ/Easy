package com.thhh.easy.dao;

import java.util.List;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.thhh.easy.entity.Comments;

public interface ICommentsDao {

	public abstract void save(Comments transientInstance);

	public abstract void delete(Comments persistentInstance);

	public abstract Comments findById(java.lang.Integer id);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract Comments merge(Comments detachedInstance);
	
	public abstract HibernateTemplate getHibernateTemplate() ;

}