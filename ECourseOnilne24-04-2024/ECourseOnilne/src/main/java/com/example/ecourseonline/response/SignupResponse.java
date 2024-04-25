package com.example.ecourseonline.response;

public class SignupResponse {

	private String Username;

	private String email;

	public String getUsername() {
		return Username;
	}

	public void setUsername(String username) {
		Username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public SignupResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SignupResponse(String username, String email) {
		super();
		Username = username;
		this.email = email;
	}

}
