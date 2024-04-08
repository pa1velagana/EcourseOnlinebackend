package com.example.RegisterLogin.Entity;

import com.example.RegisterLogin.Dto.UserDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="UserDetails")
public class User {
			
	@Id
	@Column(name="user_id",length=45)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int userid;
	
	@Column(name="user_name",length= 255)
	private String username;
	
	@Column(name="email",length= 255)
	private String email;
	
	@Column(name="password",length= 255)
	private String password;


	
}
