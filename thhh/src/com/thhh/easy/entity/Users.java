package com.thhh.easy.entity;

import java.util.HashSet;
import java.util.Set;

/**
 * Users entity. @author MyEclipse Persistence Tools
 */

public class Users implements java.io.Serializable {

	// Fields

	private Integer id;
	private Image image;
	private String name;
	private String pwd;
	private String nickname;
	private String email;
	private String numbers;
	private String depart;
	private String tname;
	private String gender;
	private Integer rp;

	// Constructors

	/** default constructor */
	public Users() {
	}

	/** minimal constructor */
	public Users(String name, String pwd, Integer rp) {
		this.name = name;
		this.pwd = pwd;
		this.rp = rp;
	}

	/** full constructor */
	public Users(Image image, String name, String pwd, String nickname,
			String email, String numbers, String depart, String tname,
			String gender, Integer rp) {
		this.image = image;
		this.name = name;
		this.pwd = pwd;
		this.nickname = nickname;
		this.email = email;
		this.numbers = numbers;
		this.depart = depart;
		this.tname = tname;
		this.gender = gender;
		this.rp = rp;
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

	public String getPwd() {
		return this.pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNumbers() {
		return this.numbers;
	}

	public void setNumbers(String numbers) {
		this.numbers = numbers;
	}

	public String getDepart() {
		return this.depart;
	}

	public void setDepart(String depart) {
		this.depart = depart;
	}

	public String getTname() {
		return this.tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Integer getRp() {
		return this.rp;
	}

	public void setRp(Integer rp) {
		this.rp = rp;
	}


}