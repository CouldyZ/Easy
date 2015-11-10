package com.thhh.easy.dao;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.thhh.easy.entity.Orders;

public interface IOrdersDao {

	public abstract void save(Orders transientInstance);

	public abstract void delete(Orders persistentInstance);

	public abstract Orders findById(java.lang.Integer id);

	public abstract Orders merge(Orders detachedInstance);
	
	public Integer saveOrders(Orders transientInstance) ;
	
	public abstract HibernateTemplate getHibernateTemplate() ;

}