package com.example.ecourseonline.service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.ecourseonline.entity.CurrentSessionUser;
import com.example.ecourseonline.entity.MyUser;
import com.example.ecourseonline.entity.UserLogin;
import com.example.ecourseonline.exception.CurrentUserNotFoundException;
import com.example.ecourseonline.repository.CurrentSessionRepo;
import com.example.ecourseonline.repository.UserRepository;
import com.example.ecourseonline.response.MessageResponse;
import com.example.ecourseonline.response.SignupResponse;
import com.example.ecourseonline.response.UserDetailsResponse;

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
		if (userRepository.existsByUserName(user.getUserName())) {

			return ResponseEntity.badRequest().body(new MessageResponse(("Error: Username is already taken!")));
		}

		if (userRepository.existsByUserEmail(user.getUserEmail())) {

			return ResponseEntity.badRequest().body(new MessageResponse(("Error: Email is already in use!")));
		}

		// Create new user's account
		MyUser userDetail = new MyUser();
		userDetail.setUserEmail(user.getUserEmail());
		userDetail.setUserName(user.getUserName());
		userDetail.setUserPassword(user.getUserPassword());
		userRepository.save(userDetail);

		return ResponseEntity.ok().body(new SignupResponse(user.getUserName(), user.getUserEmail()));
	}

	@Override
	public CurrentSessionUser login(UserLogin userLogin) {
		MyUser user = userRepository.findByUserEmail(userLogin.getUserEmail());
		if (userRepository.existsByUserEmail(user.getUserEmail())) {
			MyUser myUser = user;
			if (myUser.getUserPassword().equals(userLogin.getUserPassword())) {
				Optional<CurrentSessionUser> optional = currentSessionRepo.findById(userLogin.getUserEmail());
				if (optional.isEmpty()) {
					String key = randomString();
					CurrentSessionUser session = new CurrentSessionUser(userLogin.getUserEmail(), myUser.getUserId(),
							key);
					return currentSessionRepo.save(session);

				} else {
					throw new CurrentUserNotFoundException("Customer already login");
				}

			} else {
				throw new CurrentUserNotFoundException("password is not correct");
			}

		} else {
			throw new CurrentUserNotFoundException("Email is not correct");
		}

	}

	@Override
	public String logOut(String uuId) {
		Optional<CurrentSessionUser> user = currentSessionRepo.findByuuId(uuId);
		if (user.isPresent()) {
			CurrentSessionUser user1 = user.get();
			currentSessionRepo.delete(user1);
			return "Logout Successfully" + user1.getUserEmail();
		} else {
			throw new CurrentUserNotFoundException("wrong UUID");
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<MyUser> user = userRepository.findByUserName(username);
		if (user.isPresent()) {
			MyUser user2 = user.get();
			return User.builder().username(user2.getUserName()).password(user2.getUserPassword())

					.build();

		} else {
			throw new UsernameNotFoundException(username);
		}
	}

	public String randomString() {
		String alphabets = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456@#$%";
		int length = 6;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i <= length; i++) {
			Random random = new Random();
			int index = random.nextInt(alphabets.length());
			sb.append(alphabets.charAt(index));
		}
		return sb.toString();
	}

	@Override
	public UserDetailsResponse getUserDetails(String emailId) {
		// TODO Auto-generated method stub
		MyUser user = userRepository.findByUserEmail(emailId);
		if (user != null) {
			UserDetailsResponse response = new UserDetailsResponse();
			response.setLocation(user.getLocation());
			response.setPhoneNumber(user.getPhoneNumber());

			return response;
		}
		return null;
	}

	@Override
	public UserDetailsResponse updateUserDetails(String emailid, MyUser myuser) {
		MyUser user = userRepository.findByUserEmail(emailid);
		
		if(userRepository.existsByUserEmail(emailid)){
		user.setLocation(myuser.getLocation());
		user.setPhoneNumber(myuser.getPhoneNumber());
		userRepository.save(user);
		return getUserDetails(emailid);
		
		}
		else {
			return null;
		}
		
		
	}
}
