package com.thhh.easy.entity;

import java.util.HashSet;
import java.util.Set;

/**
 * Shop entity. @author MyEclipse Persistence Tools
 */

public class Shop implements java.io.Serializable {

	// Fields

	private Integer id;
	private Image image;
	private String name;
	private String address;
	private String phone;
	private String shortcut;

	// Constructors

	/** default constructor */
	public Shop() {
	}

	/** full constructor */
	public Shop(Image image, String name, String address, String phone,
			String shortcut) {
		this.image = image;
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.shortcut = shortcut;
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

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getShortcut() {
		return this.shortcut;
	}

	public void setShortcut(String shortcut) {
		this.shortcut = shortcut;
	}

}