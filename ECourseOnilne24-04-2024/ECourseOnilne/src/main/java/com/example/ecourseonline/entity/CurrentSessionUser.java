package com.example.ecourseonline.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class CurrentSessionUser {

	@Id
	private String userEmail;
	private int userId;
	private String uuId;

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUuId() {
		return uuId;
	}

	public void setUuId(String uuId) {
		this.uuId = uuId;
	}

	public CurrentSessionUser(String userEmail, int userId, String uuId) {
		super();
		this.userEmail = userEmail;
		this.userId = userId;
		this.uuId = uuId;
	}

	public CurrentSessionUser() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "CurrentSessionUser [userEmail=" + userEmail + ", userId=" + userId + ", uuId=" + uuId + "]";
	}

}
