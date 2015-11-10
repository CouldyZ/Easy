package com.thhh.easy.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Posts entity. @author MyEclipse Persistence Tools
 */

public class Posts implements java.io.Serializable {

	// Fields

	private Integer id;
	private Image image;
	private Users users;
	private Date dates;
	private String contents;
	private Date latest;

	// Constructors

	/** default constructor */
	public Posts() {
	}

	/** minimal constructor */
	public Posts(Users users, Date dates, String contents) {
		this.users = users;
		this.dates = dates;
		this.contents = contents;
	}

	/** full constructor */
	public Posts(Image image, Users users, Date dates, String contents,
			Date latest) {
		this.image = image;
		this.users = users;
		this.dates = dates;
		this.contents = contents;
		this.latest = latest;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Image getImage() {
		return this.image;
	}

	public void setImage(Image image) {
		this.image = image;
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

	public String getContents() {
		return this.contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public Date getLatest() {
		return this.latest;
	}

	public void setLatest(Date latest) {
		this.latest = latest;
	}

}