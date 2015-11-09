package com.thhh.easy.goods.service;

import java.util.List;

import com.thhh.easy.entity.Orderdetail;
import com.thhh.easy.entity.Orders;
import com.thhh.easy.entity.Shop;
import com.thhh.easy.entity.Users;

public interface IOrdersService {

	Shop findShop(Integer shopId);

	int saveOrders(Orders orders);

	Users findUser(Integer userId);

	Orders findOrders(int orderId);

	void saveOrderdetatil(Orderdetail orderdetail);

	List<Orders> findOrdersByUserId(int pageIndex, int rowCount, Integer userId);

	List<Orderdetail> findOrderdetailByOrderId(int pageIndex, int rowCount,
			Integer orderId);

	void deleteOrders(Integer ordersId);

}