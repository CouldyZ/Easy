package com.thhh.easy.dao;

import java.util.List;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.thhh.easy.entity.Goods;

public interface IGoodsDao {

	public abstract void save(Goods transientInstance);

	public abstract void delete(Goods persistentInstance);

	public abstract Goods findById(java.lang.Integer id);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract Goods merge(Goods detachedInstance);
	
	public abstract HibernateTemplate getHibernateTemplate() ;

}