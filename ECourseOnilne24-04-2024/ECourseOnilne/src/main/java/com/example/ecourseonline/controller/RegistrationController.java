package com.example.ecourseonline.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecourseonline.entity.CurrentSessionUser;
import com.example.ecourseonline.entity.MyUser;
import com.example.ecourseonline.entity.UserLogin;
import com.example.ecourseonline.exception.CurrentUserNotFoundException;
import com.example.ecourseonline.response.UserDetailsResponse;
import com.example.ecourseonline.service.UserService;

@RestController
@RequestMapping("/api")
public class RegistrationController {
	@Autowired
	UserService userService;

	@PostMapping(value = "/register/user")
	public ResponseEntity<?> signupUser(@RequestBody MyUser user) {

		return userService.saveUser(user);
	}

	@PostMapping(value = "/register/login")
	public ResponseEntity<CurrentSessionUser> loginTheUser(@RequestBody UserLogin userLogin) {
		return new ResponseEntity<>(userService.login(userLogin), HttpStatus.CREATED);
	}

	@ExceptionHandler(CurrentUserNotFoundException.class)
	public ResponseEntity<String> UserNotFoundExceptionHandling(CurrentUserNotFoundException ex) {
		return ResponseEntity.badRequest().body(ex.getErrMsg());
	}

	@DeleteMapping("{uuId}")
	public String deleteUser(@PathVariable("uuId") String uuid) {
		return userService.logOut(uuid);
	}

	@GetMapping("/user")
	public String getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || !authentication.isAuthenticated()) {
			return "No user authenticated";
		}

		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		return "Current logged-in user: " + userDetails.getUsername();
	}

//	@GetMapping("{emailid}")
//	public MyUser getDetails(@PathVariable("emailid") String emailId) {
//		return 
//	}
	@GetMapping("/{emailid}")
	public UserDetailsResponse getStudentsById(@PathVariable("emailid") String emailid) {
		return userService.getUserDetails(emailid);
	}

	@PutMapping("/{emailid}")
	public UserDetailsResponse updateUserDetails(@PathVariable("emailid") String emailid, @RequestBody MyUser user) {
		return userService.updateUserDetails(emailid, user);
	}
}
