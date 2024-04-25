package com.example.ecourseonline.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.example.ecourseonline.entity.CurrentSessionUser;
import com.example.ecourseonline.entity.MyUser;
import com.example.ecourseonline.entity.UserLogin;
import com.example.ecourseonline.exception.CurrentUserNotFoundException;
import com.example.ecourseonline.response.UserDetailsResponse;
import com.example.ecourseonline.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/register/user")
	public ResponseEntity<?> registerUser(@RequestBody MyUser newUser) {
		return userService.saveUser(newUser);
	}

	@PostMapping("/register/login")
	public ResponseEntity<CurrentSessionUser> loginUser(@RequestBody UserLogin userLogin) {
		return new ResponseEntity<>(userService.login(userLogin), HttpStatus.CREATED);
	}

	@PostMapping("/register/logout")
	public ResponseEntity<String> logoutUser(@RequestParam String uuId) {
		String message = userService.logOut(uuId);
		return ResponseEntity.ok(message);
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

	@GetMapping("/user/details")
	public ResponseEntity<UserDetailsResponse> getUserDetails(@RequestParam String email) {
		UserDetailsResponse userDetails = userService.getUserDetails(email);
		return ResponseEntity.ok(userDetails);
	}

	@PutMapping("/user/update")
	public ResponseEntity<UserDetailsResponse> updateUserDetails(@RequestParam String email,
																 @RequestBody MyUser updatedUserDetails) {
		UserDetailsResponse userDetails = userService.updateUserDetails(email, updatedUserDetails);
		if (userDetails != null) {
			return ResponseEntity.ok(userDetails);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/user/{uuId}")
	public String deleteUser(@PathVariable("uuId") String uuid) {
		return userService.logOut(uuid);
	}

	@GetMapping("/user/{emailId}")
	public ResponseEntity<MyUser> getUserByEmail(@PathVariable("emailId") String emailId) {
		MyUser user = userService.getUserByEmail(emailId);
		if (user != null) {
			return ResponseEntity.ok(user);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@ExceptionHandler(CurrentUserNotFoundException.class)
	public ResponseEntity<String> UserNotFoundExceptionHandling(CurrentUserNotFoundException ex) {
		return ResponseEntity.badRequest().body(ex.getErrMsg());
	}
}
