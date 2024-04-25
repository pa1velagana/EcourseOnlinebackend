package com.example.ecourseonline.service;

import com.example.ecourseonline.entity.CurrentSessionUser;
import com.example.ecourseonline.entity.MyUser;
import com.example.ecourseonline.entity.UserLogin;
import com.example.ecourseonline.exception.CurrentUserNotFoundException;
import com.example.ecourseonline.repository.CurrentSessionRepo;
import com.example.ecourseonline.repository.UserRepository;
import com.example.ecourseonline.response.MessageResponse;
import com.example.ecourseonline.response.SignupResponse;
import com.example.ecourseonline.response.UserDetailsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	CurrentSessionRepo currentSessionRepo;

	@Override
	public ResponseEntity<?> saveUser(MyUser user) {
		if (userRepository.existsByUserEmail(user.getUserEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse(("Error: Email is already in use!")));
		}

		// Encode password before saving
		user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));

		// Save user
		userRepository.save(user);

		return ResponseEntity.ok().body(new SignupResponse(user.getUserName(), user.getUserEmail()));
	}

	@Override
	public CurrentSessionUser login(UserLogin userLogin) {
		MyUser user = userRepository.findByUserEmail(userLogin.getUserEmail());
		if (user == null) {
			throw new CurrentUserNotFoundException("Email is not correct");
		}

		if (!passwordEncoder.matches(userLogin.getUserPassword(), user.getUserPassword())) {
			throw new CurrentUserNotFoundException("Password is not correct");
		}

		// Check if user is already logged in
		Optional<CurrentSessionUser> optional = currentSessionRepo.findById(user.getUserEmail());
		if (optional.isPresent()) {
			throw new CurrentUserNotFoundException("User is already logged in");
		}

		// Generate session key
		String key = randomString();

		// Create and save session
		CurrentSessionUser session = new CurrentSessionUser(user.getUserEmail(), user.getUserId(), key);
		return currentSessionRepo.save(session);
	}

	@Override
	public String logOut(String uuId) {
		Optional<CurrentSessionUser> user = currentSessionRepo.findByuuId(uuId);
		if (user.isPresent()) {
			currentSessionRepo.delete(user.get());
			return "Logout Successfully";
		} else {
			throw new CurrentUserNotFoundException("Invalid UUID");
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		MyUser user = userRepository.findByUserName(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		return User.builder()
				.username(user.getUserName())
				.password(user.getUserPassword())
				.roles("USER") // Assuming all users have the role "USER"
				.build();
	}

	@Override
	public UserDetailsResponse getUserDetails(String emailId) {
		MyUser user = userRepository.findByUserEmail(emailId);
		if (user != null) {
			UserDetailsResponse response = new UserDetailsResponse();
			response.setLocation(user.getLocation());
			response.setPhoneNumber(user.getPhoneNumber());
			// Populate other details as needed
			return response;
		}
		return null; // Or throw an exception if user not found
	}

	@Override
	public UserDetailsResponse updateUserDetails(String emailId, MyUser updatedUserDetails) {
		MyUser user = userRepository.findByUserEmail(emailId);
		if (user != null) {
			// Update user details
			user.setLocation(updatedUserDetails.getLocation());
			user.setPhoneNumber(updatedUserDetails.getPhoneNumber());
			// You can update other fields as needed

			// Save updated user
			userRepository.save(user);

			// Return updated user details
			return getUserDetails(emailId);
		} else {
			// User not found, return null or throw an exception
			return null;
		}
	}

	@Override
	public MyUser getUserByEmail(String email) {
		return userRepository.findByUserEmail(email);
	}


	public String randomString() {
		String alphabets = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456@#$%";
		int length = 6;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			Random random = new Random();
			int index = random.nextInt(alphabets.length());
			sb.append(alphabets.charAt(index));
		}
		return sb.toString();
	}
}
