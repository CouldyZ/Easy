package com.thhh.easy.dao;

import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.thhh.easy.entity.Collects;

public interface ICollectsDao {

	public abstract void save(Collects transientInstance);

	public abstract void delete(Collects persistentInstance);

	public abstract Collects findById(java.lang.Integer id);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract Collects merge(Collects detachedInstance);
	
	public abstract HibernateTemplate getHibernateTemplate() ;

}