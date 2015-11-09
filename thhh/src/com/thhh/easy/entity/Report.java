package com.thhh.easy.entity;

import java.util.Date;

/**
 * Report entity. @author MyEclipse Persistence Tools
 */

public class Report implements java.io.Serializable {

	// Fields

	private Integer id;
	private Users users;
	private Act act;
	private Date dates;

	// Constructors

	/** default constructor */
	public Report() {
	}

	/** full constructor */
	public Report(Users users, Act act, Date dates) {
		this.users = users;
		this.act = act;
		this.dates = dates;
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

	public Act getAct() {
		return this.act;
	}

	public void setAct(Act act) {
		this.act = act;
	}

	public Date getDates() {
		return this.dates;
	}

	public void setDates(Date dates) {
		this.dates = dates;
	}

}