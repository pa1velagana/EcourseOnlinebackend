package com.example.ecourseonline.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.example.ecourseonline.entity.CurrentSessionUser;
import com.example.ecourseonline.entity.MyUser;
import com.example.ecourseonline.entity.UserLogin;
import com.example.ecourseonline.response.UserDetailsResponse;

public interface UserService {
	ResponseEntity<?> saveUser(MyUser user);

	public CurrentSessionUser login(UserLogin userLogin);

	public String logOut(String uuId);

	public UserDetailsResponse getUserDetails(String emailId);

	public UserDetailsResponse updateUserDetails(String emailid, MyUser myuser);


}
