package com.thhh.easy.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Orders entity. @author MyEclipse Persistence Tools
 */

public class Orders implements java.io.Serializable {

	// Fields

	private Integer id;
	private Users users;
	private Shop shop;
	private Date dates;
	private String states;
	private String take;
	private Float amount;
	private Float allDeposit;

	// Constructors

	/** default constructor */
	public Orders() {
	}

	/** minimal constructor */
	public Orders(Users users, Shop shop, Date dates, String states,
			String take, Float amount, Float allDeposit) {
		this.users = users;
		this.shop = shop;
		this.dates = dates;
		this.states = states;
		this.take = take;
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

	public Users getUsers() {
		return this.users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public Shop getShop() {
		return this.shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public Date getDates() {
		return this.dates;
	}

	public void setDates(Date dates) {
		this.dates = dates;
	}

	public String getStates() {
		return this.states;
	}

	public void setStates(String states) {
		this.states = states;
	}

	public String getTake() {
		return this.take;
	}

	public void setTake(String take) {
		this.take = take;
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