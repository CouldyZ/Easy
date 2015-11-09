package com.thhh.easy.entity;

/**
 * Orderdetail entity. @author MyEclipse Persistence Tools
 */

public class Orderdetail implements java.io.Serializable {

	// Fields

	private Integer id;
	private Goods goods;
	private Orders orders;
	private Integer account;
	private Float amount;
	private Float allDeposit;

	// Constructors

	/** default constructor */
	public Orderdetail() {
	}

	/** full constructor */
	public Orderdetail(Goods goods, Orders orders, Integer account,
			Float amount, Float allDeposit) {
		this.goods = goods;
		this.orders = orders;
		this.account = account;
		this.amount = amount;
		this.allDeposit = allDeposit;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Goods getGoods() {
		return this.goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	public Orders getOrders() {
		return this.orders;
	}

	public void setOrders(Orders orders) {
		this.orders = orders;
	}

	public Integer getAccount() {
		return this.account;
	}

	public void setAccount(Integer account) {
		this.account = account;
	}

	public Float getAmount() {
		return this.amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public Float getAllDeposit() {
		return this.allDeposit;
	}

	public void setAllDeposit(Float allDeposit) {
		this.allDeposit = allDeposit;
	}

}