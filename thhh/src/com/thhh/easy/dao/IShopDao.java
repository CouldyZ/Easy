package com.thhh.easy.dao;

import java.util.List;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.thhh.easy.entity.Shop;

public interface IShopDao {

	public abstract void save(Shop transientInstance);

	public abstract void delete(Shop persistentInstance);

	public abstract Shop findById(java.lang.Integer id);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract Shop merge(Shop detachedInstance);
	
	public abstract HibernateTemplate getHibernateTemplate() ;

}