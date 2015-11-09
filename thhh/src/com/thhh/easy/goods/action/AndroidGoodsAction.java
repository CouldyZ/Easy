package com.thhh.easy.goods.action;

import java.util.List;

import com.thhh.easy.entity.Goods;
import com.thhh.easy.entity.Shop;
import com.thhh.easy.goods.service.IGoodsService;
import com.thhh.easy.util.Constant;
import com.thhh.easy.util.MyUtil;

public class AndroidGoodsAction {

	private IGoodsService goodsService ;
	private int pageIndex; // 当前页数
	private int rowCount; // 每页显示条数
	
	private Goods goods ;	//商品对象
	
	/**
	 * 查看商店
	 */
	public void seekShop(){
		if (pageIndex == 0 || rowCount == 0) {
			setPageIndex(Constant.DEFAULT_PAGE);
			setRowCount(Constant.DEFAULT_PAGE_SIZE);
		}
		List<Shop> listShop = goodsService.findShop(pageIndex,rowCount) ;
		MyUtil.sendString(listShop) ;
	}
	
	/**
	 * 查看商品
	 */
	public void seekGoods(){
		if (pageIndex == 0 || rowCount == 0) {
			setPageIndex(Constant.DEFAULT_PAGE);
			setRowCount(Constant.DEFAULT_PAGE_SIZE);
		}
		if(goods == null || goods.getShop() == null ||
				goods.getShop().getId() == null){
			MyUtil.sendString(Constant.STRING_0) ;
			return ;
		}
		List<Goods> listGoods = goodsService.findGoodsByShopId(pageIndex,rowCount,goods.getShop().getId()) ;
		for (Goods goods : listGoods) {
			goods.setShop(null) ;
		}
		MyUtil.sendString(listGoods) ;
		goods = null ;
	}
	
	/**
	 * 查看商品详情
	 */
	public void goodsInfo(){
		if(goods == null || goods.getId() == null ){
			MyUtil.sendString(Constant.STRING_0) ;
			return ;
		}
		goods = goodsService.findGoodsInfo(goods.getId()) ;
		MyUtil.sendString(goods) ;
		goods = null ;
	}
	
	public IGoodsService getGoodsService() {
		return goodsService;
	}

	public void setGoodsService(IGoodsService goodsService) {
		this.goodsService = goodsService;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}
}
