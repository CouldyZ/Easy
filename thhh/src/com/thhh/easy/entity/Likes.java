package com.thhh.easy.entity;

/**
 * Likes entity. @author MyEclipse Persistence Tools
 */

public class Likes implements java.io.Serializable {

	// Fields

	private Integer id;
	private Posts posts;
	private Users users;

	// Constructors

	/** default constructor */
	public Likes() {
	}

	/** full constructor */
	public Likes(Posts posts, Users users) {
		this.posts = posts;
		this.users = users;
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

}