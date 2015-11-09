package com.thhh.easy.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Act entity. @author MyEclipse Persistence Tools
 */

public class Act implements java.io.Serializable {

	// Fields

	private Integer id;
	private Users users;
	private Date createDate;
	private String theme;
	private Integer account;
	private Integer days;
	private Float pay;
	private String states;
	private String contents;
	private Date startDate;

	// Constructors

	/** default constructor */
	public Act() {
	}

	/** minimal constructor */
	public Act(Users users, Date createDate, String theme, Integer account,
			Integer days, Float pay, String states, String contents,
			Date startDate) {
		this.users = users;
		this.createDate = createDate;
		this.theme = theme;
		this.account = account;
		this.days = days;
		this.pay = pay;
		this.states = states;
		this.contents = contents;
		this.startDate = startDate;
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

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getTheme() {
		return this.theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public Integer getAccount() {
		return this.account;
	}

	public void setAccount(Integer account) {
		this.account = account;
	}

	public Integer getDays() {
		return this.days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	public Float getPay() {
		return this.pay;
	}

	public void setPay(Float pay) {
		this.pay = pay;
	}

	public String getStates() {
		return this.states;
	}

	public void setStates(String states) {
		this.states = states;
	}

	public String getContents() {
		return this.contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

}