package com.thhh.easy.goods.service.imp;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.thhh.easy.dao.IOrderdetailDAO;
import com.thhh.easy.dao.IOrdersDao;
import com.thhh.easy.dao.IShopDao;
import com.thhh.easy.dao.IUsersDao;
import com.thhh.easy.entity.Goods;
import com.thhh.easy.entity.Orderdetail;
import com.thhh.easy.entity.Orders;
import com.thhh.easy.entity.Shop;
import com.thhh.easy.entity.Users;
import com.thhh.easy.goods.service.IOrdersService;

public class OrdersService implements IOrdersService {

	private IOrdersDao ordersDao ;
	private IShopDao shopDao ;
	private IUsersDao usersDao ;
	private IOrderdetailDAO orderdetailDAO ;

	/**
	 * 根据id查找商店
	 */
	public Shop findShop(Integer shopId) {
		// TODO Auto-generated method stub
		return shopDao.findById(shopId);
	}
	
	/**
	 * 根据id查找用户
	 */
	public Users findUser(Integer userId) {
		
		return usersDao.findById(userId);
	}
	
	/**
	 * 保存订单
	 */
	public int saveOrders(Orders orders) {
		
		return ordersDao.saveOrders(orders) ;
	}
	
	/**
	 * 查找订单
	 */
	public Orders findOrders(int orderId) {
		// TODO Auto-generated method stub
		return ordersDao.findById(orderId);
	}
	
	/**
	 * 保存订单项
	 */
	public void saveOrderdetatil(Orderdetail orderdetail) {
		// TODO Auto-generated method stub
		orderdetailDAO.save(orderdetail) ;
	}
	
	/**
	 * 查看用户订单
	 */
	public List<Orders> findOrdersByUserId(int pageIndex, int rowCount,
			Integer userId) {
		List<Orders> listOrders = null ;
		DetachedCriteria criteria = DetachedCriteria.forClass(Orders.class) ;
		criteria.add(Restrictions.eq("users.id", userId));
		listOrders = ordersDao.getHibernateTemplate().findByCriteria(criteria, (pageIndex - 1) * rowCount, rowCount) ;
		return listOrders;
	}
	
	/**
	 * 查看订单详情
	 */
	public List<Orderdetail> findOrderdetailByOrderId(int pageIndex,
			int rowCount, Integer orderId) {
		List<Orderdetail> listOrderdetail = null ;
		DetachedCriteria criteria = DetachedCriteria.forClass(Orderdetail.class) ;
		criteria.add(Restrictions.eq("orders.id", orderId));
		listOrderdetail = orderdetailDAO.getHibernateTemplate().findByCriteria(criteria, (pageIndex - 1) * rowCount, rowCount) ;
		return listOrderdetail;
	}
	
	public IOrdersDao getOrdersDao() {
		return ordersDao;
	}

	public void setOrdersDao(IOrdersDao ordersDao) {
		this.ordersDao = ordersDao;
	}
	public IShopDao getShopDao() {
		return shopDao;
	}
	public void setShopDao(IShopDao shopDao) {
		this.shopDao = shopDao;
	}

	public IUsersDao getUsersDao() {
		return usersDao;
	}

	public void setUsersDao(IUsersDao usersDao) {
		this.usersDao = usersDao;
	}

	public IOrderdetailDAO getOrderdetailDAO() {
		return orderdetailDAO;
	}

	public void setOrderdetailDAO(IOrderdetailDAO orderdetailDAO) {
		this.orderdetailDAO = orderdetailDAO;
	}



}
