package com.example.ecourseonline.entity;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserLogin {
	private String userEmail;
	private String userPassword;

    public UserLogin(String userEmail, String userPassword) {
		super();
		this.userEmail = userEmail;
		this.userPassword = userPassword;
	}
	public UserLogin() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "UserLogin [userEmail=" + userEmail + ", userPassword=" + userPassword + "]";
	}

}
