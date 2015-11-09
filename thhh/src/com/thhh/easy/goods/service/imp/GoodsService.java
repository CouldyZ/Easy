package com.thhh.easy.goods.service.imp;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.thhh.easy.dao.IGoodsDao;
import com.thhh.easy.dao.IShopDao;
import com.thhh.easy.entity.Goods;
import com.thhh.easy.entity.Posts;
import com.thhh.easy.entity.Shop;
import com.thhh.easy.goods.service.IGoodsService;

public class GoodsService implements IGoodsService {

	private IGoodsDao goodsDao ;
	private IShopDao shopDao ;

	/**
	 * 查看商店
	 */
	public List<Shop> findShop(int pageIndex, int rowCount) {
		List<Shop> listShop = null ;
		DetachedCriteria criteria = DetachedCriteria.forClass(Shop.class) ;
		listShop = shopDao.getHibernateTemplate().findByCriteria(criteria, (pageIndex - 1) * rowCount, rowCount) ;
		for (Shop shop : listShop) {
			Hibernate.initialize(shop.getImage());
		}
		return listShop;
	}
	
	/**
	 * 查看商品
	 */
	public List<Goods> findGoodsByShopId(int pageIndex, int rowCount, Integer shopId) {
		List<Goods> listGoods = null ;
		DetachedCriteria criteria = DetachedCriteria.forClass(Goods.class) ;
		criteria.add(Restrictions.eq("shop.id", shopId));
		listGoods = goodsDao.getHibernateTemplate().findByCriteria(criteria, (pageIndex - 1) * rowCount, rowCount) ;
		for (Goods goods : listGoods) {
			Hibernate.initialize(goods.getImage());
			Hibernate.initialize(goods.getShop());
		}
		return listGoods;
	}
	
	/**
	 * 查看商品详情
	 */
	public Goods findGoodsInfo(Integer goodsId) {
		// TODO Auto-generated method stub
		return goodsDao.findById(goodsId);
	}
	
	public IGoodsDao getGoodsDao() {
		return goodsDao;
	}

	public void setGoodsDao(IGoodsDao goodsDao) {
		this.goodsDao = goodsDao;
	}

	public IShopDao getShopDao() {
		return shopDao;
	}

	public void setShopDao(IShopDao shopDao) {
		this.shopDao = shopDao;
	}

}
