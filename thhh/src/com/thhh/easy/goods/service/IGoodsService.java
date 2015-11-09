package com.thhh.easy.goods.service;

import java.util.List;

import com.thhh.easy.entity.Goods;
import com.thhh.easy.entity.Shop;

public interface IGoodsService {

	List<Shop> findShop(int pageIndex, int rowCount);

	List<Goods> findGoodsByShopId(int pageIndex, int rowCount, Integer shopId);

	Goods findGoodsInfo(Integer goodsId);

}