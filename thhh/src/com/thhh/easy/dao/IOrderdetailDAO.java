package com.thhh.easy.dao;

import java.util.List;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.thhh.easy.entity.Orderdetail;

public interface IOrderdetailDAO {

	public abstract void save(Orderdetail transientInstance);

	public abstract void delete(Orderdetail persistentInstance);

	public abstract Orderdetail findById(java.lang.Integer id);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract Orderdetail merge(Orderdetail detachedInstance);
	
	public abstract HibernateTemplate getHibernateTemplate() ;

}