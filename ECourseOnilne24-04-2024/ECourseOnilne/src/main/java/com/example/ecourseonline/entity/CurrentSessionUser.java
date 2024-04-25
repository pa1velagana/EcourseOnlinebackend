package com.example.ecourseonline.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class CurrentSessionUser {

	@Id
	private String userEmail;
	private int userId;
	private String uuId;

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
