package com.example.RegisterLogin.Service;

import com.example.RegisterLogin.Dto.UserDTO;
import com.example.RegisterLogin.Dto.LoginDTO;
import com.example.RegisterLogin.response.LoginResponse;

public interface UserService {



	UserDTO addEmployee(UserDTO employeeDTO);

	LoginResponse loginEmployee(LoginDTO loginDTO);
}
