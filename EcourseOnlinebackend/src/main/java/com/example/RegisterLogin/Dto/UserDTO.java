package com.example.RegisterLogin.Dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

	private int userid;
	
	private String username;

	private String email;
	
	private String password;

	

	
}