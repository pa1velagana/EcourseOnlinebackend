package com.example.RegisterLogin.Service;

import com.example.RegisterLogin.Dto.UserDTO;
import com.example.RegisterLogin.Dto.LoginDTO;
import com.example.RegisterLogin.Entity.User;
import com.example.RegisterLogin.response.LoginResponse;
import org.springframework.http.ResponseEntity;

public interface UserService {



	UserDTO addEmployee(UserDTO employeeDTO);

	LoginResponse loginEmployee(LoginDTO loginDTO);

	ResponseEntity<?> addingUserDetails(User user);
}
