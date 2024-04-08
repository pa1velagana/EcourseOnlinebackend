package com.example.RegisterLogin.EmployeeController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.RegisterLogin.Dto.UserDTO;
import com.example.RegisterLogin.Dto.LoginDTO;
import com.example.RegisterLogin.Service.UserService;
import com.example.RegisterLogin.response.LoginResponse;

@RestController
@CrossOrigin
@RequestMapping("api/v1/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping(path="/save")
	public ResponseEntity<UserDTO> saveEmployee(@RequestBody UserDTO employeeDTO){
		return new ResponseEntity<UserDTO>(userService.addEmployee(employeeDTO),HttpStatus.OK);
	}
	
	@PostMapping(path="/login")
	public ResponseEntity<?> loginEmployee(@RequestBody LoginDTO loginDTO)
	{
		LoginResponse loginResponse=userService.loginEmployee(loginDTO);
		return  ResponseEntity.ok(loginResponse);
	}
}
