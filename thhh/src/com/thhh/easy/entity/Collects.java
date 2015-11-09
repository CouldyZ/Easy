package com.thhh.easy.entity;

import java.util.Date;

/**
 * Collects entity. @author MyEclipse Persistence Tools
 */

public class Collects implements java.io.Serializable {

	// Fields

	private Integer id;
	private Posts posts;
	private Users users;
	private Date dates;

	// Constructors

	/** default constructor */
	public Collects() {
	}

	/** full constructor */
	public Collects(Posts posts, Users users, Date dates) {
		this.posts = posts;
		this.users = users;
		this.dates = dates;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Posts getPosts() {
		return this.posts;
	}

	public void setPosts(Posts posts) {
		this.posts = posts;
	}

	public Users getUsers() {
		return this.users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public Date getDates() {
		return this.dates;
	}

	public void setDates(Date dates) {
		this.dates = dates;
	}

}