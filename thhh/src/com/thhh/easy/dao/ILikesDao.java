package com.thhh.easy.dao;

import java.util.List;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.thhh.easy.entity.Likes;

public interface ILikesDao {

	public abstract void save(Likes transientInstance);

	public abstract void delete(Likes persistentInstance);

	public abstract Likes findById(java.lang.Integer id);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract Likes merge(Likes detachedInstance);

	public abstract HibernateTemplate getHibernateTemplate();

}