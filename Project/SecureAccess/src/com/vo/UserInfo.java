package com.vo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "userinfo")
public class UserInfo {
	
	@Id
	@GeneratedValue
	@Column(name = "id", unique = true)
	private int id;
	
	@Column(name = "name", nullable = false, length = 100)
	private String name;
	
	@Column(name = "dob")
	private String dob;
	
	@Column(name = "email",  length = 100)
	private String email;
	
	@Column(name = "phone",length = 20)
	private String phone;
	
	@Column(name = "panno", length = 20)
	private String panno;
	
	@Column(name = "aatharno",length = 20)
	private String aatharno;
	
	@Column(name = "passportno",length = 10)
	private String passportno;
	
	@Column(name = "fingureprint",length = 500000)
	private byte[] fingureprint;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPanno() {
		return panno;
	}

	public void setPanno(String panno) {
		this.panno = panno;
	}

	public String getAatharno() {
		return aatharno;
	}

	public void setAatharno(String aatharno) {
		this.aatharno = aatharno;
	}

	public String getPassportno() {
		return passportno;
	}

	public void setPassportno(String passportno) {
		this.passportno = passportno;
	}

	public byte[] getFingureprint() {
		return fingureprint;
	}

	public void setFingureprint(byte[] fingureprint) {
		this.fingureprint = fingureprint;
	}

}
