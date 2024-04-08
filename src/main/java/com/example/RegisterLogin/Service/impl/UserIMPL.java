package com.example.RegisterLogin.Service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.RegisterLogin.Dto.UserDTO;
import com.example.RegisterLogin.Dto.LoginDTO;
import com.example.RegisterLogin.Entity.User;
import com.example.RegisterLogin.Repo.UserRepo;
import com.example.RegisterLogin.Service.UserService;
import com.example.RegisterLogin.response.LoginResponse;

@Service
public class UserIMPL implements UserService {

	@Autowired
	private UserRepo userRepo;
	

	
	@Override
	public UserDTO addEmployee(UserDTO employeeDTO) {
		// TODO Auto-generated method stub
		
		User employee = new User(
			
			employeeDTO.getUserid(),
			employeeDTO.getUsername(),
			employeeDTO.getEmail(),
			employeeDTO.getPassword()

		);
		
		userRepo.save(employee);
		
		 // Create a new EmployeeDTO object with the employee name
	    UserDTO savedEmployeeDTO = new UserDTO(
	        employee.getUserid(),
	        employee.getUsername(),
	        employee.getEmail(),
	        employee.getPassword()
	    );

	    return savedEmployeeDTO;
	}


	@Override
	public LoginResponse loginEmployee(LoginDTO loginDTO) {
	    String msg = "";
	    User employee1 = userRepo.findByEmail(loginDTO.getEmail());
	    if (employee1 != null) {
	        String password = loginDTO.getPassword();
	        String storedPassword = employee1.getPassword();
	        if (password.equals(storedPassword)) {
	            Optional<User> employee = userRepo.findOneByEmailAndPassword(loginDTO.getEmail(), storedPassword);
	            if (employee.isPresent()) {
	                return new LoginResponse("Login Success", true);
	            }
	        } else {
	            return new LoginResponse("Password Not Match", false);
	        }
	    } else {
	        return new LoginResponse("Email not exists", false);
	    }
	    // If the code reaches here, it implies login failed due to incorrect email or other errors
	    return new LoginResponse("Login Failed", false);
	}
	


}
