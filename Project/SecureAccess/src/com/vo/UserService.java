package com.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "userservice")
public class UserService {
	
	@Id
	@GeneratedValue
	@Column(name = "id", unique = true)
	private int id;
	
	@Column(name = "userid", nullable = false, length = 200)
	private String userid;
	
	@Column(name = "service")
	private int service;

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public int getService() {
		return service;
	}

	public void setService(int service) {
		this.service = service;
	}
	
	
}
