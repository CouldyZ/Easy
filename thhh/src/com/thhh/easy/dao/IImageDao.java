package com.thhh.easy.dao;

import java.util.List;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.thhh.easy.entity.Image;
import com.thhh.easy.entity.Users;

public interface IImageDao {
	
	public abstract List<Image> findByProperty(String propertyName, Object value);
	
	public abstract HibernateTemplate getHibernateTemplate() ;
	
	public abstract void save(Image image);
	
	public abstract Image merge(Image image);

}
