package com.example.ecourseonline.response;

import java.math.BigInteger;

public class UserDetailsResponse {

	

	private String location;

	private BigInteger phoneNumber;



	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public BigInteger getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(BigInteger phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public UserDetailsResponse(String location, BigInteger phoneNumber) {
		super();
		this.location = location;
		this.phoneNumber = phoneNumber;
	}

	public UserDetailsResponse() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "UserDetailsResponse [location=" + location + ", phoneNumber=" + phoneNumber + "]";
	}

	
}
