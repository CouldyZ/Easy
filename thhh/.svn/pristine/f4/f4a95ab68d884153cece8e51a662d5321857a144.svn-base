package com.thhh.easy.image.service.imp;

import java.util.List;

import com.thhh.easy.dao.IImageDao;
import com.thhh.easy.entity.Image;
import com.thhh.easy.image.service.IImageService;

public class ImageService implements IImageService{
	
	private IImageDao imageDao;

	//查看用户的图片 propertyName users , value Users
	public List<Image> findByProperty(String propertyName, Object value) {
		
		return imageDao.findByProperty(propertyName, value);
		
	}

	public IImageDao getImageDao() {
		return imageDao;
	}

	public void setImageDao(IImageDao imageDao) {
		this.imageDao = imageDao;
	}
	
}
