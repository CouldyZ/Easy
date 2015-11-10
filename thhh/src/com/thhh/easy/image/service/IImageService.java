package com.thhh.easy.image.service;

import java.util.List;

import com.thhh.easy.entity.Act;
import com.thhh.easy.entity.Image;

public interface IImageService {

	public abstract List<Image> findByProperty(String propertyName, Object value);

}
